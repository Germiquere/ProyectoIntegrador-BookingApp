import { Tooltip } from "@mui/material";
import { useBikesContext } from "../../../context/BikesContext";
import { SkeletonTableProducts } from "../SkeletonTableProducts";
import { BsPlusLg } from "react-icons/bs";
import { MdDelete } from "react-icons/md";
import { useCategoriesContext } from "../../../context/CategoriesContext";

export const TableCategories = () => {
    const { categoriesData, deleteACategory, handleDeleteImages, loading } =
        useCategoriesContext();
    const { fetchData } = useBikesContext();
    const handleDeleteCategory = async (category) => {
        const imageTodelete = [category.imagen];
        console.log(imageTodelete);
        await handleDeleteImages(imageTodelete);
        const deleted = await deleteACategory(category.categoriaId);
        fetchData();
    };

    return (
        <div className="flex flex-col gap-2">
            {categoriesData.map((category) => (
                <div key={category.categoriaId}>
                    <div
                        //   onClick={() => {
                        //       setOpenEditProductModal(true);
                        //       setFormState({
                        //           bicicletaId: bike.bicicletaId,
                        //           nombre: bike.nombre,
                        //           descripcion: bike.descripcion,
                        //           precioAlquilerPorDia:
                        //               bike.precioAlquilerPorDia,
                        //           categoria: {
                        //               categoriaId:
                        //                   bike.categoria.categoriaId,
                        //           },
                        //           imagenes: bike.imagenes,
                        //       });
                        //   }}
                        className="cursor-pointer flex gap-8 justify-between items-center rounded-xl text-xs p-2 bg-white shadow-md border border-gray-200 relative hover:bg-gray-100"
                    >
                        <div className="flex gap-2 flex-1 items-center">
                            <div className="w-10 h-10 rounded-xl overflow-hidden">
                                <img
                                    src={category.imagen.url}
                                    alt={category.nombre}
                                    className="w-full h-full object-cover"
                                />
                            </div>
                            <p>{category.nombre}</p>
                        </div>
                        <Tooltip title="Borrar">
                            <button
                                className="flex text-gray-500 items-center justify-center middle none center rounded-full  h-10 w-10 font-sans text-xs font-bold uppercase hover:bg-blackOpacity1  transition-all  active:bg-tertiary disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none "
                                data-ripple-dark="true"
                                disabled={loading}
                                onClick={() => {
                                    handleDeleteCategory(category);
                                }}
                            >
                                <MdDelete className="text-xl" />
                            </button>
                        </Tooltip>
                    </div>
                </div>
            ))}
        </div>
    );
};
