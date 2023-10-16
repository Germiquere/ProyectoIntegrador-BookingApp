import { Link } from "react-router-dom";

export const Footer = () => {
  return (
    <footer className='flex flex-col items-center border-t bg-stone-200'>
      <div className='flex flex-col p-4 gap-4 md:flex-row items-center w-full justify-evenly lg:w-2/3'>
        <div className='flex flex-col items-center'>
          <a href='/'>
            <img className='w-50 h-40' src='/src/assets/bike.png' alt='' />
          </a>
          <h2 className='font-bold'>BikeMeNow</h2>
        </div>

        <div className='flex flex-row justify-evenly gap-8 font-bold text-slate-800 lg:text-xl '>
          <nav>
            <ul>
              <Link to='/'>Home</Link>
            </ul>
            <ul>
              <Link to='/'>Categories</Link>
            </ul>
            <ul>
              <Link to='/'>Products</Link>
            </ul>
          </nav>
          <nav>
            <ul>
              <Link to='/'>About Us</Link>
            </ul>
            <ul>
              <Link to='/'>Community</Link>
            </ul>
            <ul>
              <Link to='/'>Privacy Policy</Link>
            </ul>
          </nav>
        </div>

        <div className='flex gap-6 p-4 justify-center'>
          <a href='https://www.facebook.com/' target='_blank'>
            <img
              className='w-8 h-8'
              src='/src/assets/icon-facebook.svg'
              alt=''
            />
          </a>
          <a href='https://www.instagram.com/' target='_blank'>
            <img
              className='w-8 h-8 '
              src='/src/assets/icon-instagram.svg'
              alt=''
            />
          </a>
          <a href='https://www.youtube.com/' target='_blank'>
            <img
              className='w-8 h-8 '
              src='/src/assets/icon-youtube.svg'
              alt=''
            />
          </a>
          <a href='https://www.tiktok.com/' target='_blank'>
            <img
              className='w-8 h-8 '
              src='/src/assets/icon-tiktok.svg'
              alt=''
            />
          </a>
        </div>
      </div>
      <div className='flex items-center justify-center bg-slate-900 w-full  text-stone-200 text-xs text-center h-10  font-bold  md:text-sm'>
        © Copyright 2023 BikeMeNow. All Rights Reserved
      </div>
    </footer>
  );
};
