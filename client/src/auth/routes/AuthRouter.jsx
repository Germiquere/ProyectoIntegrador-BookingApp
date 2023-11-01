import { Navigate, Route, Routes } from "react-router-dom";
import { LoginPage } from "../pages/LoginPage";
import { RegisterPage } from "../pages/RegisterPage";
import { AuthLayout } from "../layout/AuthLayout";

export const AuthRouter = () => {
    return (
        <Routes>
            <Route element={<AuthLayout />}>
                <Route index path="login" element={<LoginPage />} />
                <Route path="register" element={<RegisterPage />} />
                <Route path="/*" element={<Navigate to="login" />} />
            </Route>
        </Routes>
    );
};
