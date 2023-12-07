import React, { useEffect, useState } from "react";
import {
    useParams,
    useNavigate,
    useLocation,
    Navigate,
    Link,
} from "react-router-dom";
import Section from "../components/Section";
import { BsFillShareFill, BsFillStarFill } from "react-icons/bs";
import {
    IoIosArrowDropleft,
    IoIosArrowDropright,
    IoIosArrowBack,
} from "react-icons/io";
import { FaRegHeart, FaHeart } from "react-icons/fa6";
import { ImgGallery } from "../components/sectionDescription/ImgGallery";
import { ImgSwiper } from "../components/sectionDescription/ImgSwiper";
import { CalendarDescription } from "../components/sectionDescription/CalendarDescription";
import { SkeletonDescription } from "../components/sectionDescription/SkeletonDescription";
import { useCategoriesContext } from "../../context/CategoriesContext";
import { useBikesContext } from "../../context/BikesContext";
import { differenceInDays, parse } from "date-fns";
import { useCalendarAndSearchContext } from "../../context/CalendarSearchContext";
import { LineaBreak } from "../../ui/LineaBreak";
import { useFavoritesContext } from "../../context/FavoritesContext";
import { useUsersContext } from "../../context/UsersContext";
import { Tooltip } from "@mui/material";
import { ShareModal } from "../components/sectionDescription/ShareModal";
import { Helmet } from "react-helmet";
import { RatingReviewsCards } from "../components/sectionDescription/RatingReviewsCards";
import { RatingReviewsModal } from "../components/sectionDescription/RatingReviewsModal";
import { object } from "prop-types";

