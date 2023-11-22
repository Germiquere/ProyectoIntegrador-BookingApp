import { createContext, useContext, useState, useEffect } from 'react';
import { getFavorite, postFavorites, deleteFavorite } from '../api/fav';
import { useUsersContext } from './UsersContext';

export const FavoritesContext = createContext();
export const useFavoritesContext = () => {
  return useContext(FavoritesContext);
};

export const FavoritesProvider = ({ children }) => {
  const [favorites, setFavorites] = useState([]);
  const [error, setError] = useState(null);
  const [loadingFavorites, setLoadingFavorites] = useState(false);
  const { userData, isAuthenticated } = useUsersContext();

  // FUNCION PARA HACER  EL GET
  const fetchData = async (id) => {
    setLoadingFavorites(true);
    try {
      const data = await getFavorite(id);
      console.log('Datos obtenidos:', data);
      setFavorites(data);
    } catch (err) {
      setError(err);
    } finally {
      setLoadingFavorites(false);
    }
  };

  // FUNCION PARA AGREGAR UNA FAVORITOS
  const addNewFavorite = async (userId, bikeId) => {
    setLoadingFavorites(true);
    try {
      const newFavorite = await postFavorites(userId, bikeId);
      fetchData(userData.usuarioId);
      return newFavorite;
    } catch (err) {
      setError(err);
    } finally {
      setLoadingFavorites(false);
    }
  };

  // FUNCION PARA ELIMINAR A FAVORITOS
  const removeAFavorite = async (id) => {
    setLoadingFavorites(true);
    try {
      const deletedFavorite = await deleteFavorite(id);

      fetchData(userData.usuarioId);
      return deletedFavorite;
    } catch (err) {
      setError(err);
    } finally {
      setLoadingFavorites(false);
    }
  };

  useEffect(() => {
    console.log(isAuthenticated);
    if (isAuthenticated) {
      console.log(userData.usuarioId);
      fetchData(userData.usuarioId);
    }
  }, [isAuthenticated]);

  return (
    <FavoritesContext.Provider
      value={{
        // PROPIEDADES
        favorites,
        error,
        loadingFavorites,
        // METODOS
        setFavorites,
        addNewFavorite,
        removeAFavorite,
      }}
    >
      {children}
    </FavoritesContext.Provider>
  );
};
