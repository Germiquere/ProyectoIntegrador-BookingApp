import { Navigate, Route, Routes } from "react-router-dom";
import { AuthRouter } from "../auth/routes/AuthRouter";
import { BookingAppRouter } from "../bookingApp/routes/BookingAppRouter";

export const AppRouter = () => {
    return (
        <Routes>
            <Route path="/*" element={<BookingAppRouter />} />
            <Route path="/auth/*" element={<AuthRouter />} />
        </Routes>
    );
};
