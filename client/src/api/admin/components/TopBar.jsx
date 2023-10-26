import { BsSearch, BsPlusLg } from "react-icons/bs";
export const TopBar = () => {
    return (
        <div className="w-full flex ">
            <h2>Productos</h2>
            <div className="flex gap-2">
                <div className="relative h-11 w-full sm:min-w-[200px] sm:z-40 ">
                    <input
                        className="peer h-full w-full rounded-full  p-3 font-sans text-sm font-normal text-blue-gray-700 outline outline-0 transition-all  focus:outline-0  disabled:bg-blue-gray-50 "
                        placeholder="Buscar producto"
                        // value={formState.search}
                        name={"search"}
                        // onChange={onInputChange}
                    />

                    <BsSearch className="absolute top-1/2 right-3 transform -translate-y-1/2 cursor-pointer text-gray-400   rounded-full p-2 text-3xl" />
                </div>
                <button
                    className="flex gap-2 middle none center mr-3 rounded-full border border-red-500 py-3 px-6 font-sans text-xs font-bold uppercase text-primary transition-all hover:opacity-75 focus:ring focus:ring-tertiary active:opacity-[0.85] disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none"
                    data-ripple-dark="true"
                >
                    AÑADIR PRODUCTO
                    <BsPlusLg />
                </button>
            </div>
        </div>
    );
};
