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

const Rating = ({
    rating,
    handleStarClick,
    setGotoRating,
    handleRatingChange,
    erros,
    setErros,
}) => {
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
        bookingDetail,
    } = useBookingsContext();

    const handleInputChange = (e, toNumber = false) => {
        const { name, value } = e.target;
        onInputChange(e, toNumber);
        setErros({
            ...erros,
            [name]: value.trim() === "",
        });
    };

    return (
        <>
            {/* MAIN */}
            <div className="flex flex-col gap-5 p-5  mb-14 ">
                <div className="flex gap-5">
                    <div className="flex flex-col gap-2 flex-1 ">
                        <h3 className=" font-semibold text-basel">
                            ¿Qué te pareció la bicicleta?
                        </h3>

                        <div className="min-w-[64px] min-h-[64px] rounded-xl border border-gray-100  w-40 h-40 overflow-hidden">
                            <img
                                className="object-contain h-40 w-40"
                                src={bookingDetail.bicicleta.imagenes[0].url}
                                alt={bookingDetail.bicicleta.nombre}
                            />
                        </div>
                        <h3 className="text-base">
                            {bookingDetail.bicicleta.nombre}
                        </h3>
                        <div className="w-full my-3">
                            <div className="flex  gap-1">
                                {[1, 2, 3, 4, 5].map((value) => (
                                    <div
                                        className="cursor-pointer"
                                        key={value}
                                        onClick={() => {
                                            handleStarClick(value);
                                            handleRatingChange(value);
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
                        <div>
                            <label className="text-base font-semibold mb-2">
                                Contanos más acerca de la bicicleta *
                            </label>
                            <textarea
                                className="p-2 w-full outline outline-0 shadow-md border-[1px] rounded-xl overflow-hidden border-gray-100"
                                style={{ resize: "none" }}
                                placeholder="Una experiencia increible,no tuve ningun problema con la bicicleta"
                                rows={4}
                                value={comentario}
                                name="comentario"
                                onChange={handleInputChange}
                            ></textarea>
                            <p
                                className={`pt-1 text-xs text-red-500 ${
                                    erros.comentario ? "block" : "hidden"
                                }`}
                            >
                                Campo obligatorio
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </>
    );
};

export default Rating;
