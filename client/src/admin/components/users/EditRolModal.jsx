import { BsXLg, BsCurrencyDollar, BsCloudUpload } from "react-icons/bs";

import { Tooltip } from "@mui/material";
import { useUsersContext } from "../../../context/UsersContext";
import { useForm } from "../../../hooks/useForm";

export const EditRolModal = () => {
    const {
        setOpenEditUserModal,
        usersData,
        editUserRole,
        usersFormState,
        onInputChange,
    } = useUsersContext();
    const { usuarioId, rol } = usersFormState;
    const handleSave = async () => {
        const user = await editUserRole(usersFormState);
        setOpenEditUserModal(false);
    };
    return (
        <>
            <div
                className={` rounded-xl max-h-[600px] overflow-hidden bg-white  fixed top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 max-w-[1200px] min-w-[700px] mx-auto transition-opacity duration-200 z-50 `}
            >
                {/* HEADER */}
                <div className=" w-full h-16  flex justify-between p-3 border-b-[1px] border-gray-300 bg-primary text-white">
                    <h2 className="text-xl font-semibold flex gap-5 items-center">
                        <p>Editar usuario</p>
                        {/* {loadingBikes && <Loader className={"text-white"} />} */}
                    </h2>
                    <Tooltip title="Cerrar">
                        <button
                            className="flex  items-center justify-center middle none center rounded-full  h-10 w-10 font-sans text-xs font-bold uppercase  transition-all hover:bg-blackOpacity1 active:bg-tertiary disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none "
                            data-ripple-dark="true"
                            // // disabled={loadingBikes}
                            // onClick={() => {
                            //     setOpenEditProductModal(false);
                            //     setImageChange([]);
                            //     onResetForm();
                            // }}
                            onClick={() => {
                                setOpenEditUserModal(false);
                            }}
                        >
                            <BsXLg className="text-lg" />
                        </button>
                    </Tooltip>
                </div>
                {/* MAIN */}
                <div className=" p-5 min-h-[450px] max-h-[450px] overflow-auto mb-16">
                    <div className="w-full">
                        <h2 className="text-lg font-semibold pb-3 border-b-[1px] border-gray-300 mb-3">
                            Detalles del usuario
                        </h2>
                    </div>
                    <div className="flex gap-5">
                        {/* div de la izquierda */}
                        <div className="flex flex-col gap-3 flex-1">
                            {/* CATEGORIA */}
                            <div>
                                <label className="text-base font-semibold mb-2">
                                    Rol
                                </label>
                                {/* TODO: en base al value del select, cambia el color del texto a text-graty-400 o "" */}
                                <div
                                    className={`relative h-11 w-full min-w-[200px]  border-[1px]  border-gray-100 overflow-hidden shadow-md rounded-xl 
                                  
                                    `}
                                    // ${
                                    //     categoria.categoriaId
                                    //         ? ""
                                    //         : "text-gray-400"
                                    // }
                                >
                                    <select
                                        name="rol"
                                        value={rol}
                                        onChange={onInputChange}
                                        disabled={rol === "admin"}
                                        className="peer h-full w-full p-2 font-sans text-sm font-normal  outline outline-0 transition-all focus:outline-0 disabled:bg-blue-gray-50"
                                    >
                                        <option
                                            value=""
                                            className="text-gray-400"
                                        >
                                            Selecciona un rol
                                        </option>
                                        <option
                                            value="user"
                                            className="text-black"
                                        >
                                            User
                                        </option>
                                        <option
                                            value="admin"
                                            className="text-black"
                                        >
                                            Admin
                                        </option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                {/* FOOTER */}
                <div className="flex justify-end p-3 items-center border-t-[1px] border-gray-300 fixed bottom-0 right-0-0 w-full bg-white z-20 h-16">
                    <div className="flex gap-2">
                        <button
                            className="middle none center  rounded-full bg-primary py-2 px-6 font-sans text-xs font-bold uppercase text-white shadow-sm  transition-all  hover:shadow-secondary  active:opacity-[0.85] active:shadow-none disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none"
                            data-ripple-light="true"
                            // disabled={loadingBikes}
                            onClick={handleSave}
                        >
                            GUARDAR
                        </button>
                    </div>
                </div>
            </div>

            <div
                className={`fixed top-0 left-0 w-full h-full bg-black bg-opacity-70 transition-opacity duration-200 z-40`}
            ></div>
        </>
    );
};
