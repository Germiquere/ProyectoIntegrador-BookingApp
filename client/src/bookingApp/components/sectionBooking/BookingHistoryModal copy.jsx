import { Tooltip } from "@mui/material";
import { useRef, useState } from "react";
import {
    BsXLg,
    BsCurrencyDollar,
    BsCloudUpload,
    BsX,
    BsStar,
    BsStarFill,
} from "react-icons/bs";
import { useForm } from "../../../hooks/useForm";
import { useBookingsContext } from "../../../context/BookingContext";
import { Loader } from "../../../ui/Loader";
import { es } from "date-fns/locale";
import { format } from "date-fns";
import { parseISO } from "date-fns";
import Rating from "./Rating";

const BookingHistoryModal = () => {
    const {
        onResetForm,
        setFormState,
        puntuacion,
        comentario,
        onInputChange,
        formState,
        addNewRating,
        handleOpenHistoryModal,
        loadingRating,
        bookingByUserId,
        bookingDetail,
    } = useBookingsContext();

    const [rating, setRating] = useState(0);
    const [gotoRating, setGotoRating] = useState(false);
    const [erros, setErros] = useState({
        comentario: false,
    });

    const handleStarClick = (value) => {
        setRating(value);
    };

    const handleRatingChange = (value) => {
        setFormState({
            ...formState,
            puntuacion: value,
        });
    };

    const formatDates = (date) => {
        if (date.length > 0) {
            const parsedDate = parseISO(date, "d-MM-yyyy", new Date());
            const formatedParsedDate = format(parsedDate, "dd MMMM yyyy", {
                locale: es,
            });
            return formatedParsedDate;
        }
        return date;
    };
    const handleValidations = () => {
        let hasError = false;

        if (comentario.trim() === "") {
            setErros((prevErrors) => ({
                ...prevErrors,
                comentario: true,
            }));
            hasError = true;
        }

        return hasError;
    };

    const handleSave = async () => {
        if (!handleValidations()) {
            const rating = await addNewRating(formState);

            if (rating) {
                setGotoRating(false);
                onResetForm();
                handleOpenHistoryModal();
            }
        }
    };
    console.log(gotoRating);
    return (
        <>
            <div
                className={` md:rounded-xl h-screen  md:max-h-[600px] overflow-hidden bg-white  fixed top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2  w-full md:max-w-[700px] mx-auto transition-opacity duration-200 z-50 `}
            >
                {/* HEADER */}
                <div className=" w-full h-16  flex justify-between p-3 border-b-[1px] border-gray-300 bg-primary text-white">
                    <h2 className="text-xl font-semibold flex gap-5 items-center">
                        <p>Reserva</p>
                        {loadingRating && <Loader className={"text-white"} />}
                    </h2>
                    <Tooltip title="Cerrar">
                        <button
                            className="flex  items-center justify-center middle none center rounded-full  h-10 w-10 font-sans text-xs font-bold uppercase  transition-all hover:bg-blackOpacity1 active:bg-tertiary disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none "
                            data-ripple-dark="true"
                            onClick={() => {
                                handleOpenHistoryModal();
                                onResetForm();
                            }}
                        >
                            <BsXLg className="text-lg" />
                        </button>
                    </Tooltip>
                </div>
                {/* MAIN */}
                <div className="flex flex-col gap-5 p-5 md:max-h-[450px] mb-14 overflow-auto">
                    {/* DETALLES DE LA BICICLETA */}
                    {!gotoRating ? (
                        <div>
                            <div className="flex flex-col gap-2 flex-1">
                                <div className="w-full">
                                    <h2 className="text-lg font-semibold pb-3 border-b-[1px] border-gray-300 mb-3">
                                        Detalles de la reserva
                                    </h2>
                                </div>
                                <div className="flex flex-col gap-2">
                                    {/* CARD BICICLETA */}
                                    <div className="flex gap-2">
                                        <div className="min-w-[64px] min-h-[64px] rounded-md border border-gray-100 overflow-hidden w-16 h-16">
                                            <img
                                                className="object-contain h-16 w-16"
                                                src={
                                                    bookingDetail.bicicleta
                                                        .imagenes[0].url
                                                }
                                                alt={
                                                    bookingDetail.bicicleta
                                                        .nombre
                                                }
                                            />
                                        </div>
                                        <h3 className="font-semibold text-sm md:hidden">
                                            {bookingDetail.bicicleta.nombre
                                                .length > 40
                                                ? bookingDetail.bicicleta.nombre.slice(
                                                      0,
                                                      40
                                                  ) + "..."
                                                : bookingDetail.bicicleta
                                                      .nombre}
                                        </h3>
                                        <h3 className="font-semibold text-sm hidden md:block">
                                            {bookingDetail.bicicleta.nombre}
                                        </h3>
                                    </div>
                                    <div className="flex gap-10">
                                        <div className="flex flex-col">
                                            <p className="text-base font-semibold mb-2">
                                                Fecha de inicio
                                            </p>
                                            <p>
                                                {formatDates(
                                                    bookingDetail.fechaInicio
                                                )}
                                            </p>
                                        </div>
                                        <div className="flex flex-col">
                                            <p className="text-base font-semibold mb-2">
                                                Fecha de finalizacion
                                            </p>
                                            <p>
                                                {formatDates(
                                                    bookingDetail.fechaFin
                                                )}
                                            </p>
                                        </div>
                                    </div>
                                </div>
                                {/* PUNTUACION */}
                                <div className="w-full">
                                    <p className="text-base font-semibold mb-2">
                                        ¿Qué te pareció ésta bicicleta?
                                    </p>

                                    <div className="flex  gap-1">
                                        {[1, 2, 3, 4, 5].map((value) => (
                                            <div
                                                className="cursor-pointer"
                                                key={value}
                                                onClick={() => {
                                                    handleStarClick(value);
                                                    handleRatingChange(value);
                                                    setGotoRating(true);
                                                }}
                                            >
                                                {value <= rating ? (
                                                    <BsStarFill className="text-xl text-primary" />
                                                ) : (
                                                    <BsStar className="text-xl text-primary" />
                                                )}
                                            </div>
                                        ))}
                                    </div>
                                </div>
                            </div>
                        </div>
                    ) : (
                        <Rating
                            rating={rating}
                            handleStarClick={handleStarClick}
                            setGotoRating={setGotoRating}
                            handleRatingChange={handleRatingChange}
                            erros={erros}
                            setErros={setErros}
                        />
                    )}
                </div>
                {/* FOOTER */}
                {gotoRating && (
                    <div className="flex justify-between p-3 items-center border-t-[1px] border-gray-300 fixed bottom-0 right-0-0 w-full bg-white z-20 h-16">
                        <h2> * Campos obligatorios</h2>
                        <div className="flex gap-2">
                            <button
                                className="middle none center  rounded-full bg-primary py-2 px-6 font-sans text-xs font-bold uppercase text-white shadow-sm  transition-all  hover:shadow-secondary  active:opacity-[0.85] active:shadow-none disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none"
                                data-ripple-light="true"
                                disabled={loadingRating}
                                onClick={handleSave}
                            >
                                GUARDAR
                            </button>
                        </div>
                    </div>
                )}
            </div>
            <div
                className={`fixed top-0 left-0 w-full h-full bg-black bg-opacity-70 transition-opacity duration-200 z-40`}
            ></div>
        </>
    );
};

export default BookingHistoryModal;
