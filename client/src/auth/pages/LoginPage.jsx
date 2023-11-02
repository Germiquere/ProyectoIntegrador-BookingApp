import { Link } from 'react-router-dom';
import Section from '../../bookingApp/components/Section';
import React, { useState } from 'react';
import { VscEyeClosed, VscEye } from 'react-icons/vsc';

export const LoginPage = () => {
  const [showPassword, setShowPassword] = useState(false);
  return (
    <>
      <Section>
        <div className='max-w-[1200px] mx-auto flex flex-col items-center gap-11'>
          <h2 className='text-primary font-bold text-2xl'>Iniciar sesión</h2>

          <div className='relative h-11  min-w-[230px] flex flex-col sm:min-w-[500px] '>
            <label className='font-semibold mb-2'>Correo electronico</label>
            <input
              id='email-address'
              name='email'
              type='email'
              required
              className='shadow-md rounded-lg peer h-full  p-2 font-sans text-sm font-normal text-blue-gray-700 outline outline-0 transition-all  focus:outline-0  disabled:bg-blue-gray-50'
              placeholder='BikeMeNow@gmail.com'
            />
          </div>
          <div className='relative h-11 min-w-[230px] flex flex-col sm:min-w-[500px]'>
            <label className='font-semibold mb-2'>Contraseña</label>
            <input
              id='password'
              name='password'
              type={showPassword ? 'text' : 'password'}
              required
              className='shadow-md rounded-lg peer h-full p-2 font-sans text-sm font-normal text-blue-gray-700 outline outline-0 transition-all  focus:outline-0  disabled:bg-blue-gray-50'
              placeholder='**************'
            />
            <span
              className='absolute inset-y-10 right-0 pr-3 flex items-center cursor-pointer'
              onClick={() => setShowPassword(!showPassword)}
            >
              {showPassword ? <VscEyeClosed /> : <VscEye />}
            </span>
          </div>
          <div className='flex flex-col gap-4 pt-6'>
            <button
              className='middle none center mr-3  rounded-full bg-primary py-3 px-20 font-sans text-xs font-bold uppercase text-white shadow-sm  transition-all  hover:shadow-secondary  active:opacity-[0.85] active:shadow-none disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none'
              data-ripple-light='true'
            >
              Ingresar
            </button>
            <p>
              ¿Aún no tienes cuenta?{' '}
              <Link to='/auth/register' className='text-primary'>
                Registrate
              </Link>
            </p>
          </div>
        </div>
      </Section>
      ;
    </>
  );
};
