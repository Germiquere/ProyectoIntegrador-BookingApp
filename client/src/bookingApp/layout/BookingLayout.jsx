import React, { useContext } from "react";
import { Outlet } from "react-router-dom";
import { Header } from "../components/header/Header";
import { Footer } from "../components/footer/Footer";
import { ResponsiveCalendarAndSearch } from "../components/sectionCalendarAndSearch/ResponsiveCalendarAndSearch";
import { CalendarAndSearchContext } from "../../context/CalendarSearchContext";

export const BookingLayout = () => {
    const { openCalendarAndSearch } = useContext(CalendarAndSearchContext);
    return (
        <>
            {/* {openCalendarAndSearch && <ResponsiveCalendarAndSearch />} */}
            <ResponsiveCalendarAndSearch />
            <Header />
            <main>
                <Outlet />
            </main>
            <Footer />
        </>
    );
};
