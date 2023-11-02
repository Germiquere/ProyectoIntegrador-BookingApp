import React, { useContext } from "react";
import { SearchBar } from "./SearchBar";
import { Calendar } from "./Calendar/Calendar";
import { BsXCircle } from "react-icons/bs";
import { CalendarAndSearchContext } from "../../../context/CalendarSearchContext";
import { Link } from "react-router-dom";
export const ResponsiveCalendarAndSearch = () => {
    const { handleOpenCalendarAndSearch } = useContext(
        CalendarAndSearchContext
    );
    return (
        <form
            className={`absolute z-40 h-full transform  w-full bg-gray-100 sm:hidden p-3 transition-all duration-500`}
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
                <div className="flex justify-center w-full">
                    <div className="w-full">
                        <Link to={`description/1`}>
                            <button
                                className="w-full h-11 middle none center  rounded-full bg-primary py-3 px-6 font-sans text-xs font-bold uppercase text-white shadow-sm transition-all  hover:shadow-secondary active:opacity-[0.85] active:shadow-none disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none"
                                data-ripple-light="true"
                                onClick={handleOpenCalendarAndSearch}
                            >
                                Buscar
                            </button>
                        </Link>
                    </div>
                </div>
            </div>
            <BsXCircle
                className="absolute top-3 right-3 text-2xl cursor-pointer text-primary"
                onClick={handleOpenCalendarAndSearch}
            />
        </form>
    );
};
