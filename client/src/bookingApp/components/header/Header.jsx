import React from "react";
import { Link } from "react-router-dom";

export const Header = () => {
  return (
    <header className='pr-8 pl-8 pt-4 pb-4 flex items-center justify-around border-b shadow-lg top-0 w-full sticky bg-stone-200'>
      <div>
        <a href='/'>
          <img className='w-30 h-20 ' src='/src/assets/bike.png' alt='' />
        </a>
        <h2 className='font-bold'>BikeMeNow</h2>
      </div>

      <div className='visibility: hidden lg:flex gap-5 font-bold text-xl'>
        <div>
          <Link to='/'>Bikes</Link>
        </div>
        <div>
          <Link to='/'>Productos</Link>
        </div>
        <div>
          <Link to='/'>Categorias</Link>
        </div>
        <div>
          <Link to='/'>Ayuda</Link>
        </div>
      </div>

      <div className='space-x-4 flex items-center justify-center '>
        <button
          className='middle none center mr-3 rounded-lg border border-blue-500 py-3 px-6 font-sans text-xs font-bold uppercase text-blue-500 transition-all hover:opacity-75 focus:ring focus:ring-blue-200 active:opacity-[0.85] disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none '
          data-ripple-dark='true'
        >
          Login
        </button>
        <button
          className='middle none center mr-3 rounded-lg border border-blue-500 py-3 px-6 font-sans text-xs font-bold uppercase text-blue-500 transition-all hover:opacity-75 focus:ring focus:ring-blue-200 active:opacity-[0.85] disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none  '
          data-ripple-dark='true'
        >
          Register
        </button>
      </div>
    </header>
  );
};
