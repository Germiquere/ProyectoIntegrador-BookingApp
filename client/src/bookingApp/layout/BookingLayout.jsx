import React, { useContext } from 'react';
import { Outlet } from 'react-router-dom';
import { Header } from '../components/header/Header';
import { Footer } from '../components/footer/Footer';
import { ResponsiveCalendarAndSearch } from '../components/sectionCalendarAndSearch/ResponsiveCalendarAndSearch';
import { CalendarAndSearchContext } from '../../context/CalendarSearchContext';

export const BookingLayout = () => {
  const { openCalendarAndSearch } = useContext(CalendarAndSearchContext);

  const user = {
    name: 'Joaquin',
    apellido: 'Braicovich',
    email: 'joquintbraicovich@gmail.com',
  };

  return (
    <>
      {openCalendarAndSearch && <ResponsiveCalendarAndSearch />}
      {/* <ResponsiveCalendarAndSearch /> */}
      <Header user={user} />
      <main className='min-h-[calc(100vh-186px)] md:min-h-[calc(100vh-148px)] '>
        <Outlet />
        {/* <IconPicker /> */}
      </main>
      <Footer />
    </>
  );
};
