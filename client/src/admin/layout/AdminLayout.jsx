import React, { useEffect } from "react";
import { Outlet } from "react-router-dom";
import Section from "../../bookingApp/components/Section";
import { TopBar } from "../components/TopBar";
import { useCategoriesContext } from "../../context/CategoriesContext";
import { Manage } from "../components/manage/Manage";
import { useUsersContext } from "../../context/UsersContext";
import { useCharacteristicsContext } from "../../context/CharacteristicsContext";

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
