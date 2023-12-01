import { createContext, useContext, useEffect, useState } from 'react';
import {
  getBookings,
  getBookingsId,
  postBookings,
  deleteBooking,
} from '../api/booking';

const BookingsContext = createContext();
export const useBookingsContext = () => {
  return useContext(BookingsContext);
};

export const BookingsProvider = ({ children }) => {
  const [bookingsData, setBookingsData] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [bookingById, setBookingById] = useState([]);

  const fetchData = async () => {
    setLoading(true);
    try {
      const data = await getBookings();
      setBookingsData(data);
    } catch (err) {
      setError(err);
    } finally {
      setLoading(false);
    }
  };

  // FUNCION PARA BUSCAR POR ID
  const bookingByIdGet = async (id) => {
    setLoading(true);
    try {
      const data = await getBookingsId(id);
      setBookingById(data);
    } catch (err) {
      setError(err);
    } finally {
      setLoading(false);
    }
  };

  // FUNCION PARA CREAR RESERVAS
  const addNewBooking = async (userId, bikeId, fechaInicio, fechaFin) => {
    setLoading(true);
    try {
      const newBooking = await postBookings(
        userId,
        bikeId,
        fechaInicio,
        fechaFin
      );
      fetchData();
      return newBooking;
    } catch (err) {
      console.log(err.message);
      setError(err);
    } finally {
      setLoading(false);
    }
  };

  // FUNCION PARA BORRAR RESERVAS
  const deleteABooking = async (id) => {
    setLoading(true);
    try {
      const deletedBooking = await deleteBooking(id);
      fetchData();
      return deletedBooking;
    } catch (err) {
      setError(err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <BookingsContext.Provider
      value={{
        //PROPIEDADES
        bookingsData,
        loading,
        error,
        bookingById,
        // METODOS
        bookingByIdGet,
        addNewBooking,
        deleteABooking,
        setError,
      }}
    >
      {children}
    </BookingsContext.Provider>
  );
};
