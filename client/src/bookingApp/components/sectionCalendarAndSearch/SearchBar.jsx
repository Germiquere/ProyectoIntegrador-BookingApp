import { BsSearch } from "react-icons/bs";
export const SearchBar = () => {
    return (
        <div className="relative h-11 w-full sm:min-w-[200px] sm:z-40 ">
            <input
                className="peer h-full w-full sm:rounded-l-full sm:rounded-r-none rounded-full  p-3 font-sans text-sm font-normal text-blue-gray-700 outline outline-0 transition-all  focus:outline-0  disabled:bg-blue-gray-50 "
                placeholder="Busca tu bicicleta"
            />

            <BsSearch className="absolute top-1/2 right-3 transform -translate-y-1/2 cursor-pointer text-gray-400 sm:hidden" />
        </div>
    );
};
