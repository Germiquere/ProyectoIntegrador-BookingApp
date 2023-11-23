import { BsXLg } from "react-icons/bs";
import { useCategoriesContext } from "../../../context/CategoriesContext";
import { Tooltip } from "@mui/material";
import { useState } from "react";
import { Navbar } from "./Navbar";
import { ManageCategories } from "./ManageCategories";
import { Loader } from "../../../ui/Loader";
import { ManagePolicies } from "./ManagePolicies";
import { usePoliciesContext } from "../../../context/PoliciesContext";

export const Manage = () => {
    const {
        openManageCategories,
        setOpenManageCategories,
        loading: loadingCategories,
    } = useCategoriesContext();
    const { loading: loadingPolicies } = usePoliciesContext();
    const [currentComponent, setCurrentComponent] = useState("Categorias");
    return (
        <>
            <div
                className={` rounded-xl max-h-[600px] overflow-hidden bg-white  fixed top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 max-w-[1200px] min-w-[700px] mx-auto transition-opacity duration-200 z-50 `}
            >
                {/* HEADER */}
                <div className=" w-full h-20 flex flex-col  p-3 pb-0  border-gray-300 bg-primary text-white">
                    <div className=" flex justify-between items-center">
                        <h2 className="text-xl font-semibold flex gap-5 items-center">
                            <p>Administrar Categorías y Politicas</p>
                            {loadingCategories ||
                                (loadingPolicies && (
                                    <Loader className={"text-white"} />
                                ))}
                        </h2>
                        <Tooltip title="Cerrar">
                            <button
                                className="flex  items-center justify-center middle none center rounded-full  h-10 w-10 font-sans text-xs font-bold uppercase  transition-all hover:bg-blackOpacity1 active:bg-tertiary disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none "
                                data-ripple-dark="true"
                                disabled={loadingCategories || loadingPolicies}
                                onClick={() => {
                                    setOpenManageCategories(false);
                                }}
                            >
                                <BsXLg className="text-lg" />
                            </button>
                        </Tooltip>
                    </div>
                    <Navbar
                        currentComponent={currentComponent}
                        setCurrentComponent={setCurrentComponent}
                    />
                </div>

                {/* MAIN */}
                <div className=" p-5 min-h-[450px]  max-h-[450px] overflow-auto ">
                    {currentComponent === "Categorias" ? (
                        <ManageCategories />
                    ) : (
                        <ManagePolicies />
                    )}
                </div>
            </div>

            <div
                className={`fixed top-0 left-0 w-full h-full bg-black bg-opacity-70 transition-opacity duration-200 z-40`}
            ></div>
        </>
    );
};
