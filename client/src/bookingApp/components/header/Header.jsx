import React, { useState } from "react";
import { FaBars } from "react-icons/fa";
import { Link } from "react-router-dom";

export const Header = () => {
  let [open, setOpen] = useState(false);
  return (
    <header className='px-[5%] lg:px-[2%] shadow-lg top-0 w-full bg-white sticky z-30 overflow-hidden'>
      {/* <div className='absolute bottom-0  w-full shadow-2xl shadow-secondary border-b-2 border-primary animate-borderAnimation'></div> */}
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

        <div
          onClick={() => setOpen(!open)}
          className=' text-3xl  cursor-pointer lg:hidden h-full flex  text-primary'
        >
          <FaBars name={open ? "close" : "menu"}></FaBars>
        </div>

        <div
          className={`flex flex-col items-start lg:flex lg:flex-row  pb-6 lg:items-center lg:pb-2 pt-4 absolute lg:static  bg-white lg:z-auto gap-4  left-0 w-full  lg:w-auto md:pl-2 pl-9 transition-all duration-500 ease-in ${
            open ? "top-[50px] " : "top-[-490px]"
          }`}
        >
          <button
            className='middle none center rounded-full border border-primary w-[120px] h-[40px] py-3 px-6 font-sans text-xs font-bold uppercase text-primary transition-all hover:opacity-75 focus:ring focus:ring-tertiary active:opacity-[0.85] disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none'
            data-ripple-dark='true'
          >
            Login
          </button>
          <button
            className='middle none center rounded-full bg-primary w-[120px] h-[40px] py-3 px-6 font-sans text-xs font-bold uppercase text-white shadow-sm transition-all hover:shadow-secondary active:opacity-[0.85] active:shadow-none disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none'
            data-ripple-light='true'
          >
            Register
          </button>
        </div>
      </div>
    </header>
  );
};

export default Header;
