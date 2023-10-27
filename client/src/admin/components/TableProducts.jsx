import { useEffect, useRef, useState } from "react";
import {
    BsXLg,
    BsCurrencyDollar,
    BsCloudUpload,
    BsThreeDotsVertical,
} from "react-icons/bs";
import { MdOutlineDeleteForever } from "react-icons/md";
import { useBikesContext } from "../../context/BikesContext";
import { SkeletonTableProducts } from "./SkeletonTableProducts";
import { ConfirmDelete } from "./ConfirmDelete";

export const TableProducts = () => {
    const {
        loading,
        bikesData,
        setFormState,
        setOpenEditProductModal,
        handleDeleteImages,
        deleteABike,
        openConfirmDelete,
        setOpenConfirmDelete,
    } = useBikesContext();

    const divRef = useRef(null);

    //  FUNCION PARA ELIMINAR UN PRODUCTO  CON SUS IMAGENES
    const handleDeleteProduct = async (bike) => {
        const { imagenes, bicicletaId } = bike;
        await handleDeleteImages(imagenes);
        const deleted = await deleteABike(bicicletaId);
        if (deleted && deleted.statusCode !== 409) {
            setOpenConfirmDelete(false);
        }
    };
    // TRACKER SI EL DIV DE BORRAR ESTA ABIERTO O NO

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
                                  setOpenEditProductModal(true);
                                  setFormState({
                                      bicicletaId: bike.bicicletaId,
                                      nombre: bike.nombre,
                                      descripcion: bike.descripcion,
                                      precioAlquilerPorDia:
                                          bike.precioAlquilerPorDia,
                                      categoria: {
                                          categoriaId:
                                              bike.categoria.categoriaId,
                                      },
                                      imagenes: bike.imagenes,
                                  });
                              }}
                              className="cursor-pointer flex gap-8 justify-between items-center rounded-xl text-xs p-3 bg-white shadow-md border border-gray-200 relative"
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

                              <div
                                  className="w-16 flex justify-center "
                                  ref={divRef}
                              >
                                  <button
                                      className="flex items-center justify-center middle none center rounded-full h-7 w-7 font-sans text-xs font-bold uppercase transition-all hover:bg-blackOpacity1 active:bg-tertiary disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none"
                                      data-ripple-dark="true"
                                      onClick={(e) => {
                                          e.stopPropagation();
                                          setOpenConfirmDelete(true);
                                      }}
                                  >
                                      <MdOutlineDeleteForever className="text-xl" />
                                  </button>
                              </div>
                              {/* CONFIRMDELETE MODAL */}
                          </div>
                          {openConfirmDelete && (
                              <div
                                  onClick={(e) => {
                                      e.stopPropagation();
                                  }}
                              >
                                  <div
                                      className={` rounded-xl h-96 overflow-hidden bg-white  fixed top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 w-72 mx-auto transition-opacity duration-200 z-50 `}
                                  >
                                      <div className="bg-primary w-full h-44 flex justify-center items-center text-white">
                                          <MdOutlineDeleteForever className="text-9xl" />
                                      </div>
                                      <div className="flex flex-col items-center justify-around py-10 text-center">
                                          <h2 className="pb-10 text-2xl font-semibold">
                                              Estas seguro que deseas eliminar
                                              este producto?
                                          </h2>
                                          <div className="flex gap-2 justify-center">
                                              <button
                                                  className="middle none center rounded-full border border-primary py-3 px-6 font-sans text-xs font-bold uppercase text-primary transition-all hover:opacity-75 focus:ring focus:ring-tertiary active:opacity-[0.85] disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none"
                                                  data-ripple-dark="true"
                                                  onClick={() => {
                                                      handleDeleteProduct(bike);
                                                  }}
                                              >
                                                  CONFIRMAR
                                              </button>
                                              <button
                                                  className="middle none center mr-3 rounded-full bg-primary py-3 px-6 font-sans text-xs font-bold uppercase text-white shadow-sm  transition-all  hover:shadow-secondary  active:opacity-[0.85] active:shadow-none disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none"
                                                  data-ripple-light="true"
                                                  onClick={() => {
                                                      setOpenConfirmDelete(
                                                          false
                                                      );
                                                  }}
                                              >
                                                  CANCELAR
                                              </button>
                                          </div>
                                      </div>
                                  </div>
                                  <div
                                      className={`fixed top-0 left-0 w-full h-full bg-black bg-opacity-10 transition-opacity duration-200 z-40`}
                                  ></div>
                              </div>
                          )}
                          {/* {clickedOutside &&
                              deleteDivVisibility[bike.bicicletaId] &&
                              toggleDeleteDiv(bike.bicicletaId)} */}
                      </div>
                  ))}
        </div>
    );
};
