import React, { useContext, useState } from 'react';
import { Outlet } from 'react-router-dom';
import { Header } from '../components/header/Header';
import { Footer } from '../components/footer/Footer';
import { ResponsiveCalendarAndSearch } from '../components/sectionCalendarAndSearch/ResponsiveCalendarAndSearch';
import { CalendarAndSearchContext } from '../../context/CalendarSearchContext';

export const BookingLayout = () => {
  const { openCalendarAndSearch } = useContext(CalendarAndSearchContext);

  const [user, setUser] = useState({
    name: 'Joaquin',
  });
  /* 
  const user = null; */

  return (
    <>
      {openCalendarAndSearch && <ResponsiveCalendarAndSearch />}

      {/* <ResponsiveCalendarAndSearch /> */}
      <Header user={user} />
      <main className=' md:min-h-[calc(100vh-228px)]  '>
        <Outlet />
      </main>
      <Footer />
    </>
  );
};
