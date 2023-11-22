import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { FaRegHeart, FaHeart } from 'react-icons/fa6';
import { useUsersContext } from '../../context/UsersContext';
import { useFavoritesContext } from '../../context/FavoritesContext';
import Section from '../components/Section';

export default function Favorites() {
  const { userData, isAuthenticated } = useUsersContext();
  const { favorites, addNewFavorite, removeAFavorite } = useFavoritesContext();
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

  // FUNCION PARA AGREGAR O QUITAR FAVORITO
  const handleFav = (id) => {
    const isFavorite = favorites.some(
      (fav) => fav.bicicleta.bicicletaId === id
    );
    if (isFavorite) {
      handleRemoveFavorite(id);
    } else {
      handleAddFavorite(userData.usuarioId, id);
    }
    console.log(isFavorite);
  };

  // FUNCION PARA AÑADIR FAVORITOS
  const handleAddFavorite = async (userId, bikeId) => {
    try {
      const newFavorite = await addNewFavorite(userId, bikeId);
      console.log('Nuevo favorito agregado:', newFavorite);
    } catch (err) {
      console.error('Error al agregar favorito:', err);
    }
  };

  // FUNCION PARA REMOVER FAVORITOS
  const handleRemoveFavorite = async (id) => {
    const favoriteDelete = favorites.find(
      (fav) => fav.bicicleta.bicicletaId === id
    );
    console.log(favoriteDelete.favoritoId);

    try {
      const deletedFavorite = await removeAFavorite(favoriteDelete.favoritoId);
      console.log('Favorito eliminado:', deletedFavorite);
    } catch (err) {
      console.error('Error al eliminar favorito:', err);
    }
  };

  // FUNCION PARA QUE SE PINTE EL CORAZON FAVORITOS
  useEffect(() => {
    setFavorite(() => {
      const initialStatus = favorites.reduce((status, fav) => {
        status[fav.bicicleta.bicicletaId] = true;
        return status;
      }, {});
      console.log(initialStatus);
      return initialStatus;
    });
  }, [favorites]);

  return (
    <Section>
      <div className='max-w-[1200px] mx-auto '>
        <h2 className='text-lg sm:text-2xl font-semibold pt-6'>Favoritos</h2>
        <div className='grid grid-cols-1 gap-4 ssm:grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-5 '>
          {favorites.map((item) => (
            <div
              className={`relative mt-8 mb-8 rounded-xl transition-transform transform-gpu duration-300 shadow-md hover:-translate-y-1 hover:scale-105`}
              key={item.bicicleta.bicicletaId}
            >
              {/*  Boton de favoritos */}
              <div className='absolute text-primary right-6 top-[240px] text-[25px]'>
                {isAuthenticated && (
                  <button
                    onClick={() => {
                      handleFav(item.bicicleta.bicicletaId);
                      handleToggleFavorite(item.bicicleta.bicicletaId);
                    }}
                  >
                    {favorite[item.bicicleta.bicicletaId] ? (
                      <FaHeart />
                    ) : (
                      <FaRegHeart />
                    )}
                  </button>
                )}
              </div>
              <Link
                to={`/description/${item.bicicleta.bicicletaId}`}
                className=''
              >
                <div>
                  <img
                    className='rounded-t-xl  w-full h-48 object-contain'
                    src={item.bicicleta.imagenes[0].url}
                    alt={item.bicicleta.nombre}
                  />
                </div>

                <p className='pl-4 pt-4'>{item.bicicleta.nombre}</p>
                <p className='p-4 font-bold '>
                  Desde ${item.bicicleta.precioAlquilerPorDia}/día
                </p>
              </Link>
            </div>
          ))}
        </div>
      </div>
    </Section>
  );
}
