import React, { useState } from 'react';
import { IoIosArrowDown } from 'react-icons/io';
import { Link } from 'react-router-dom';
import MenuHamburger from './MenuHamburger';

export const Header = ({ user }) => {
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);

  const toggleDropdown = () => {
    setIsDropdownOpen(!isDropdownOpen);
  };

  const renderUserLinks = () => {
    if (user) {
      return (
        <div className='flex items-center'>
          <Link className='w-10 h-10 bg-primary rounded-full flex items-center justify-center text-white text-md hover:bg-secondary mr-2'>
            {user.name.charAt(0).toUpperCase()}
          </Link>
          <Link className='text-black mr-2 font-semibold'>{user.name}</Link>
          <div className='cursor-pointer' onClick={toggleDropdown}>
            <IoIosArrowDown />
          </div>
        </div>
      );
    } else {
      return (
        <div>
          <MenuHamburger />
        </div>
      );
    }
  };

  return (
    <header className='px-[5%] lg:px-[2%] shadow-lg top-0 w-full bg-white sticky z-30 '>
      <div className='flex items-center justify-between max-w-[1200px] mx-auto  '>
        <div>
          <Link to='/'>
            <img
              className='w-full h-[50px] '
              src='/src/assets/BikeMeNow_BlueAlpha.png'
              alt=''
            />
          </Link>
        </div>
        {renderUserLinks()}
      </div>

      {isDropdownOpen && (
        <div
          className={`absolute top-[55px] right-[5px] bg-white border border-gray-300 p-2 rounded-lg shadow-lg transition-opacity  xl:right-[355px] ${
            isDropdownOpen ? 'opacity-100' : 'opacity-0'
          }duration-300 ease-in-out`}
        >
          <div className='flex flex-col items-center'>
            <div className='flex items-center'>
              <p className='w-10 h-10 bg-primary rounded-full flex items-center justify-center text-white text-md  mr-2'>
                {user.name.charAt(0).toUpperCase()}
              </p>
              <p className='text-black mr-2 font-bold'>
                {user.name} {user.apellido}
                <p className='font-semibold'>{user.email}</p>
              </p>
            </div>

            <button
              className=' middle none center  rounded-full border border-primary py-3 px-6 font-sans text-xs font-bold uppercase text-primary transition-all hover:opacity-75 focus:ring focus:ring-tertiary active:opacity-[0.85] disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none mt-4'
              data-ripple-dark='true'
            >
              Cerrar sesión
            </button>
          </div>
        </div>
      )}
    </header>
  );
};

export default Header;
