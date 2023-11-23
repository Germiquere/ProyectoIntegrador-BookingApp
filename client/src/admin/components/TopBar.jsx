import { BsSearch, BsPlusLg, BsFillGearFill } from "react-icons/bs";
import { useBikesContext } from "../../context/BikesContext";
import { Link, NavLink } from "react-router-dom";
import { Tooltip } from "@mui/material";
import { useCategoriesContext } from "../../context/CategoriesContext";
export const TopBar = () => {
    const { setOpenNewProductModal } = useBikesContext();
    const { openManageCategories, setOpenManageCategories } =
        useCategoriesContext();
    return (
        <div className="w-full flex justify-between items-center bg-white ">
            <nav>
                <ul className="flex ">
                    <li>
                        <NavLink
                            to="/admin"
                            // className={({ isActive }) =>
                            //     isActive ? "text-cyan-400" : ""
                            // }
                        >
                            <button
                                className="middle none center rounded-full py-3 px-3 font-sans text-xs font-bold uppercase text-primary transition-all hover:bg-tertiary active:bg-tertiary disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none "
                                data-ripple-dark="true"
                            >
                                Productos
                            </button>
                        </NavLink>
                    </li>
                    <li>
                        <NavLink to="usuarios">
                            <button
                                className=" middle none center rounded-full py-3 px-3 font-sans text-xs font-bold uppercase text-primary transition-all hover:bg-tertiary active:bg-tertiary disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none "
                                data-ripple-dark="true"
                            >
                                USUARIOS
                            </button>
                        </NavLink>
                    </li>
                </ul>
            </nav>
            {/* <h2 className="text-lg sm:text-2xl font-semibold">Productos</h2> */}
            <div className="flex gap-2 items-center">
                <div className="relative h-11 w-full sm:min-w-[200px]  flex-1 items-center shadow-xl rounded-full">
                    <input
                        className="peer h-full w-full rounded-full  p-3 font-sans text-sm font-normal text-blue-gray-700 outline outline-0 transition-all  focus:outline-0  disabled:bg-blue-gray-50 "
                        placeholder="Buscar producto"
                        // value={formState.search}
                        name={"search"}
                        // onChange={onInputChange}
                    />

                    <BsSearch className="absolute top-1/2 right-3 transform -translate-y-1/2 cursor-pointer text-gray-400   rounded-full p-2 text-3xl" />
                </div>
                <button
                    className="flex gap-2 items-center middle none center  rounded-full border border-primary py-3 px-6 font-sans text-xs font-bold uppercase text-primary transition-all hover:opacity-75 focus:ring focus:ring-tertiary active:opacity-[0.85] disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none"
                    data-ripple-dark="true"
                    onClick={() => setOpenNewProductModal(true)}
                >
                    AÑADIR PRODUCTO
                    <BsPlusLg className="text-xl" />
                </button>
                <Tooltip title="Administrar categorías y políticas">
                    <button
                        className="text-primary flex  items-center justify-center middle none center rounded-full  h-10 w-10 font-sans text-xs font-bold uppercase  transition-all hover:bg-tertiary active:bg-tertiary disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none "
                        data-ripple-dark="true"
                        onClick={() => setOpenManageCategories(true)}
                    >
                        <BsFillGearFill className="text-xl" />
                    </button>
                </Tooltip>
                {/* <Tooltip message="Administrar categorias">
                    <button
                        className="text-primary flex  items-center justify-center middle none center rounded-full  h-7 w-7 font-sans text-xs font-bold uppercase  transition-all hover:bg-tertiary active:bg-tertiary disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none "
                        data-ripple-dark="true"
                    >
                        <BsFillGearFill className="text-xl" />
                    </button>
                </Tooltip> */}
            </div>
        </div>
    );
};
