import { useState, useEffect, createContext, useContext } from "react";
import { useForm } from "../hooks/useForm";
import { getCharacteristics } from "../api/characteristics";

export const CharacteristicsContext = createContext();
export const useCharacteristicsContext = () => {
    return useContext(CharacteristicsContext);
};
export function CharacteristicsProvider({ children }) {
    const [characteristicsData, setCharacteristicsData] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    // FUNCION PARA TRAER LAS CARACTERISTICAS
    const fetchData = async () => {
        // MANEJO EL ESTADO  DEL LOADING EN TRUE
        setLoading(true);
        try {
            // LLAMO A LA FUNCION GET DEL ARCHIVO categories.js
            const data = await getCharacteristics();
            // TENER EN CUENTA COMO VIENE MI DATA
            setCharacteristicsData(data);
        } catch (err) {
            setError(err);
        } finally {
            // MANEJO EL ESTADO DEL LOADING EN FALSE UNA  VEZ TERMINADO EL FETCH YA SEA EXITOSO O NO
            setLoading(false);
        }
    };
    return (
        <CharacteristicsContext.Provider
            value={{
                // PROPIEDADES
                loading,
                error,
                characteristicsData,
                // METODOS
                fetchData,
            }}
        >
            {children}
        </CharacteristicsContext.Provider>
    );
}
