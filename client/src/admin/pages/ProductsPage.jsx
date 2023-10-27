import Section from "../../bookingApp/components/Section";
import { useBikesContext } from "../../context/BikesContext";
import { CreateProductModal } from "../components/CreateProductModal";
import { EditProductModal } from "../components/EditProductModal";
import { TableHeader } from "../components/TableHeader";
import { TableProducts } from "../components/TableProducts";
import { TopBar } from "../components/TopBar";

export const ProductsPage = () => {
    const { openNewProductModal, openEditProductModal } = useBikesContext();
    return (
        <Section>
            <div className="max-w-[1200px] mx-auto lg:flex flex-col mt-3 gap-3 hidden">
                {openNewProductModal && <CreateProductModal />}
                {openEditProductModal && <EditProductModal />}
                <TopBar />
                <TableHeader />
                <TableProducts />
            </div>
            <div className="lg:hidden max-w-[1200px] mx-auto mt-3 flex justify-center items-center min-h-[calc(100vh-350px)] md:min-h-[calc(100vh-350px)] ">
                <img
                    src="https://res.cloudinary.com/djslo5b3u/image/upload/v1698357563/404_vegexo.png"
                    alt="imagen de no disponible"
                />
            </div>
        </Section>
    );
};
