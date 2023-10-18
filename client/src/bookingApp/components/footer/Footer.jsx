import { Link } from "react-router-dom";
import { FaFacebookF, FaInstagram, FaYoutube, FaTiktok } from "react-icons/fa";

export const Footer = () => {
  return (
    <footer className='flex flex-col items-center bg-tertiary '>
      <div className='flex flex-col p-2 gap-4 md:flex-row items-center w-full justify-evenly lg:w-2/3'>
        <div className='flex flex-col items-center'>
          <a href='/'>
            <img
              className='w-[220px] h-[100px] md:w-[200px] h-[80px]'
              src='/src/assets/BikeMeNow_BlueAlpha.png'
              alt=''
            />
          </a>
        </div>

        <div className='flex flex-row justify-evenly gap-8 font-bold text-primary lg:text-l '>
          <nav>
            <ul className='hover:text-gray-400 duration-500'>
              <Link to='/'>Home</Link>
            </ul>
            <ul className='hover:text-gray-400 duration-500'>
              <Link to='/'>Categories</Link>
            </ul>
            <ul className='hover:text-gray-400 duration-500'>
              <Link to='/'>Products</Link>
            </ul>
          </nav>
          <nav>
            <ul className='hover:text-gray-400 duration-500'>
              <Link to='/'>About Us</Link>
            </ul>
            <ul className='hover:text-gray-400 duration-500'>
              <Link to='/'>Community</Link>
            </ul>
            <ul className='hover:text-gray-400 duration-500'>
              <Link to='/'>Privacy Policy</Link>
            </ul>
          </nav>
        </div>

        <div className='flex gap-6 pr-4 justify-center text-[30px] text-primary'>
          <a
            href='https://www.facebook.com/'
            target='_blank'
            className='hover:text-gray-400 duration-500'
          >
            <FaFacebookF />
          </a>
          <a
            href='https://www.instagram.com/'
            target='_blank'
            className='hover:text-gray-400 duration-500'
          >
            <FaInstagram />
          </a>
          <a
            href='https://www.youtube.com/'
            target='_blank'
            className='hover:text-gray-400 duration-500'
          >
            <FaYoutube />
          </a>
          <a
            href='https://www.tiktok.com/'
            target='_blank'
            className='hover:text-gray-400 duration-500'
          >
            <FaTiktok />
          </a>
        </div>
      </div>
      <div className='flex items-center justify-center bg-primary w-full  text-white text-xs text-center h-10  font-bold  md:text-sm'>
        © Copyright 2023 BikeMeNow. All Rights Reserved
      </div>
    </footer>
  );
};
