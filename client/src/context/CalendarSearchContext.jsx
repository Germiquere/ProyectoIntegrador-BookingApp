import { useState, useEffect, createContext, useContext } from "react";
import { useForm } from "../hooks/useForm";
import { getDatesbyBikeId } from "../api/bikes";
const formData = {
    search: "",
    startDate: "",
    endDate: "",
};
export const CalendarAndSearchContext = createContext();
export const useCalendarAndSearchContext = () => {
    return useContext(CalendarAndSearchContext);
};
export function CalendarAndSearchProvider({ children }) {
    const [openCalendarAndSearch, setOpenCalendarAndSearch] = useState(false);
    const [datesData, setDatesData] = useState([]);
    const [loadingDates, setLoadingDates] = useState(false);
    // TODO: LUEGO DEL SPRINT CAMBIAR EL VALOR A FALSE
    const [errorDates, setErrorDates] = useState(false);
    const { onInputChange, formState, setFormState, onResetForm } =
        useForm(formData);
    const handleOpenCalendarAndSearch = () => {
        setOpenCalendarAndSearch(!openCalendarAndSearch);
    };
    const fetchDatesByBikeId = async (id) => {
        // MANEJO EL ESTADO  DEL LOADING EN TRUE
        setLoadingDates(true);
        try {
            // LLAMO A LA FUNCION GET DEL ARCHIVO categories.js
            const data = await getDatesbyBikeId(id);
            // TENER EN CUENTA COMO VIENE MI DATA
            setDatesData(data);
            return data;
        } catch (err) {
            setErrorDates(err);
        } finally {
            // MANEJO EL ESTADO DEL LOADING EN FALSE UNA  VEZ TERMINADO EL FETCH YA SEA EXITOSO O NO
            setLoadingDates(false);
        }
    };
    return (
        <CalendarAndSearchContext.Provider
            value={{
                // PROPIEDADES
                formState,
                openCalendarAndSearch,
                datesData,
                loadingDates,
                errorDates,
                // METODOS
                onInputChange,
                onResetForm,
                setFormState,
                setOpenCalendarAndSearch,
                handleOpenCalendarAndSearch,
                fetchDatesByBikeId,
                setErrorDates,
                setDatesData,
            }}
        >
            {children}
        </CalendarAndSearchContext.Provider>
    );
}
