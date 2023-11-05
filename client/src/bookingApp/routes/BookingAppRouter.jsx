import React from "react";
import { Navigate, Route, Routes } from "react-router-dom";
import { Home } from "../pages/Home";
import { BookingLayout } from "../layout/BookingLayout";
import { Description } from "../pages/Description";
import { Categories } from "../pages/Categories";
import { AdminRouter } from "../../admin/routes/AdminRouter";
import { useCategoriesContext } from "../../context/CategoriesContext";
import { SearchProducts } from "../pages/SearchProducts";

export const BookingAppRouter = () => {
    const { categoriesData } = useCategoriesContext();
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
                <Route path="admin/*" element={<AdminRouter />} />
                <Route path="/description/:id" element={<Description />} />
                <Route path="/items" element={<SearchProducts />} />

                <Route path="/*" element={<Navigate to="/" />} />
            </Route>
        </Routes>
    );
};
