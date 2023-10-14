import React from "react";
import { Navigate, Route, Routes } from "react-router-dom";
import { CityBikes } from "../pages/categories/CityBikes";
import { EBikes } from "../pages/categories/EBikes";
import { KidsBikes } from "../pages/categories/KidsBikes";
import { MountainBikes } from "../pages/categories/MountainBikes";
import { RoadBikes } from "../pages/categories/RoadBikes";
import { Home } from "../pages/Home";
import { BookingLayout } from "../layout/BookingLayout";

export const BookingAppRouter = () => {
    return (
        <Routes>
            <Route path="/" element={<BookingLayout />}>
                <Route index element={<Home />}></Route>
                <Route path="city" element={<CityBikes />} />
                <Route path="electric" element={<EBikes />} />
                <Route path="kids" element={<KidsBikes />} />
                <Route path="mountain" element={<MountainBikes />} />
                <Route path="road" element={<RoadBikes />} />
                <Route path="/*" element={<Navigate to="/" />} />
            </Route>
        </Routes>
    );
};
