import { CategoriesAndRecommended } from "../components/sectionCategoriesAndRecommended/CategoriesAndRecommended";
import { CalendarAndSearch } from "../components/sectionCalendarAndSearch/CalendarAndSearch";
import { Products } from "../components/sectionProducts/Products";

export const Home = () => {
  return (
    <>
      <CalendarAndSearch />
      <CategoriesAndRecommended />
      <Products />
    </>
  );
};
