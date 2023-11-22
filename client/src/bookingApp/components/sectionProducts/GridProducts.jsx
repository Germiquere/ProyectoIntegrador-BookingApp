import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { useBikesContext } from "../../../context/BikesContext";
import { useUsersContext } from "../../../context/UsersContext";
import { useFavoritesContext } from "../../../context/FavoritesContext";
import { SkeletonGridProducts } from "./SkeletonGridProducts";
import { FaRegHeart, FaHeart } from "react-icons/fa6";

export default function GridProducts() {
    const { bikesData, loading: loadingProducts } = useBikesContext();
    const { userData, isAuthenticated, rol } = useUsersContext();
    const { favorites, handleFav } = useFavoritesContext();
    const [loadingFav, setLoadingFav] = useState(false);
    const [favorite, setFavorite] = useState([]);
    const [randomCards, setRandomCards] = useState([]);
    const totalCards = 10;

    // FUNCION MEZCLAR PRODUCTS
    function shuffleArray(array) {
        const shuffled = [...array];
        for (let i = shuffled.length - 1; i > 0; i--) {
            const j = Math.floor(Math.random() * (i + 1));
            [shuffled[i], shuffled[j]] = [shuffled[j], shuffled[i]];
        }
        return shuffled;
    }

    // FUNCION PARA PINTAR O DESPINTAR CORAZON
    const handleToggleFavorite = (id) => {
        setFavorite((prevState) => {
            const newStatus = { ...prevState };
            newStatus[id] = !newStatus[id];
            console.log(newStatus);
            return newStatus;
        });
    };

    useEffect(() => {
        if (!loadingProducts) {
            const shuffledData = shuffleArray(bikesData);
            const selectedCards = shuffledData.slice(0, totalCards);
            setRandomCards(selectedCards);
        }
    }, [bikesData, loadingProducts]);

    //FUNCION PARA QUE SE PINTE EL CORAZON FAVORITOS
    useEffect(() => {
        if (!loadingFav && favorites.length > 0) {
            setFavorite(() => {
                const initialStatus = favorites.reduce((status, fav) => {
                    status[fav.bicicleta.bicicletaId] = true;
                    return status;
                }, {});
                return initialStatus;
            });
            setLoadingFav(true);
        }
    }, [favorites]);

    return (
        <div className="grid grid-cols-1  gap-4  ssm:grid-cols-2  sm:grid-cols-3  md:grid-cols-4  lg:grid-cols-5 ">
            {loadingProducts
                ? Array.from({ length: totalCards }).map((_, index) => (
                      <SkeletonGridProducts key={index} />
                  ))
                : randomCards.map((item) => (
                      <div
                          className={`relative mt-8 mb-8 rounded-xl transition-transform transform-gpu duration-300 shadow-md hover:-translate-y-1 hover:scale-105 
              `}
                          key={item.bicicletaId}
                      >
                          {/*  Boton de favoritos */}
                          <div className="absolute text-primary right-6 bottom-1 text-[25px]">
                              {isAuthenticated && rol === "user" && (
                                  <button
                                      onClick={() => {
                                          handleFav(item.bicicletaId, userData);
                                          handleToggleFavorite(
                                              item.bicicletaId
                                          );
                                      }}
                                  >
                                      {favorite[item.bicicletaId] ? (
                                          <FaHeart />
                                      ) : (
                                          <FaRegHeart />
                                      )}
                                  </button>
                              )}
                          </div>
                          <Link
                              to={`/description/${item.bicicletaId}`}
                              className=""
                          >
                              <div>
                                  <img
                                      className="rounded-t-xl  w-full h-48 object-contain"
                                      src={item.imagenes[0].url}
                                      alt={item.nombre}
                                  />
                              </div>

                              <p className="p-4">
                                  {item.nombre.length > 55
                                      ? item.nombre.slice(0, 55) + "..."
                                      : item.nombre}
                              </p>
                              <p className="p-4 font-bold ">
                                  Desde ${item.precioAlquilerPorDia}/día
                              </p>
                          </Link>
                      </div>
                  ))}
        </div>
    );
}
