import { Tooltip } from "@mui/material";
import { useBikesContext } from "../../../context/BikesContext";
import { SkeletonTableProducts } from "../SkeletonTableProducts";
import { BsPlusLg } from "react-icons/bs";
import { MdDelete } from "react-icons/md";

export const TableCategories = () => {
    const { loading, bikesData, setFormState, setOpenEditProductModal } =
        useBikesContext();
    return (
        <div className="flex flex-col gap-2">
            {loading
                ? [1, 2, 3, 4, 5, 6, 7, 8, 9, 10].map((index) => (
                      <SkeletonTableProducts key={index} />
                  ))
                : bikesData.map((bike) => (
                      <div key={bike.bicicletaId}>
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
                                      {bike.imagenes[0] && (
                                          <img
                                              src={bike.imagenes[0].url}
                                              alt={bike.nombre}
                                              className="w-full h-full object-cover"
                                          />
                                      )}
                                  </div>
                                  <p>{bike.nombre}</p>
                              </div>
                              <Tooltip title="Borrar">
                                  <button
                                      className="flex text-gray-500 items-center justify-center middle none center rounded-full  h-10 w-10 font-sans text-xs font-bold uppercase hover:bg-blackOpacity1  transition-all  active:bg-tertiary disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none "
                                      data-ripple-dark="true"
                                      // disabled={loadingBikes}
                                      // onClick={() => {
                                      //     setOpenManageCategories(false);
                                      // }}
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
