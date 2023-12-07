import { createContext, useContext, useEffect, useState } from "react";
import {
    getBookings,
    postBookings,
    deleteBooking,
    getBookingsByUserId,
    postRating,
} from "../api/booking";
import { useForm } from "../hooks/useForm";

const BookingsContext = createContext();
export const useBookingsContext = () => {
    return useContext(BookingsContext);
};
const formData = {
    puntuacion: "",
    comentario: "",
    reserva: "",
};
export const BookingsProvider = ({ children }) => {
    const [bookingsData, setBookingsData] = useState([]);
    const [loading, setLoading] = useState(true);
    const [upLoadingBooking, setUploadingBooking] = useState(false);
    const [error, setError] = useState("");
    const [bookingByUserId, setBookingByUserId] = useState([]);
    const [openBookingHistoryModal, setOpenBookingHistoryModal] =
        useState(false);
    const [openDetailBookingModal, setOpenDetailBookingModal] = useState(false);
    const [bookingDetail, setBookingDetail] = useState({});
    const [loadingRating, setLoadingRating] = useState(false);
    const [errorRating, setErrorRating] = useState(false);
    const {
        onInputChange,
        onResetForm,
        reserva,
        puntuacion,
        comentario,
        setFormState,
        formState,
    } = useForm(formData);
    const handleOpenDetailBookingModal = () => {
        setOpenDetailBookingModal(!openDetailBookingModal);
    };
    const handleOpenHistoryModal = () => {
        setOpenBookingHistoryModal(!openBookingHistoryModal);
    };
    const [isReserved, setIsReserved] = useState(false);

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
    const bookingByUserIdGet = async (id) => {
        setLoading(true);
        try {
            const data = await getBookingsByUserId(id);
            setBookingByUserId(data);
        } catch (err) {
            setError(err);
        } finally {
            setLoading(false);
        }
    };

    // FUNCION PARA CREAR RESERVAS
    const addNewBooking = async (userId, bikeId, fechaInicio, fechaFin) => {
        setUploadingBooking(true);
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
            setUploadingBooking(false);
        }
    };
    // FUNCION PARA CREAR VALORACION
    const addNewRating = async (data) => {
        setLoadingRating(true);
        try {
            const newRating = await postRating(data);
            return newRating;
        } catch (err) {
            console.log(err.message);
            setErrorRating(err);
        } finally {
            setLoadingRating(false);
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

    return (
        <BookingsContext.Provider
            value={{
                //PROPIEDADES
                puntuacion,
                comentario,
                reserva,
                formState,
                bookingsData,
                bookingByUserId,
                loading,
                loadingRating,
                error,
                isReserved,
                openBookingHistoryModal,
                openDetailBookingModal,
                upLoadingBooking,
                bookingDetail,
                // METODOS
                bookingByUserIdGet,
                addNewBooking,
                deleteABooking,
                setError,
                setIsReserved,
                handleOpenHistoryModal,
                onInputChange,
                setFormState,
                onResetForm,
                addNewRating,
                handleOpenDetailBookingModal,
                setBookingDetail,
            }}
        >
            {children}
        </BookingsContext.Provider>
    );
};
