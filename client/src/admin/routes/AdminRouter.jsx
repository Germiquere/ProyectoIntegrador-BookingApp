import { Navigate, Route, Routes } from "react-router-dom";
import { ProductsPage } from "../pages/ProductsPage";

export const AdminRouter = () => {
    return (
        <Routes>
            <Route path="/" element={<ProductsPage />} />
            {/* <Route path="usuarios" element={<Categories />} /> */}
            <Route path="/*" element={<Navigate to="/admin" />} />
        </Routes>
    );
};
