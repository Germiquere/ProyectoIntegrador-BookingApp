import React, { useEffect } from "react";
import { Outlet } from "react-router-dom";
import Section from "../../bookingApp/components/Section";
import { TopBar } from "../components/TopBar";
import { useCategoriesContext } from "../../context/CategoriesContext";
import { Manage } from "../components/manage/Manage";
import { useUsersContext } from "../../context/UsersContext";
import { useCharacteristicsContext } from "../../context/CharacteristicsContext";
import { Helmet } from "react-helmet";

export const AdminLayout = () => {
    const { openManageCategories, setOpenManageCategories } =
        useCategoriesContext();
    const { fetchData: fetchCaractsData } = useCharacteristicsContext();
    const { fetchUsersData } = useUsersContext();
    useEffect(() => {
        fetchUsersData();
        fetchCaractsData();
    }, []);

    return (
        <Section>
            <Helmet>
                <title>Administracion Bike Me Now</title>
                {/* <link rel="canonical" href="http://mysite.com/example" /> */}

                <meta
                    property="og:image"
                    content="https://res.cloudinary.com/djslo5b3u/image/upload/v1698971497/BikeMeNow_BlueAlpha_svidg9.png"
                />
                <meta
                    property="og:title"
                    content="Administracion Bike Me Now"
                />
                <meta
                    property="og:description"
                    content="Descubre la libertad sobre dos ruedas con nuestro servicio de alquiler de bicicletas. Explora tu ciudad o destinos increíbles mientras reservas la bicicleta perfecta para cada aventura. ¡Siente el viento en tu rostro y pedalea hacia tus próximas experiencias inolvidables!"
                />
            </Helmet>
            <div className="max-w-[1200px] mx-auto lg:flex flex-col mt-3 gap-3 hidden">
                {/* {openNewProductModal && <CreateProductModal />}
                {openEditProductModal && <EditProductModal />} */}
                <TopBar />
                <Outlet />
                {/* <TableHeader /> */}
                {/* <TableProducts /> */}
            </div>
            <div className="lg:hidden max-w-[1200px] mx-auto mt-3 flex justify-center items-center min-h-[calc(100vh-350px)] md:min-h-[calc(100vh-350px)] ">
                <img
                    src="https://res.cloudinary.com/djslo5b3u/image/upload/v1698357563/404_vegexo.png"
                    alt="imagen de no disponible"
                />
            </div>
            {openManageCategories && <Manage />}
        </Section>
    );
};
