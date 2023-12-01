import React, { useState } from 'react';
import Section from '../Section';
import { useBookingsContext } from '../../../context/BookingContext';
import { useBikesContext } from '../../../context/BikesContext';
import { useUsersContext } from '../../../context/UsersContext';
import { IoIosArrowBack } from 'react-icons/io';
import { Link } from 'react-router-dom';
import { Loader } from '../../../ui/Loader';

export const Bookings = () => {
  const { addNewBooking, loading } = useBookingsContext();
  const { bikeById } = useBikesContext();
  const { userData } = useUsersContext();

  const [formData, setFormData] = useState({
    fechaInicio: '',
    fechaFin: '',
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await addNewBooking(
        userData.usuarioId,
        bikeById.bicicletaId,
        formData.fechaInicio,
        formData.fechaFin
      );

      setFormData({
        fechaInicio: '',
        fechaFin: '',
      });
    } catch (error) {
      console.error('Error al agregar reserva:', error);
    }
  };

  return (
    <Section>
      <div className='max-w-[1200px] mx-auto flex flex-col gap-6 pt-8 '>
        <div className='flex gap-2 items-center'>
          <div>
            <Link to={{}}>
              <button>
                <IoIosArrowBack />
              </button>
            </Link>
          </div>
          <div>
            <h2 className='text-lg sm:text-2xl font-semibold p-4'>
              Solicitar reserva
            </h2>
          </div>
        </div>

        <div className='flex flex-col gap-8 xl:flex-row'>
          <div className='border border-gray-100 shadow-md p-4 rounded-lg w-auto'>
            <div className='md:w-[600px] h-48'>
              <img
                className='object-cover w-full h-full'
                src={bikeById.imagenes[0].url}
                alt={`Imagen principal`}
              />
            </div>

            <h3 className='font-extrabold pt-2'>{bikeById.nombre}</h3>
            <div className='flex flex-col pt-2'>
              <p className='font-semibold'>Fecha de inicio</p>
              <p className='font-semibold pb-4'>Fecha de finalizacion</p>
              <p className='border-t border-gray-200 pt-4 font-semibold'>
                Total (ARG)
                <span className='font-light pl-24'>
                  {bikeById.precioAlquilerPorDia}
                </span>
              </p>
            </div>
          </div>
          <div className=' flex flex-col w-auto'>
            <h3 className='font-extrabold p-2'>Datos de la reserva</h3>
            <form onSubmit={handleSubmit}>
              <div className='flex flex-col items-start p-2'>
                <label htmlFor='fechaInicio'>Fecha de Inicio</label>
                <input
                  type='date'
                  id='fechaInicio'
                  name='fechaInicio'
                  value={formData.fechaInicio}
                  onChange={handleInputChange}
                />

                <label htmlFor='fechaFin'>Fecha de Fin</label>
                <input
                  type='date'
                  id='fechaFin'
                  name='fechaFin'
                  value={formData.fechaFin}
                  onChange={handleInputChange}
                />
              </div>
              <div className='border-t border-gray-200 p-2'>
                <p className='font-semibold flex flex-col lg:flex-row lg:gap-16'>
                  Usuario:
                  <span className='font-light'>
                    {userData.nombre &&
                      userData.nombre.charAt(0).toUpperCase() +
                        userData.nombre.slice(1)}{' '}
                    {userData.apellido &&
                      userData.apellido.charAt(0).toUpperCase() +
                        userData.apellido.slice(1)}
                  </span>
                </p>

                <p className='font-semibold flex flex-col lg:flex-row lg:gap-20'>
                  Email:
                  <span className='font-light '>
                    {userData.email &&
                      userData.email.charAt(0).toUpperCase() +
                        userData.email.slice(1)}
                  </span>
                </p>
              </div>
              <div className='border-t border-gray-200 p-2'>
                <p className='font-semibold'>
                  Método de pago
                  <span className='font-light pl-2'>Efectivo</span>
                </p>
                <p className='font-semibold'>
                  Precio Total
                  <span className='font-light pl-10'>
                    {bikeById.precioAlquilerPorDia}
                  </span>
                </p>
              </div>

              <div className='flex flex-col md:flex-row md:justify-center xl:flex-row xl:justify-evenly items-center gap-2  pt-12'>
                <button
                  className='middle none center  rounded-full bg-primary py-2 px-24 font-sans text-xs font-bold uppercase text-white shadow-sm  transition-all  hover:shadow-secondary  active:opacity-[0.85] active:shadow-none disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none'
                  data-ripple-light='true'
                  type='submit'
                  disabled={loading}
                >
                  {loading ? (
                    <div>
                      <Loader />
                      <p>RESERVANDO ...</p>
                    </div>
                  ) : (
                    'ALQUILAR'
                  )}
                </button>
                <button
                  className='middle none center  rounded-full border border-primary py-2 px-24 font-sans text-xs font-bold uppercase text-primary transition-all hover:opacity-75 focus:ring focus:ring-tertiary active:opacity-[0.85] disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none'
                  data-ripple-dark='true'
                >
                  CANCELAR
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </Section>
  );
};
