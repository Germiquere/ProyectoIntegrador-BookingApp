import React from "react";
import Header from "../components/Header";
import { Outlet } from "react-router-dom";
import { Footer } from "../../bookingApp/components/footer/Footer";

export const AuthLayout = () => {
    return (
        <>
            <Header />
            <main>
                <Outlet />
            </main>
            <Footer />
        </>
    );
};
