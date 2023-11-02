import { useRef } from "react";
import { Tooltip } from "@mui/material";
import {
    BsXLg,
    BsCurrencyDollar,
    BsCloudUpload,
    BsPlusLg,
} from "react-icons/bs";
import { TableCategories } from "./TableCategories";
export const ManageCategories = () => {
    const fileInputRef = useRef(null);
    return (
        <div className="flex flex-col gap-5">
            <div className="w-full">
                <h2 className="text-lg font-semibold pb-3 border-b-[1px] border-gray-300">
                    Detalles de la categoría
                </h2>
            </div>
            <div className="flex gap-5 ">
                <div className="flex gap-2 flex-1">
                    <div className="w-full">
                        <label className="text-base font-semibold mb-2">
                            Categoría *
                        </label>
                        <div
                            className={`relative h-11 w-full min-w-[200px] shadow-md rounded-xl border-[1px] border-gray-100 overflow-hidden `}
                        >
                            <input
                                className={` peer h-full w-full  p-2 font-sans text-sm font-normal text-blue-gray-700 outline outline-0 transition-all  focus:outline-0  disabled:bg-blue-gray-50`}
                                placeholder="Montaña"
                                type="text"
                                // value={nombre}
                                name="nombre"
                                // onChange={handleInputChange}
                            />
                        </div>
                        {/* <p
                            className={`pt-1 text-xs text-red-500 ${
                                erros.nombre ? "block" : "hidden"
                            }`}
                        >
                            Campo obligatorio
                        </p>
                        <p
                            className={`pt-1 text-xs text-red-500 ${
                                error.status === 409 ? "block" : "hidden"
                            }`}
                        >
                            Ya existe un producto con ese nombre
                        </p> */}
                    </div>
                </div>
                {/* div de la derecha */}

                <div className="flex flex-1 gap-20 items-center">
                    <div>
                        <p className="text-base font-semibold ">
                            Cargar imágen *
                        </p>
                        <div className="grid grid-cols-2">
                            <input
                                type="file"
                                multiple
                                accept="image/*"
                                className="hidden"
                                // onChange={onFileInputChange}
                                ref={fileInputRef}
                            />
                            <Tooltip title="Cargar imágen">
                                <div className=" w-16 h-16 flex items-center justify-center shadow-md rounded-xl  border-[1px] border-gray-100">
                                    <BsCloudUpload
                                        className="text-xl text-gray-400 cursor-pointer"
                                        onClick={() =>
                                            fileInputRef.current.click()
                                        }
                                    />
                                </div>
                            </Tooltip>
                            {/* <div
                                className="relative group  w-16 h-16  shadow-md border-[1px] border-gray-100 overflow-hidden text-white"
                                // key={image.lastModified}
                            >
                                <img
                                    className="w-full  h-full object-cover pointer-events-none"
                                    // src={URL.createObjectURL(image)}
                                    alt=""
                                />
                                <div className="w-full h-full absolute bg-grayTertiary top-0 opacity-0 group-hover:opacity-50 "></div>

                                <BsXLg
                                    className=" bg-primary  opacity-40 group-hover:opacity-100  p-1 rounded-full text-2xl   absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 z-20  cursor-pointer"
                                    // onClick={() => {
                                    //     deleteImageChange(image.lastModified);
                                    //     console.log(image);
                                    // }}
                                />
                            </div> */}
                        </div>
                    </div>
                    <Tooltip title="Añadir Categoría">
                        <button
                            className="flex text-white items-center justify-center middle none center rounded-full  h-10 w-10 font-sans text-xs font-bold uppercase  bg-primary transition-all  active:bg-tertiary disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none "
                            data-ripple-dark="true"
                            // disabled={loadingBikes}
                            // onClick={() => {
                            //     setOpenManageCategories(false);
                            // }}
                        >
                            <BsPlusLg className="text-xl" />
                        </button>
                    </Tooltip>
                </div>
            </div>
            {/* CATEGORIAS */}

            <TableCategories />
        </div>
    );
};
