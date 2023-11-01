import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { useBikesContext } from "../../../context/BikesContext";

export default function GridProducts() {
    const { bikesData } = useBikesContext();

    const [randomCards, setRandomCards] = useState([]);
    const totalCards = 10;

    function shuffleArray(array) {
        const shuffled = [...array];
        for (let i = shuffled.length - 1; i > 0; i--) {
            // Generar un índice aleatorio entre 0 y i
            const j = Math.floor(Math.random() * (i + 1));

            // Intercambiar elementos para mezclar el arreglo
            [shuffled[i], shuffled[j]] = [shuffled[j], shuffled[i]];
        }
        return shuffled;
    }

    useEffect(() => {
        // Función para mezclar aleatoriamente los datos
        const shuffledData = shuffleArray(bikesData);

        // Seleccionar los primeros 10 elementos mezclados para mostrar
        const selectedCards = shuffledData.slice(0, totalCards);

        // Actualizar el estado con las tarjetas seleccionadas
        setRandomCards(selectedCards);
    }, [bikesData]);

    return (
        <div className="grid grid-cols-1  gap-4  ssm:grid-cols-2  sm:grid-cols-3  md:grid-cols-4  lg:grid-cols-5  ">
            {randomCards.map((item) => (
                <div
                    className={` mt-8 mb-8 rounded-xl transition-transform transform-gpu duration-300 shadow-md hover:-translate-y-1 hover:scale-105 
          `}
                    key={item.bicicletaId}
                >
                    <Link to={`/description/${item.bicicletaId}`} className="">
                        <div>
                            <img
                                className="rounded-t-xl  w-full h-48 object-contain"
                                src={item.imagenes[0].url}
                                alt={item.nombre}
                            />
                        </div>

                        <p className="pl-4 pt-4">{item.nombre}</p>
                        <p className="p-4 font-bold ">
                            Desde ${item.precioAlquilerPorDia}/día
                        </p>
                    </Link>
                </div>
            ))}
        </div>
    );
}
