import { CategoriesAndRecommended } from '../components/sectionCategoriesAndRecommended/CategoriesAndRecommended';
import { CalendarAndSearch } from '../components/sectionCalendarAndSearch/CalendarAndSearch';
import { Products } from '../components/sectionProducts/Products';
import { LoginPage } from '../../auth/pages/LoginPage';

export const Home = () => {
  return (
    <>
      <CalendarAndSearch />
      <CategoriesAndRecommended />
      <Products />
      <LoginPage />
    </>
  );
};
