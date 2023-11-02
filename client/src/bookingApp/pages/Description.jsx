import React, { useState } from "react";
import { useParams, useNavigate, useLocation } from "react-router-dom";
import Section from "../components/Section";
import { IoIosArrowDropleft, IoIosArrowDropright } from "react-icons/io";
import { ImgGallery } from "../components/sectionDescription/ImgGallery";
import { ImgSwiper } from "../components/sectionDescription/ImgSwiper";
import { CalendarDescription } from "../components/sectionDescription/CalendarDescription";
import { SkeletonDescription } from "../components/sectionDescription/SkeletonDescription";
import { useCategoriesContext } from "../../context/CategoriesContext";
export const Description = () => {
    const { loading: loadingCategories } = useCategoriesContext();
    // hacer el fetch con ese id

    const { id } = useParams();
    const navigate = useNavigate();

    const goBack = () => {
        // cambiar a la ruta que  quiero que me redirija
        navigate("/");
    };

    const [openImgGallery, setOpenImgGallery] = useState(false);
    const handleToggleImgGallery = () => {
        setOpenImgGallery(!openImgGallery);
    };
    const data = [
        {
            id: 1,
            img: "https://http2.mlstatic.com/D_NQ_NP_824252-MLU70488123954_072023-O.webp",
            category: "city",
            name: "Ciudad",
            showButton: false,
        },
        {
            id: 2,
            img: "https://http2.mlstatic.com/D_NQ_NP_824252-MLU70488123954_072023-O.webp",
            category: "electric",
            name: "Electricas",
            showButton: false,
        },
        {
            id: 3,
            img: "https://http2.mlstatic.com/D_NQ_NP_824252-MLU70488123954_072023-O.webp",
            category: "kids",
            name: "Niños",
            showButton: false,
        },
        {
            id: 4,
            img: "https://http2.mlstatic.com/D_NQ_NP_824252-MLU70488123954_072023-O.webp",
            category: "mountain",
            name: "Montaña",
            showButton: false,
        },
        {
            id: 5,
            img: "https://http2.mlstatic.com/D_NQ_NP_824252-MLU70488123954_072023-O.webp",
            category: "road",
            name: "Ruta",
            showButton: true,
        },
    ];
    return (
        <Section>
            {loadingCategories ? (
                <SkeletonDescription />
            ) : (
                <div className="flex gap-3 flex-col relative lg:flex-row md:justify-center mt-3 max-w-[1200px] mx-auto ">
                    {/* seccion del lado izquierdo */}
                    {/* NOTA: con flex-1 le digo que ocupe todo el espacio libre */}
                    <div className="flex-1 p-3 border-[1px] border-gray-200 rounded-xl">
                        <h2 className="text-lg sm:text-2xl font-semibold pb-2 w-full">
                            Nombre del producto
                        </h2>
                        <h3 className="text-lg sm:text-2xl  pb-2 w-full">
                            Descripcion
                        </h3>
                        <p className="max-w-[1000px] pb-5">
                            Lorem, ipsum dolor sit amet consectetur adipisicing
                            elit. Quod dicta enim reprehenderit sint laboriosam
                            ut, inventore est dolorem, voluptates, sit autem
                            quasi impedit! Ullam, nesciunt voluptate modi
                            cupiditate corporis voluptatum!
                        </p>

                        <ImgGallery
                            data={data}
                            handleToggleImgGallery={handleToggleImgGallery}
                        />
                    </div>
                    {/* seccion del costado derecho */}
                    <div className=" flex flex-col border-[1px] border-gray-200 rounded-xl  p-3 gap-3 lg:w-[300px] h-full lg:h-auto">
                        <div
                            className="flex gap-2 items-center justify-end
                        cursor-pointer
                        absolute top-4  right-3
                        lg:relative
                        lg:top-auto
                        lg:right-auto
                    "
                            onClick={goBack}
                        >
                            <h3 className="hidden sm:block sm:text-lg">
                                Volver
                            </h3>
                            <IoIosArrowDropleft className="text-lg text-primary" />
                        </div>
                        <div className="flex flex-col gap-2 h-full justify-between">
                            <div className="flex flex-col gap-2">
                                <h3 className="text-lg sm:text-2xl  w-full">
                                    Precio por dia
                                </h3>
                                <h2 className="text-lg  ">
                                    ¿Cuando queres reservar?
                                </h2>
                                <CalendarDescription />
                            </div>

                            <button
                                className="middle none center mr-3 rounded-full bg-primary py-3 px-6 font-sans text-xs font-bold uppercase text-white shadow-sm  transition-all  hover:shadow-secondary  active:opacity-[0.85] active:shadow-none disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none w-full"
                                data-ripple-light="true"
                            >
                                ALQUILAR
                            </button>
                        </div>
                    </div>
                </div>
            )}

            {openImgGallery && (
                <>
                    <div
                        className={`fixed top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 w-3/5  transition-opacity duration-200 z-50 `}
                    >
                        <ImgSwiper
                            data={data}
                            handleToggleImgGallery={handleToggleImgGallery}
                        />
                    </div>

                    <div
                        className={`fixed top-0 left-0 w-full h-full bg-black bg-opacity-80 transition-opacity duration-200 z-40`}
                        onClick={handleToggleImgGallery}
                    ></div>
                </>
            )}
        </Section>
    );
};
