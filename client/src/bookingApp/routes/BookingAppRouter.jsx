import React from 'react';
import { Navigate, Route, Routes } from 'react-router-dom';
import { Home } from '../pages/Home';
import { BookingLayout } from '../layout/BookingLayout';
import { Description } from '../pages/Description';
import { Categories } from '../pages/Categories';
import { AdminRouter } from '../../admin/routes/AdminRouter';
import { useCategoriesContext } from '../../context/CategoriesContext';
import { SearchProducts } from '../pages/SearchProducts';
import { useUsersContext } from '../../context/UsersContext';
import Favorites from '../pages/Favorites';
import { Bookings } from '../components/sectionBooking/Bookings';
export const BookingAppRouter = () => {
  const { categoriesData } = useCategoriesContext();
  const { isAuthenticated, rol } = useUsersContext();
  // console.log(categoriesData[0].categoriaId);
  return (
    <Routes>
      <Route path='/' element={<BookingLayout />}>
        <Route index element={<Home />}></Route>
        {categoriesData &&
          categoriesData.map((category) => (
            <Route
              key={category.categoriaId}
              path={category.nombre.toLowerCase()}
              element={<Categories />}
            />
          ))}
        {isAuthenticated && rol === 'admin' && (
          <Route path='admin/*' element={<AdminRouter />} />
        )}
        {isAuthenticated && <Route path='/favorites' element={<Favorites />} />}
        {isAuthenticated && <Route path='/bookings' element={<Bookings />} />}

        <Route path='/description/:id' element={<Description />} />
        <Route path='/items' element={<SearchProducts />} />

        <Route path='/*' element={<Navigate to='/' />} />
      </Route>
    </Routes>
  );
};
