import Section from "../../../bookingApp/components/Section";
import { CreateProductModal } from "../components/CreateProductModal";
import { useBikesContext } from "../../../context/BikesContext";
import { TopBar } from "../components/topBar";

export const ProductsPage = () => {
    const { openNewProductModal } = useBikesContext();
    return (
        <Section>
            <div className="max-w-[1200px] mx-auto flex flex-col mt-3">
                {openNewProductModal && <CreateProductModal />}
                <TopBar />
            </div>
        </Section>
    );
};
