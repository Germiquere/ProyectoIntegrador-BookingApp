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

export const BookingAppRouter = () => {
    return (
        <Routes>
            <Route path="/" element={<BookingLayout />}>
                <Route index element={<Home />}></Route>
                {/*----- */}
                {/* TODO: HACER QUE SEAN DINAMICAS OBTENIENDO LAS CATEGORIAS DESDE LA API */}
                {/* TODO: EN BASE AL PATH, HACER EL FETCH EN EL UNICO COMPONENTE QUE SE VA A RENDERIZAR EN VEZ DE TODOS LOS OTROS HARDCODEADOS SE TENRIA QUE LLAMAR ALGO ASI COMO CATEGORIES Y FILTRAR LAS CATEGORIAS POR EL PATH TAMBIEN*/}
                <Route path="city" element={<CityBikes />} />
                <Route path="electric" element={<EBikes />} />
                <Route path="kids" element={<KidsBikes />} />
                <Route path="mountain" element={<MountainBikes />} />
                <Route path="road" element={<RoadBikes />} />
                {/* TEST */}
                <Route path="bulbasaur" element={<Categories />} />

                {/*----- */}

                <Route path="/description/:id" element={<Description />} />
                <Route path="/*" element={<Navigate to="/" />} />
            </Route>
        </Routes>
    );
};
