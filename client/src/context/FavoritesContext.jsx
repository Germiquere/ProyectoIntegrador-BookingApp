import { createContext, useContext, useState, useEffect } from "react";
import { getFavorite, postFavorites, deleteFavorite } from "../api/fav";
import { useUsersContext } from "./UsersContext";

export const FavoritesContext = createContext();
export const useFavoritesContext = () => {
    return useContext(FavoritesContext);
};

export const FavoritesProvider = ({ children }) => {
    const [favorites, setFavorites] = useState([]);
    const [error, setError] = useState(null);
    const [loadingFavorites, setLoadingFavorites] = useState(false);
    const { userData, isAuthenticated, rol } = useUsersContext();

    // FUNCION PARA HACER  EL GET
    const fetchData = async (id) => {
        setLoadingFavorites(true);
        try {
            const data = await getFavorite(id);
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
    // FUNCION PARA AGREGAR O QUITAR FAVORITO
    const handleFav = (id, user) => {
        const isFavorite = favorites.some(
            (fav) => fav.bicicleta.bicicletaId === id
        );
        if (isFavorite) {
            handleRemoveFavorite(id);
        } else {
            handleAddFavorite(user.usuarioId, id);
        }
    };

    // FUNCION PARA AÑADIR FAVORITOS
    const handleAddFavorite = async (userId, bikeId) => {
        try {
            await addNewFavorite(userId, bikeId);
        } catch (err) {
            console.error("Error al agregar favorito:", err);
        }
    };

    // FUNCION PARA REMOVER FAVORITOS
    const handleRemoveFavorite = async (id) => {
        const favoriteDelete = favorites.find(
            (fav) => fav.bicicleta.bicicletaId === id
        );
        try {
            await removeAFavorite(favoriteDelete.favoritoId);
        } catch (err) {
            console.error("Error al eliminar favorito:", err);
        }
    };

    useEffect(() => {
        if (isAuthenticated && rol === "user") {
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
                handleFav,
            }}
        >
            {children}
        </FavoritesContext.Provider>
    );
};
