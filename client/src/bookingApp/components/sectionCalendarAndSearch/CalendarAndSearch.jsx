import React, { useContext } from "react";
import Section from "../Section";
import { SearchBar } from "./SearchBar";
import { Calendar } from "./Calendar/Calendar";
import { CalendarAndSearchContext } from "../../../context/CalendarSearchContext";
import { Link, useNavigate } from "react-router-dom";
import { BsSearch } from "react-icons/bs";
export const CalendarAndSearch = () => {
    const { handleOpenCalendarAndSearch, onResetForm, formState } = useContext(
        CalendarAndSearchContext
    );
    const navigate = useNavigate();
    const { search, startDate, endDate } = formState;
    const handleSubmit = (e) => {
        e.preventDefault();
        if (search.length === 0) return;
        navigate(
            `/items?search=${search}${
                startDate ? `&startDate=${startDate}` : ""
            }${endDate ? `&endDate=${endDate}` : ""}&offset=0`
        );
    };
    return (
        <Section
            className={`bg-[url('https://enduro-mtb.com/wp-content/uploads/2016/11/Affrodable-bike-group-test-ENDURO-magazine-7685-122-2000x500.jpg')] bg-cover bg-no-repeat bg-center sm:bg-none`}
        >
            <div className="h-48 flex items-center justify-center max-w-[1200px] mx-auto relative  bg-[url('https://enduro-mtb.com/wp-content/uploads/2016/11/Affrodable-bike-group-test-ENDURO-magazine-7685-122-2000x500.jpg')] bg-cover bg-no-repeat bg-center sm:mt-5 sm:rounded-xl  ">
                <form
                    className="flex items-center justify-center absolute bottom-[-22px] shadow-2xl shadow-primary  rounded-full"
                    onSubmit={handleSubmit}
                >
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

                    <button
                        className="hidden sm:flex sm:items-center sm:justify-end h-11 middle none center  rounded-r-full bg-white py-3 px-3 font-sans text-xs font-bold uppercase text-white shadow-sm transition-all  active:opacity-[0.85] active:shadow-none disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none"
                        data-ripple-light="true"
                    >
                        <div className="h-8 w-8 rounded-full bg-primary hover:shadow-secondary flex items-center justify-center hover:shadow-xl font-normal transition-all">
                            <BsSearch />
                        </div>
                    </button>
                </form>
            </div>
        </Section>
    );
};
