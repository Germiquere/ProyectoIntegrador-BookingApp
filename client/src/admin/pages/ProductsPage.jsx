import Section from "../../bookingApp/components/Section";
import { useBikesContext } from "../../context/BikesContext";
import { CreateProductModal } from "../components/CreateProductModal";
import { EditProductModal } from "../components/EditProductModal";
import { TableHeader } from "../components/TableHeader";
import { TableProducts } from "../components/TableProducts";
export const ProductsPage = () => {
    const { openNewProductModal, openEditProductModal } = useBikesContext();
    return (
        <div className=" lg:flex flex-col mt-3 gap-3">
            {openNewProductModal && <CreateProductModal />}
            {openEditProductModal && <EditProductModal />}
            <TableHeader />
            <TableProducts />
        </div>
    );
};
