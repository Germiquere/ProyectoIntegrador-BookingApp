import React, { useContext } from "react";
import { Outlet } from "react-router-dom";
import { Header } from "../components/header/Header";
import { Footer } from "../components/footer/Footer";
import { ResponsiveCalendarAndSearch } from "../components/sectionCalendarAndSearch/ResponsiveCalendarAndSearch";
import { CalendarAndSearchContext } from "../../context/CalendarSearchContext";
import { CreateProductModal } from "../components/admin/CreateProductModal";
import { useBikesContext } from "../../context/BikesContext";

export const BookingLayout = () => {
    const { openCalendarAndSearch } = useContext(CalendarAndSearchContext);
    const { openNewProductModal } = useBikesContext();
    return (
        <>
            {openCalendarAndSearch && <ResponsiveCalendarAndSearch />}
            {openNewProductModal && <CreateProductModal />}
            {/* <ResponsiveCalendarAndSearch /> */}
            <Header />
            <main className=" md:min-h-[calc(100vh-228px)] ">
                <Outlet />
            </main>
            <Footer />
        </>
    );
};