export const Description = () => {
    const {
        loading: loadingBikes,
        bikeByIdGet,
        bikeById,
        setBikeById,
        openShareModal,
        openRatingModal,
        handleOpenRatingModal,
        handleOpenShareModal,
        setOpenShareModal,
    } = useBikesContext();
    const { formState, fetchDatesByBikeId, setDatesData } =
        useCalendarAndSearchContext();
    const {
        userData,
        isAuthenticated,
        rol,
        loginbyBooking,
        setLoginbyBooking,
    } = useUsersContext();
    const { favorites, handleFav } = useFavoritesContext();
    const [isFav, setIsFav] = useState(false);
    const [loadingFav, setLoadingFav] = useState(false);

    const [total, setTotal] = useState(0);
    const calcPrice = () => {
        if (!formState.endDate || !formState.startDate) {
            return setTotal(0);
        } else if (bikeById) {
            const difference = differenceInDays(
                parse(formState.endDate, "dd-MM-yyyy", new Date()),
                parse(formState.startDate, "dd-MM-yyyy", new Date())
            );
            const pricePerDay = parseFloat(
                bikeById.precioAlquilerPorDia
            ).toFixed(2);
            const newTotal = difference * pricePerDay;
            setTotal(newTotal);
        }
    };
    const [errorDates, setErrorDates] = useState(false);
    const handleErrorDates = () => {
        let hasError = false;
        if (!formState.startDate || !formState.endDate) {
            setErrorDates(true);
            hasError = true;
        }
        return hasError;
    };
    const { id } = useParams();
    const navigate = useNavigate();
    const goToBooking = () => {
        if (handleErrorDates()) {
            return;
        }
        if (!isAuthenticated) {
            setLoginbyBooking(true);
            return navigate("/auth/login");
        }
        setErrorDates(false);
        navigate("/bookings");
    };
    const goBack = () => {
        navigate(-1);
    };

    const [openImgGallery, setOpenImgGallery] = useState(false);
    const handleToggleImgGallery = () => {
        setOpenImgGallery(!openImgGallery);
    };
    // FUNCION PARA PINTAR O DESPINTAR CORAZON
    const handleIsFav = () => {
        setIsFav(!isFav);
    };
    //FUNCION PARA QUE SE PINTE EL CORAZON FAVORITOS
    useEffect(() => {
        if (
            !loadingFav &&
            favorites.length > 0 &&
            Object.keys(bikeById).length > 0
        ) {
            const isInFavArray = favorites.some(
                (item) => item.bicicleta.bicicletaId === bikeById.bicicletaId
            );
            isInFavArray && setIsFav(true);
            setLoadingFav(true);
        }
    }, [favorites, bikeById]);
    useEffect(() => {
        bikeByIdGet(id);
        return () => {};
    }, []);

    useEffect(() => {
        calcPrice();
    }, [formState.endDate, formState.startDate]);
    // useEffect(() => {
    //     return () => {
    //         setBikeById([]);
    //     };
    // }, []);
    useEffect(() => {
        if (Object.keys(bikeById).length > 0) {
            fetchDatesByBikeId(bikeById.bicicletaId);
        }

        return () => {
            setDatesData([]);
        };
    }, [bikeById]);

    return (
        <Section>
            {loadingBikes ? (
                <SkeletonDescription />
            ) : (
                <>
                    <Helmet>
                        <title>{bikeById.nombre}</title>
                        {/* <link rel="canonical" href="http://mysite.com/example" /> */}

                        <meta
                            property="og:image"
                            content={
                                Object.keys(bikeById).length > 0 &&
                                bikeById?.imagenes[0]?.url
                            }
                        />
                        <meta property="og:title" content={bikeById.nombre} />
                        <meta
                            property="og:description"
                            content={bikeById.descripcion}
                        />
                    </Helmet>
                    <div
                        className={`flex gap-3 flex-col relativemd:justify-center mt-3 max-w-[1200px] mx-auto `}
                    >
                        {/* seccion del lado izquierdo */}
                        {/* NOTA: with flex-1, it takes up all available spac   e */}
                        <div className="flex-1 p-3 border-[1px] border-gray-200  rounded-xl flex flex-col gap-3">
                            <div className="flex justify-between items-center">
                                <h2 className="text-lg sm:text-2xl font-semibold pb-2 w-full">
                                    {bikeById.nombre}
                                </h2>

                                <div
                                    className="flex gap-2 items-center justify-end
                                cursor-pointer
                            pb-2
                        "
                                    onClick={goBack}
                                >
                                    <button
                                        className="flex gap-2 items-center middle none center rounded-full  py-3 px-3 sm:px-6 font-sans text-xs font-bold uppercase text-primary transition-all hover:bg-tertiary active:bg-tertiary disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none "
                                        data-ripple-dark="true"
                                    >
                                        <h3 className="hidden sm:block sm:text-sm">
                                            Volver
                                        </h3>
                                        <IoIosArrowBack className="text-lg text-primary" />
                                    </button>
                                </div>
                            </div>
                            <div
                                className={`flex flex-col sm:flex-row  gap-3 sm:items-center text-sm `}
                            >
                                <button
                                    className="flex gap-3 items-center text-primary"
                                    onClick={handleOpenRatingModal}
                                >
                                    {bikeById.cantidadValoraciones !== null && (
                                        <>
                                            <BsFillStarFill className="   cursor-pointer" />
                                            <p>
                                                {parseFloat(
                                                    bikeById.promedioPuntuacion
                                                ).toFixed(1)}
                                            </p>
                                        </>
                                    )}

                                    <p className="underline underline-offset-1">
                                        {bikeById.cantidadValoraciones !==
                                            null &&
                                            (bikeById.cantidadValoraciones > 1
                                                ? `${bikeById.cantidadValoraciones} valoraciones`
                                                : `${bikeById.cantidadValoraciones} valoración`)}
                                    </p>
                                </button>
                                <div className="flex gap-2">
                                    <button
                                        className="flex gap-2 items-center text-primary"
                                        onClick={handleOpenShareModal}
                                    >
                                        <BsFillShareFill className="   cursor-pointer" />
                                        <p className="underline underline-offset-1">
                                            Compartir
                                        </p>
                                    </button>
                                    {/*  Boton de favoritos */}
                                    <div className=" text-primary  flex items-center">
                                        {isAuthenticated && rol === "user" && (
                                            <div
                                                onClick={() => {
                                                    handleFav(
                                                        bikeById.bicicletaId,
                                                        userData
                                                    );
                                                    handleIsFav();
                                                }}
                                            >
                                                {isFav ? (
                                                    <button className="flex gap-2 items-center">
                                                        <FaHeart />
                                                        <p className="underline underline-offset-1">
                                                            Guardados
                                                        </p>
                                                    </button>
                                                ) : (
                                                    <button className="flex gap-2  items-center">
                                                        <FaRegHeart />
                                                        <p className="underline underline-offset-1">
                                                            Guardar
                                                        </p>
                                                    </button>
                                                )}
                                            </div>
                                        )}
                                    </div>
                                </div>
                            </div>

                            {bikeById &&
                                bikeById.imagenes &&
                                bikeById.imagenes.length > 0 && (
                                    <ImgGallery
                                        data={bikeById}
                                        loadingBikes={loadingBikes}
                                        handleToggleImgGallery={
                                            handleToggleImgGallery
                                        }
                                    />
                                )}
                        </div>
                        {/* seccion del costado derecho */}
                        <div className=" flex flex-col    p-3 gap-3  h-full lg:h-auto border-[1px] border-gray-200 rounded-xl">
                            <div className="flex flex-col sm:flex-row  gap-2 h-full justify-between ">
                                <div className="flex flex-col flex-1 gap-5">
                                    <div className="flex flex-col gap-2">
                                        <h3 className="text-lg sm:text-2xl font-semibold">
                                            Descripción
                                        </h3>
                                        <div className="max-w-[1000px]">
                                            <LineaBreak
                                                text={bikeById.descripcion}
                                            />
                                        </div>
                                    </div>
                                    <div className="flex flex-col gap-2">
                                        <h3 className="text-lg sm:text-2xl font-semibold ">
                                            Características
                                        </h3>
                                        <div className="w-full  grid grid-cols-1 lg:grid-cols-2 gap-5">
                                            {bikeById?.caracteristicas?.map(
                                                (caract) => (
                                                    <div
                                                        className="flex gap-2 items-center text-lg"
                                                        key={
                                                            caract.caracteristicaId
                                                        }
                                                    >
                                                        <i
                                                            className={`${caract.icono} text-primary`}
                                                        ></i>
                                                        <p>{caract.nombre}</p>
                                                    </div>
                                                )
                                            )}
                                        </div>
                                    </div>
                                    <div className="flex flex-col gap-2">
                                        <h3 className="text-lg sm:text-2xl font-semibold underline underline-offset-1 ">
                                            Políticas
                                        </h3>
                                        <ul className="w-full  flex flex-col gap-2">
                                            {bikeById?.politicas?.map((pol) => (
                                                <li
                                                    className="flex flex-col gap-1 "
                                                    key={pol.politicaId}
                                                >
                                                    <h3 className="text-lg font-semibold">
                                                        {pol.titulo}
                                                    </h3>
                                                    <p>{pol.descripcion}</p>
                                                </li>
                                            ))}
                                        </ul>
                                    </div>
                                </div>

                                {/* div de la derecha */}
                                <div className="flex flex-col w-full sm:w-[300px] gap-3 rounded-xl shadow-xl p-3 border-[1px] border-gray-100 h-full">
                                    <h3 className="text-lg sm:text-2xl font-semibold w-full">
                                        ${bikeById.precioAlquilerPorDia}/dia
                                    </h3>
                                    <h2 className="text-lg  ">
                                        ¿Cuándo quieres reservar?
                                    </h2>
                                    <CalendarDescription
                                        bikeId={id}
                                        setDateErrors={setErrorDates}
                                    />
                                    <p
                                        className={`pt-1 text-xs text-red-500 ${
                                            errorDates ? "block" : "hidden"
                                        }`}
                                    >
                                        Debes seleccionar una fecha
                                    </p>

                                    <button
                                        onClick={goToBooking}
                                        className="mb-5 middle none center mr-3 rounded-full bg-primary py-3 px-6 font-sans text-xs font-bold uppercase text-white shadow-sm  transition-all  hover:shadow-secondary  active:opacity-[0.85] active:shadow-none disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none w-full"
                                        data-ripple-light="true"
                                    >
                                        ALQUILAR
                                    </button>

                                    <div className=" border-t border-gray-200 w-full pt-3">
                                        <div className="flex justify-between">
                                            <h3 className="text-lg sm:text-2xl font-semibold w-full">
                                                Total a pagar
                                            </h3>
                                            <h3 className="text-lg sm:text-2xl font-semibold">
                                                {total ? ` $${total}` : ""}
                                            </h3>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        {/* seccion reviews */}
                        {bikeById.valoraciones &&
                            bikeById.valoraciones.length > 0 && (
                                <div className="flex flex-col   gap-5 p-3 border-[1px] border-gray-200 rounded-xl">
                                    <div className="flex gap-3 items-center text-lg sm:text-2xl font-semibold  ">
                                        <BsFillStarFill className="cursor-pointer sm:text-xl" />

                                        <p>
                                            {parseFloat(
                                                bikeById.promedioPuntuacion
                                            ).toFixed(1)}
                                        </p>

                                        {bikeById.cantidadValoraciones > 1
                                            ? `${bikeById.cantidadValoraciones} valoraciones`
                                            : `${bikeById.cantidadValoraciones} valoración`}
                                    </div>
                                    <div className=" grid grid-cols-1 gap-5 sm:grid-cols-2">
                                        {bikeById.valoraciones.length >= 4
                                            ? bikeById.valoraciones
                                                  .slice(0, 4)
                                                  .map((rev) => (
                                                      <RatingReviewsCards
                                                          key={rev.valoracionId}
                                                          {...rev}
                                                      />
                                                  ))
                                            : bikeById.valoraciones.map(
                                                  (rev) => (
                                                      <RatingReviewsCards
                                                          key={rev.valoracionId}
                                                          {...rev}
                                                      />
                                                  )
                                              )}
                                    </div>
                                    {bikeById.valoraciones.length >= 4 && (
                                        <p
                                            onClick={handleOpenRatingModal}
                                            className=" font-semibold flex items-center justify-center max-w-[270px] cursor-pointer border-grayTertiary border-[1px] rounded-md p-1 bg-white hover:bg-gray-100"
                                        >
                                            {`Mostrar todo: ${bikeById.cantidadValoraciones} valoraciones`}
                                        </p>
                                    )}
                                </div>
                            )}
                    </div>
                </>
            )}

            {openImgGallery &&
                bikeById &&
                bikeById.imagenes &&
                bikeById.imagenes.length > 0 && (
                    <>
                        <div
                            className={`fixed top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 w-3/5  transition-opacity duration-200 z-50 `}
                        >
                            <ImgSwiper
                                data={bikeById}
                                handleToggleImgGallery={handleToggleImgGallery}
                            />
                        </div>

                        <div
                            className={`fixed top-0 left-0 w-full h-full bg-black bg-opacity-80 transition-opacity duration-200 z-40`}
                            onClick={handleToggleImgGallery}
                        ></div>
                    </>
                )}
            {openShareModal && (
                <ShareModal
                    setOpenShareModal={setOpenShareModal}
                    bikeById={bikeById}
                />
            )}
            {openRatingModal && (
                <RatingReviewsModal
                    handleOpenRatingModal={handleOpenRatingModal}
                    {...bikeById}
                />
            )}
        </Section>
    );
};
