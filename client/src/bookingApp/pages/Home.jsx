import { CategoriesAndRecommended } from "../components/sectionCategoriesAndRecommended/CategoriesAndRecommended";
import { CalendarAndSearch } from "../components/sectionCalendarAndSearch/CalendarAndSearch";

export const Home = () => {
    return (
        <>
            <CalendarAndSearch />
            <CategoriesAndRecommended />
        </>
    );
};
