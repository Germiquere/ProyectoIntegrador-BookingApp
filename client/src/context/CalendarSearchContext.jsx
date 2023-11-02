import { useState, useEffect, createContext } from "react";
import { useForm } from "../hooks/useForm";
const formData = {
    search: "",
    startDate: "",
    endDate: "",
};
export const CalendarAndSearchContext = createContext();
export function CalendarAndSearchProvider({ children }) {
    const [openCalendarAndSearch, setOpenCalendarAndSearch] = useState(false);
    const { onInputChange, formState, setFormState, onResetForm } =
        useForm(formData);
    console.log(formState);
    const handleOpenCalendarAndSearch = () => {
        setOpenCalendarAndSearch(!openCalendarAndSearch);
    };

    return (
        <CalendarAndSearchContext.Provider
            value={{
                // PROPIEDADES
                formState,
                openCalendarAndSearch,
                // METODOS
                onInputChange,
                onResetForm,
                setFormState,
                setOpenCalendarAndSearch,
                handleOpenCalendarAndSearch,
            }}
        >
            {children}
        </CalendarAndSearchContext.Provider>
    );
}
