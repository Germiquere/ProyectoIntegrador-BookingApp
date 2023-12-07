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
        bookingDetail,
    } = useBookingsContext();

    const [rating, setRating] = useState(0);
    const [erros, setErros] = useState({
        puntuacion: false,
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
        const shouldTrim = typeof value === "string";

        setErros({
            ...erros,
            puntuacion: shouldTrim ? value.trim() === "" : false,
        });
    };
    const handleInputChange = (e, toNumber = false) => {
        const { name, value } = e.target;
        onInputChange(e, toNumber);
        setErros({
            ...erros,
            [name]: value.trim() === "",
        });
    };
    const handleValidations = () => {
        let hasError = false;

        if (typeof puntuacion === "string" && puntuacion.trim() === "") {
            setErros((prevErrors) => ({
                ...prevErrors,
                puntuacion: true,
            }));
            hasError = true;
        }
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
                onResetForm();
                handleOpenHistoryModal();
            }
        }
    };
    return (
        <>
            <div
                className={` md:rounded-xl h-screen  md:max-h-[600px] overflow-hidden bg-white  fixed top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2  w-full md:max-w-[700px] mx-auto transition-opacity duration-200 z-50 `}
            >
                {/* HEADER */}
                <div className=" w-full h-16  flex justify-between p-3 border-b-[1px] border-gray-300 bg-primary text-white">
                    <h2 className="text-xl font-semibold flex gap-5 items-center">
                        <p>Agregar comentario</p>
                        {loadingRating && <Loader className={"text-white"} />}
                    </h2>
                    <Tooltip title="Cerrar">
                        <button
                            className="flex  items-center justify-center middle none center rounded-full  h-10 w-10 font-sans text-xs font-bold uppercase  transition-all hover:bg-blackOpacity1 active:bg-tertiary disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none "
                            data-ripple-dark="true"
                            // disabled={loadingBikes}
                            onClick={() => {
                                // setOpenNewProductModal(false);
                                // setImageChange([]);
                                handleOpenHistoryModal();
                                onResetForm();
                                // setError(false);
                            }}
                        >
                            <BsXLg className="text-lg" />
                        </button>
                    </Tooltip>
                </div>
                {/* MAIN */}
                <div className="flex flex-col gap-5 p-5 max-h-[450px] mb-14 overflow-auto">
                    <div className="flex gap-5 ">
                        <div className="flex flex-col gap-2 flex-1">
                            <div className="w-full">
                                <p className="text-base font-semibold mb-2">
                                    Puntuación *
                                </p>

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
                                <p
                                    className={`pt-2 text-xs text-red-500 ${
                                        erros.puntuacion ? "block" : "hidden"
                                    }`}
                                >
                                    Campo obligatorio
                                </p>
                            </div>
                            <div>
                                <label className="text-base font-semibold mb-2">
                                    Comentario *
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
                        {/* Div de la derecha */}
                    </div>
                </div>
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
            </div>
            <div
                className={`fixed top-0 left-0 w-full h-full bg-black bg-opacity-70 transition-opacity duration-200 z-40`}
            ></div>
        </>
    );
};

export default BookingHistoryModal;
