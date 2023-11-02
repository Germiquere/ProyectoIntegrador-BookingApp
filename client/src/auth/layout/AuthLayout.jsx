import React from "react";
import Header from "../components/Header";
import { Outlet } from "react-router-dom";
import { Footer } from "../../bookingApp/components/footer/Footer";

export const AuthLayout = () => {
    return (
        <>
            <Header />
            <main className="min-h-[calc(100vh-186px)] md:min-h-[calc(100vh-150px)] flex items-center justify-center">
                <Outlet />
            </main>
            <Footer />
        </>
    );
};
