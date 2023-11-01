import React from "react";
import { Navigate, Route, Routes } from "react-router-dom";
import { CityBikes } from "../pages/categories/CityBikes";
import { EBikes } from "../pages/categories/EBikes";
import { KidsBikes } from "../pages/categories/KidsBikes";
import { MountainBikes } from "../pages/categories/MountainBikes";
import { RoadBikes } from "../pages/categories/RoadBikes";
import { Home } from "../pages/Home";
import { BookingLayout } from "../layout/BookingLayout";
import { Description } from "../pages/Description";
import { Categories } from "../pages/Categories";
import { AdminRouter } from "../../admin/routes/AdminRouter";
import { useCategoriesContext } from "../../context/CategoriesContext";

export const BookingAppRouter = () => {
    const { categoriesData } = useCategoriesContext();
    console.log(categoriesData);
    return (
        <Routes>
            <Route path="/" element={<BookingLayout />}>
                <Route index element={<Home />}></Route>
                {categoriesData &&
                    categoriesData.map((category) => (
                        <Route
                            key={category.categoriaId}
                            path={category.nombre.toLowerCase()}
                            element={<Categories />}
                        />
                    ))}
                {/* TEST */}
                <Route path="admin/*" element={<AdminRouter />} />

                {/*----- */}
                <Route path="/description/:id" element={<Description />} />
                <Route path="/*" element={<Navigate to="/" />} />
            </Route>
        </Routes>
    );
};
