import { useState, useEffect, createContext } from "react";

export const CalendarAndSearchContext = createContext();

export function CalendarAndSearchProvider({ children }) {
    const [openCalendarAndSearch, setOpenCalendarAndSearch] = useState(false);
    const handleOpenCalendarAndSearch = () => {
        setOpenCalendarAndSearch(!openCalendarAndSearch);
    };

    return (
        <CalendarAndSearchContext.Provider
            value={{
                openCalendarAndSearch,
                setOpenCalendarAndSearch,
                handleOpenCalendarAndSearch,
            }}
        >
            {children}
        </CalendarAndSearchContext.Provider>
    );
}
