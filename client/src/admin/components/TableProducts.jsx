import { useBikesContext } from "../../context/BikesContext";
import { SkeletonTableProducts } from "./SkeletonTableProducts";
export const TableProducts = () => {
    const {
        loading,
        bikesData,
        setFormState,
        setOpenEditProductModal,
        formState,
    } = useBikesContext();
    console.log(formState);
    return (
        <div className="flex flex-col gap-2">
            {loading
                ? [1, 2, 3, 4, 5, 6, 7, 8, 9, 10].map((index) => (
                      <SkeletonTableProducts key={index} />
                  ))
                : bikesData.map((bike) => (
                      <div key={bike.bicicletaId}>
                          <div
                              onClick={() => {
                                  setFormState({
                                      bicicletaId: bike.bicicletaId,
                                      nombre: bike.nombre,
                                      descripcion: bike.descripcion,
                                      precioAlquilerPorDia:
                                          bike.precioAlquilerPorDia,
                                      categorias: bike.categorias,
                                      imagenes: bike.imagenes,
                                      caracteristicas: bike.caracteristicas,
                                  });
                                  console.log(bike);
                                  setOpenEditProductModal(true);
                              }}
                              className="cursor-pointer flex gap-8 justify-between items-center rounded-xl text-xs p-3 bg-white shadow-md border border-gray-200 relative hover:bg-gray-100"
                          >
                              <p className="w-10">{bike.bicicletaId}</p>
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
                              <p className="w-20 overflow-hidden">
                                  {"$ " + bike.precioAlquilerPorDia}
                              </p>
                          </div>
                      </div>
                  ))}
        </div>
    );
};
