import { Navigate, Route, Routes } from "react-router-dom";
import { ProductsPage } from "../pages/ProductsPage";
import { AdminLayout } from "../layout/AdminLayout";
import { Categories } from "../../bookingApp/pages/Categories";
import { UsersPage } from "../pages/UsersPage";

export const AdminRouter = () => {
    return (
        <Routes>
            <Route element={<AdminLayout />}>
                <Route path="/" element={<ProductsPage />} />
                <Route path="/usuarios" element={<UsersPage />} />
            </Route>
        </Routes>
    );
};
