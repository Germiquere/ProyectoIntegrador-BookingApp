import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { FaRegHeart, FaHeart } from "react-icons/fa6";
import { useUsersContext } from "../../context/UsersContext";
import { useFavoritesContext } from "../../context/FavoritesContext";
import Section from "../components/Section";
import { Helmet } from "react-helmet";

export default function Favorites() {
    const { userData, isAuthenticated } = useUsersContext();
    const { favorites, handleFav } = useFavoritesContext();
    const [favorite, setFavorite] = useState({});

    // FUNCION PARA PINTAR O DESPINTAR CORAZON
    const handleToggleFavorite = (id) => {
        setFavorite((prevState) => {
            const newStatus = { ...prevState };
            newStatus[id] = !newStatus[id];
            console.log(newStatus);
            return newStatus;
        });
    };

    // FUNCION PARA QUE SE PINTE EL CORAZON FAVORITOS
    useEffect(() => {
        setFavorite(() => {
            const initialStatus = favorites.reduce((status, fav) => {
                status[fav.bicicleta.bicicletaId] = true;
                return status;
            }, {});
            return initialStatus;
        });
    }, [favorites]);

    return (
        <Section>
            <Helmet>
                <title>Mis favoritos Bike Me Now</title>
                {/* <link rel="canonical" href="http://mysite.com/example" /> */}

                <meta
                    property="og:image"
                    content="https://res.cloudinary.com/djslo5b3u/image/upload/v1698971497/BikeMeNow_BlueAlpha_svidg9.png"
                />
                <meta property="og:title" content="Mis favoritos Bike Me Now" />
                <meta
                    property="og:description"
                    content="Descubre la libertad sobre dos ruedas con nuestro servicio de alquiler de bicicletas. Explora tu ciudad o destinos increíbles mientras reservas la bicicleta perfecta para cada aventura. ¡Siente el viento en tu rostro y pedalea hacia tus próximas experiencias inolvidables!"
                />
            </Helmet>
            <div className="max-w-[1200px] mx-auto ">
                <div className="md:mt-3 pt-3 md:pt-0">
                    {favorites.length !== 0 && (
                        <h2 className="text-lg sm:text-2xl font-semibold">
                            Favoritos
                        </h2>
                    )}
                </div>

                {favorites.length === 0 ? (
                    <div className="flex justify-center items-center min-h-[calc(100vh-350px)] gap-2">
                        <p className="text-lg sm:text-2xl font-semibold text-gray-300">
                            No tienes ningún producto en favoritos
                        </p>
                        <FaHeart className="text-gray-300 text-2xl" />
                    </div>
                ) : (
                    <div className="grid grid-cols-1 gap-4 ssm:grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-5 ">
                        {favorites.map((item) => (
                            <div
                                className={`relative mt-8 mb-8 rounded-xl transition-transform transform-gpu duration-300 shadow-md hover:-translate-y-1 hover:scale-105`}
                                key={item.bicicleta.bicicletaId}
                            >
                                {/*  Boton de favoritos */}
                                <div className="absolute text-primary  right-2 top-2 text-[25px]">
                                    {isAuthenticated && (
                                        <button
                                            onClick={() => {
                                                handleFav(
                                                    item.bicicleta.bicicletaId,
                                                    userData
                                                );
                                                handleToggleFavorite(
                                                    item.bicicleta.bicicletaId
                                                );
                                            }}
                                        >
                                            {favorite[
                                                item.bicicleta.bicicletaId
                                            ] ? (
                                                <FaHeart />
                                            ) : (
                                                <FaRegHeart />
                                            )}
                                        </button>
                                    )}
                                </div>
                                <Link
                                    to={`/description/${item.bicicleta.bicicletaId}`}
                                    className=""
                                >
                                    <div className="pb-2">
                                        <img
                                            className="rounded-t-xl  w-full h-48 object-contain"
                                            src={item.bicicleta.imagenes[0].url}
                                            alt={item.bicicleta.nombre}
                                        />
                                    </div>
                                    <p className="px-4 font-bold ">
                                        Desde $
                                        {item.bicicleta.precioAlquilerPorDia}
                                        /día
                                    </p>
                                    <p className="px-4 pb-4">
                                        {item.bicicleta.nombre.length > 50
                                            ? item.bicicleta.nombre.slice(
                                                  0,
                                                  50
                                              ) + "..."
                                            : item.bicicleta.nombre}
                                    </p>
                                </Link>
                            </div>
                        ))}
                    </div>
                )}
            </div>
        </Section>
    );
}
