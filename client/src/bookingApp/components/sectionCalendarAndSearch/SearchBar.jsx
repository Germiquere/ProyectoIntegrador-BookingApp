import { useContext, useState } from "react";
import { BsSearch } from "react-icons/bs";
import { CalendarAndSearchContext } from "../../../context/CalendarSearchContext";
import { useBikesContext } from "../../../context/BikesContext";
import { Loader } from "../../../ui/Loader";
export const SearchBar = () => {
    const [openSearch, setOpenSearch] = useState(false);
    const { onInputChange, formState, setFormState } = useContext(
        CalendarAndSearchContext
    );
    const {
        bikesData,
        bikesDataPaginated,
        fetchPaginatedData,
        loadingPagination,
    } = useBikesContext();
    const { search = "" } = formState;
    const handleInputChange = (e) => {
        onInputChange(e);
        fetchPaginatedData(0, search);
    };
    const handleClick = (bike) => {
        setFormState({
            ...formState,
            search: bike,
        });
        setOpenSearch(false);
    };
    return (
        <>
            <div className="relative h-11 w-full sm:min-w-[200px] sm:z-20  ">
                <input
                    className="peer h-full w-full sm:rounded-l-full sm:rounded-r-none  sm:border-r-[1px]  sm:border-gray-100 rounded-full  p-3 font-sans text-sm font-normal text-blue-gray-700 outline outline-0 transition-all  focus:outline-0  disabled:bg-blue-gray-50 "
                    placeholder="Busca tu bicicleta"
                    autoComplete="off"
                    value={search}
                    name={"search"}
                    onChange={handleInputChange}
                    onFocus={() => setOpenSearch(true)}
                />
                <BsSearch className="absolute top-1/2 right-3 transform -translate-y-1/2 cursor-pointer text-white sm:hidden bg-primary  rounded-full p-2 text-3xl" />
            </div>
            {openSearch && search.length > 0 && (
                <div className="absolute top-14 left-0 w-full bg-white z-30 rounded-md p-2 hidden sm:block h-72  overflow-y-auto">
                    {!loadingPagination &&
                        bikesDataPaginated.content?.map((bike) => (
                            <p
                                key={bike.bicicletaId}
                                onClick={() => {
                                    handleClick(bike.nombre);
                                }}
                                className="cursor-pointer p-2 text-sm "
                            >
                                {bike.nombre}
                            </p>
                        ))}
                </div>
            )}
        </>
    );
};
