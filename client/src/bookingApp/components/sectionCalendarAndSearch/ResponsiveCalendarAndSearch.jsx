import React, { useContext } from "react";
import { SearchBar } from "./SearchBar";
import { Calendar } from "./Calendar/Calendar";
import { BsXCircle } from "react-icons/bs";
import { CalendarAndSearchContext } from "../../../context/CalendarSearchContext";
export const ResponsiveCalendarAndSearch = () => {
    const { handleOpenCalendarAndSearch, openCalendarAndSearch } = useContext(
        CalendarAndSearchContext
    );
    return (
        <form
            className={`absolute z-40 h-full transform ${
                openCalendarAndSearch ? "translate-y-[-100%]" : "translate-y-0"
            } w-full bg-gray-100 sm:hidden p-3 transition-all duration-500`}
        >
            <div className="flex flex-col justify-center h-full gap-3">
                <div>
                    <h2 className="text-lg  font-semibold pb-2">
                        ¿Que tipo de bicicleta buscas ?
                    </h2>
                    <SearchBar />
                </div>
                <div>
                    <h2 className="text-lg  font-semibold pb-2">
                        ¿Cuando queres reservar?
                    </h2>
                    <Calendar />
                </div>
                <div className="flex justify-center">
                    <button
                        className="h-11 middle none center rounded-full sm:rounded-l-none rounded-r-full bg-primary py-3 px-6 font-sans text-xs font-bold uppercase text-white shadow-md shadow-pink-500/20 transition-all hover:shadow-lg hover:shadow-pink-500/40  active:opacity-[0.85] active:shadow-none disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none"
                        data-ripple-light="true"
                    >
                        Buscar
                    </button>
                </div>
            </div>
            <BsXCircle
                className="absolute top-3 right-3 text-2xl cursor-pointer text-primary"
                onClick={handleOpenCalendarAndSearch}
            />
        </form>
    );
};
