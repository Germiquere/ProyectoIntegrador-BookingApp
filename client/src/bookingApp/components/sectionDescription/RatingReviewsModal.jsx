import { Tooltip } from "@mui/material";
import { BsFillStarFill, BsXLg } from "react-icons/bs";
import { RatingReviewsCards } from "./RatingReviewsCards";

export const RatingReviewsModal = ({
    handleOpenRatingModal,
    valoraciones,
    cantidadValoraciones,
    promedioPuntuacion,
}) => {
    return (
        <>
            <div
                className={` md:rounded-xl h-screen md:max-h-[600px] overflow-hidden overflow-y-auto bg-white  fixed top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2   w-full md:max-w-[700px] mx-auto transition-opacity duration-200 z-50 `}
            >
                <div className="flex flex-col gap-2">
                    {/* header */}
                    <div className="flex justify-between items-center bg-white sticky top-0 p-3 ">
                        <div className="flex gap-3 items-center text-lg sm:text-2xl font-semibold  ">
                            <BsFillStarFill className="cursor-pointer sm:text-xl" />

                            <p>{parseFloat(promedioPuntuacion).toFixed(1)}</p>

                            {cantidadValoraciones > 1
                                ? `${cantidadValoraciones} valoraciones`
                                : `${cantidadValoraciones} valoración`}
                        </div>
                        <Tooltip title="Cerrar">
                            <button
                                className="flex  items-center justify-center middle none center rounded-full  h-10 w-10 font-sans text-xs font-bold uppercase  transition-all hover:bg-blackOpacity1 active:bg-tertiary disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none "
                                data-ripple-dark="true"
                                onClick={() => {
                                    handleOpenRatingModal();
                                }}
                            >
                                <BsXLg className="text-lg" />
                            </button>
                        </Tooltip>
                    </div>
                    <div className=" grid grid-cols-1 gap-5 p-3">
                        {valoraciones.map((rev) => (
                            <RatingReviewsCards
                                key={rev.valoracionId}
                                {...rev}
                            />
                        ))}
                    </div>
                </div>
            </div>
            <div
                className={`fixed top-0 left-0 w-full h-full bg-black bg-opacity-70 transition-opacity duration-200 z-40`}
            ></div>
        </>
    );
};
