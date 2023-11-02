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
            {openCalendarAndSearch && <ResponsiveCalendarAndSearch />}
            {/* <ResponsiveCalendarAndSearch /> */}
            <Header />
            <main className="min-h-[calc(100vh-204px)] md:min-h-[calc(100vh-168px)] lg:min-h-[calc(100vh-182px)] ">
                <Outlet />
                {/* <IconPicker /> */}
            </main>
            <Footer />
        </>
    );
};
