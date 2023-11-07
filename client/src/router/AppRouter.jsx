import { Navigate, Route, Routes } from "react-router-dom";
import { AuthRouter } from "../auth/routes/AuthRouter";
import { BookingAppRouter } from "../bookingApp/routes/BookingAppRouter";
import { useUsersContext } from "../context/UsersContext";

export const AppRouter = () => {
    const { isAuthenticated } = useUsersContext();

    return (
        <Routes>
            <Route path="/*" element={<BookingAppRouter />} />

            {!isAuthenticated && (
                <Route path="/auth/*" element={<AuthRouter />} />
            )}
        </Routes>
    );
};
