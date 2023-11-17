import { useContext } from "react";
import { BsSearch } from "react-icons/bs";
import { CalendarAndSearchContext } from "../../../context/CalendarSearchContext";
export const SearchBar = () => {
    const { onInputChange, formState } = useContext(CalendarAndSearchContext);
    return (
        <>
            <div className="relative h-11 w-full sm:min-w-[200px] sm:z-20  ">
                <input
                    className="peer h-full w-full sm:rounded-l-full sm:rounded-r-none  sm:border-r-[1px]  sm:border-gray-100 rounded-full  p-3 font-sans text-sm font-normal text-blue-gray-700 outline outline-0 transition-all  focus:outline-0  disabled:bg-blue-gray-50 "
                    placeholder="Busca tu bicicleta"
                    value={formState.search}
                    name={"search"}
                    onChange={onInputChange}
                />

                <BsSearch className="absolute top-1/2 right-3 transform -translate-y-1/2 cursor-pointer text-white sm:hidden bg-primary  rounded-full p-2 text-3xl" />
            </div>
        </>
    );
};
