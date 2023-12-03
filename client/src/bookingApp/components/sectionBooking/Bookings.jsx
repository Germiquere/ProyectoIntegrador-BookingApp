import React, { useState } from 'react';
import Section from '../Section';
import { useBookingsContext } from '../../../context/BookingContext';
import { useBikesContext } from '../../../context/BikesContext';
import { useUsersContext } from '../../../context/UsersContext';
import { IoIosArrowBack } from 'react-icons/io';
import { Link, useNavigate } from 'react-router-dom';
import { Loader } from '../../../ui/Loader';
import { useCalendarAndSearchContext } from '../../../context/CalendarSearchContext';
import { parse, format } from 'date-fns';

export const Bookings = () => {
  const { addNewBooking, loading, setIsReserved } = useBookingsContext();
  const { formState } = useCalendarAndSearchContext();
  const { bikeById } = useBikesContext();
  const { userData } = useUsersContext();
  const navigate = useNavigate();

  const goBack = () => {
    navigate(-1);
  };

  console.log(formState.startDate);

  const parsedStartDate = parse(formState.startDate, 'd-MM-yyyy', new Date());
  const parsedEndDate = parse(formState.endDate, 'd-MM-yyyy', new Date());

  // Maneja el envío del formulario de reserva
  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      // Llamada a la función para agregar una nueva reserva

      const postingBooking = await addNewBooking(
        userData.usuarioId,
        bikeById.bicicletaId,
        format(parsedStartDate, 'yyyy-MM-dd'),
        format(parsedEndDate, 'yyyy-MM-dd')
      );

      if (postingBooking) {
        navigate('/bookings/verification');
      }
    } catch (error) {
      console.error('Error al agregar reserva:', error);
    }
  };

  return (
    <Section>
      <div className='max-w-[1200px] mx-auto flex flex-col gap-6 pt-8 '>
        <div className='flex gap-2 items-center'>
          <div>
            <button onClick={goBack}>
              <IoIosArrowBack />
            </button>
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

            {/* Detalles de la bicicleta */}
            <h3 className='font-extrabold pt-2'>{bikeById.nombre}</h3>
            {
              <div className='flex flex-col pt-2'>
                {/* <p className='font-semibold'>
                Fecha de inicio
                <span className='font-light pl-2'>{formState.startDate}</span>
              </p>
              <p className='font-semibold pb-4'>
                Fecha de finalizacion
                <span className='font-light pl-2'>{formState.endDate}</span>
              </p> */}
                <p className='border-t border-gray-200 pt-4 font-semibold'>
                  Total (ARG)
                  <span className='font-light pl-2'>
                    ${bikeById.precioAlquilerPorDia}
                  </span>
                </p>
              </div>
            }
          </div>

          {/* Formulario de reserva */}
          <div className=' flex flex-col w-auto relative'>
            <h3 className='font-extrabold p-2'>Datos de la reserva</h3>
            <form onSubmit={handleSubmit}>
              <div className='flex flex-col items-start p-2'>
                <label htmlFor='fechaInicio' className='font-semibold'>
                  Fecha de Inicio
                  <span className='font-light pl-4'>{formState.startDate}</span>
                </label>
                <input
                  type='hidden'
                  id='fechaInicio'
                  name='fechaInicio'
                  value={formState.startDate}
                />

                <label htmlFor='fechaFin' className='font-semibold'>
                  Fecha de Fin
                  <span className='font-light pl-8'>{formState.endDate}</span>
                </label>
                <input
                  type='hidden'
                  id='fechaFin'
                  name='fechaFin'
                  value={formState.endDate}
                />
              </div>

              {/* Detalles del usuario */}
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

              {/* Detalles de pago y precio total */}
              <div className='border-t border-gray-200 p-2'>
                <p className='font-semibold'>
                  Método de pago
                  <span className='font-light pl-2'>Efectivo</span>
                </p>
                <p className='font-semibold'>
                  Precio Total
                  <span className='font-light pl-10'>
                    ${bikeById.precioAlquilerPorDia}
                  </span>
                </p>
              </div>

              <div className='flex flex-col md:flex-row md:justify-center xl:flex-row xl:justify-evenly items-center gap-2  pt-12'>
                <button
                  className=' middle none center  rounded-full bg-primary py-3 px-6 font-sans text-xs font-bold uppercase text-white shadow-sm  transition-all  hover:shadow-secondary  active:opacity-[0.85] active:shadow-none disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none w-full self-end
                  absolute bottom-0'
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
              </div>
            </form>
          </div>
        </div>
      </div>
    </Section>
  );
};
