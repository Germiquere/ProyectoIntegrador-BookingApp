import React, { useContext } from "react";
import { Outlet } from "react-router-dom";
import { Header } from "../components/header/Header";
import { Footer } from "../components/footer/Footer";
import { ResponsiveCalendarAndSearch } from "../components/sectionCalendarAndSearch/ResponsiveCalendarAndSearch";
import { CalendarAndSearchContext } from "../../context/CalendarSearchContext";
import { useBikesContext } from "../../context/BikesContext";

export const BookingLayout = () => {
    const { openCalendarAndSearch } = useContext(CalendarAndSearchContext);
    const { openShareModal, openRatingModal } = useBikesContext();
    return (
        <>
            {openCalendarAndSearch && <ResponsiveCalendarAndSearch />}
            {/* <ResponsiveCalendarAndSearch /> */}
            {!openCalendarAndSearch && <Header />}
            <main
                className={`min-h-[calc(100vh-172px)]  md:min-h-[calc(100vh-148px)]  ${
                    openCalendarAndSearch || openShareModal || openRatingModal
                        ? "h-2  md:h-auto overflow-hidden md:overflow-auto"
                        : ""
                }  `}
            >
                <Outlet />
                {/* <IconPicker /> */}
            </main>
            {!openCalendarAndSearch && <Footer />}
        </>
    );
};
