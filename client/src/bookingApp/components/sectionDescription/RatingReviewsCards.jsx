import React from "react";
import { parseISO, format } from "date-fns";
import { es } from "date-fns/locale";
export const RatingReviewsCards = ({
    usuario,
    puntuacion,
    comentario,
    fecha,
}) => {
    const newDate = parseISO(fecha);
    const newFormatedDate = format(newDate, "dd MMM yyyy", { locale: es });

    return (
        <div className="flex flex-col gap-3">
            <div className="flex gap-2 items-center">
                <div className=" w-10 h-10 bg-primary rounded-full flex items-center justify-center text-white text-md mr-2">
                    {usuario.nombre.charAt(0).toUpperCase()}
                </div>
                <div>
                    <p className="font-semibold text-md sm:text-lg">
                        {usuario.nombre &&
                            usuario.nombre.charAt(0).toUpperCase() +
                                usuario.nombre.slice(1)}
                    </p>
                    <p>{newFormatedDate}</p>
                </div>
            </div>
            <div>
                {comentario &&
                    comentario.charAt(0).toUpperCase() + comentario.slice(1)}
            </div>
        </div>
    );
};
