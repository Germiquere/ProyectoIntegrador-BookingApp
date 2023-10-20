import React, { useContext } from "react";
import Section from "../Section";
import { SearchBar } from "./SearchBar";
import { Calendar } from "./Calendar/Calendar";
import { CalendarAndSearchContext } from "../../../context/CalendarSearchContext";
import { Link } from "react-router-dom";

export const CalendarAndSearch = () => {
    const { handleOpenCalendarAndSearch, onResetForm, formState } = useContext(
        CalendarAndSearchContext
    );

    return (
        <Section
            className={`bg-[url('https://enduro-mtb.com/wp-content/uploads/2016/11/Affrodable-bike-group-test-ENDURO-magazine-7685-122-2000x500.jpg')] bg-cover bg-no-repeat bg-center `}
        >
            <div className="h-80 flex items-center justify-center max-w-[1200px] mx-auto">
                <div className="flex items-center justify-center">
                    <div className="relative w-full min-w-[200px] flex-1">
                        <SearchBar />
                        <div
                            className="absolute top-0 w-full  h-11 bg-transparent cursor-pointer z-30 sm:hidden sm:cursor-not-allowed"
                            // TODO AL HACER CLICK QUE SE ABRA EL RESPONSIVECALENDARANDSEARCH
                            onClick={handleOpenCalendarAndSearch}
                        ></div>
                    </div>
                    <div className="hidden sm:block flex-1">
                        <Calendar />
                    </div>
                    <Link to={`description/1`}>
                        <button
                            className="hidden sm:block h-11 middle none center  rounded-r-full bg-primary py-3 px-6 font-sans text-xs font-bold uppercase text-white shadow-sm transition-all  hover:shadow-secondary active:opacity-[0.85] active:shadow-none disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none"
                            data-ripple-light="true"
                        >
                            Buscar
                        </button>
                    </Link>
                </div>
            </div>
        </Section>
    );
};
