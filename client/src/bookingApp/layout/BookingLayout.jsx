import React, { useContext } from "react";
import { Outlet } from "react-router-dom";
import { Header } from "../components/header/Header";
import { Footer } from "../components/footer/Footer";
import { ResponsiveCalendarAndSearch } from "../components/sectionCalendarAndSearch/ResponsiveCalendarAndSearch";
import { CalendarAndSearchContext } from "../../context/CalendarSearchContext";
import { useBikesContext } from "../../context/BikesContext";
import { BsWhatsapp } from "react-icons/bs";
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
                <div className="fixed w-10 h-10 sm:w-14 sm:h-14 bg-[#25d366] rounded-full flex items-center justify-center text-white text-xl sm:text-3xl cursor-pointer bottom-10 right-5 md:right-24  md:bottom-24 shadow-md">
                    <a
                        className=""
                        href="https://wa.me/3496460785?text=Hola!%20Quisiera%20consultar%20por%20una%20reserva"
                        target="_blank"
                    >
                        <BsWhatsapp />
                    </a>
                </div>
            </main>
            {!openCalendarAndSearch && <Footer />}
        </>
    );
};
