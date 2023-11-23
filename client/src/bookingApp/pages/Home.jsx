import { CategoriesAndRecommended } from "../components/sectionCategoriesAndRecommended/CategoriesAndRecommended";
import { CalendarAndSearch } from "../components/sectionCalendarAndSearch/CalendarAndSearch";
import { Products } from "../components/sectionProducts/Products";
import { Helmet } from "react-helmet";
export const Home = () => {
    return (
        <>
            <Helmet>
                <title>Bike Me Now</title>
                {/* <link rel="canonical" href="http://mysite.com/example" /> */}

                <meta
                    property="og:image"
                    content="https://res.cloudinary.com/djslo5b3u/image/upload/v1698971497/BikeMeNow_BlueAlpha_svidg9.png"
                />
                <meta property="og:title" content="Bike Me Now" />
                <meta
                    property="og:description"
                    content="Descubre la libertad sobre dos ruedas con nuestro servicio de alquiler de bicicletas. Explora tu ciudad o destinos increíbles mientras reservas la bicicleta perfecta para cada aventura. ¡Siente el viento en tu rostro y pedalea hacia tus próximas experiencias inolvidables!"
                />
            </Helmet>
            <CalendarAndSearch />
            <CategoriesAndRecommended />
            <Products />
        </>
    );
};
