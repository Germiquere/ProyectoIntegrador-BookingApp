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
            {!openCalendarAndSearch && <Header />}
            <main
                className={`min-h-[calc(100vh-172px)]  md:min-h-[calc(100vh-148px)] ${
                    openCalendarAndSearch ? "h-screen overflow-hidden " : ""
                }  `}
            >
                <Outlet />
                {/* <IconPicker /> */}
            </main>
            {!openCalendarAndSearch && <Footer />}
        </>
    );
};
