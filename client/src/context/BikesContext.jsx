import { createContext, useContext, useEffect, useState } from "react";
import { getBikeById, getBikes } from "../api/bikes";

const BikesContext = createContext();
// FUNCION PARA LLAMAR AL CONTEXTO EN EL COMPONENTE QUE QUERAMOS
export const useBikesContext = () => {
    return useContext(BikesContext);
};
export const BikesProvider = ({ children }) => {
    const [bikesData, setBikesData] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [bikeById, setBikeById] = useState([]);
    const fetchData = async () => {
        // MANEJO EL ESTADO  DEL LOADING EN TRUE
        setLoading(true);
        try {
            // LLAMO A LA FUNCION GET DEL ARCHIVO categories.js
            const data = await getBikes();
            // TENER EN CUENTA COMO VIENE MI DATA
            setBikesData(data);
        } catch (err) {
            setError(err);
        } finally {
            // MANEJO EL ESTADO DEL LOADING EN FALSE UNA  VEZ TERMINADO EL FETCH YA SEA EXITOSO O NO
            setLoading(false);
        }
    };
    const bikeByIdGet = async (id) => {
        // MANEJO EL ESTADO  DEL LOADING EN TRUE
        setLoading(true);
        try {
            // LLAMO A LA FUNCION GET DEL ARCHIVO categories.js
            const data = await getBikeById(id);
            // TENER EN CUENTA COMO VIENE MI DATA
            setBikeById(data);
        } catch (err) {
            setError(err);
        } finally {
            // MANEJO EL ESTADO DEL LOADING EN FALSE UNA  VEZ TERMINADO EL FETCH YA SEA EXITOSO O NO
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchData();
    }, []);

    return (
        <BikesContext.Provider
            value={{
                //PROPIEDADES
                bikesData,
                loading,
                error,
                bikeById,
                //METODOS
                bikeByIdGet,
            }}
        >
            {children}
        </BikesContext.Provider>
    );
};
