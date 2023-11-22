import React, { useContext, useEffect } from "react";
import { SearchBar } from "./SearchBar";
import { Calendar } from "./Calendar/Calendar";
import { BsXCircle } from "react-icons/bs";
import { CalendarAndSearchContext } from "../../../context/CalendarSearchContext";
import { Link, useNavigate } from "react-router-dom";
import { useBikesContext } from "../../../context/BikesContext";
import queryString from "query-string";
export const ResponsiveCalendarAndSearch = () => {
    const { handleOpenCalendarAndSearch, formState, setFormState } = useContext(
        CalendarAndSearchContext
    );

    const {
        bikesData,
        bikesDataPaginated,
        fetchPaginatedData,
        loadingPagination,
    } = useBikesContext();
    const {
        search,
        startDate = "",
        endDate = "",
        offset = 0,
    } = queryString.parse(location.search);
    const navigate = useNavigate();
    const handleSubmit = (e) => {
        e.preventDefault();
        if (formState.search.length === 0) return;
        // const filteredResults = filterBicycles();
        // setFilteredBicycles(filteredResults);
        fetchPaginatedData(0, formState.search);
        handleOpenCalendarAndSearch();
        navigate(
            `/items?search=${formState.search}${
                formState.startDate ? `&startDate=${formState.startDate}` : ""
            }${
                formState.endDate ? `&endDate=${formState.endDate}` : ""
            }&offset=0`
        );
    };
    useEffect(() => {
        setFormState({
            search,
            startDate,
            endDate,
        });
    }, []);
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
                        <button
                            className="w-full h-11 middle none center  rounded-full bg-primary py-3 px-6 font-sans text-xs font-bold uppercase text-white shadow-sm transition-all  hover:shadow-secondary active:opacity-[0.85] active:shadow-none disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none"
                            data-ripple-light="true"
                            onClick={handleSubmit}
                        >
                            Buscar
                        </button>
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
