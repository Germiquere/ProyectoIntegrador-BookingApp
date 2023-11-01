import { MdOutlineDeleteForever } from "react-icons/md";

export const ConfirmDelete = ({
    handleDeleteProduct,
    setOpenConfirmDelete,
}) => {
    return (
        <>
            <div
                className={` rounded-xl h-96 overflow-hidden bg-white  fixed top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 w-72 mx-auto transition-opacity duration-200 z-50 `}
            >
                <div className="bg-primary w-full h-44 flex justify-center items-center text-white">
                    <MdOutlineDeleteForever className="text-9xl" />
                </div>
                <div className="flex flex-col items-center justify-around py-10 text-center">
                    <h2 className="pb-10 text-2xl font-semibold">
                        Estas seguro que deseas eliminar este producto?
                    </h2>
                    <div className="flex gap-2 ">
                        <button
                            className="middle none center mr-3 rounded-full border border-primary py-3 px-6 font-sans text-xs font-bold uppercase text-primary transition-all hover:opacity-75 focus:ring focus:ring-tertiary active:opacity-[0.85] disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none"
                            data-ripple-dark="true"
                            onClick={handleDeleteProduct}
                        >
                            CONFIRMAR
                        </button>
                        <button
                            className="middle none center mr-3 rounded-full bg-primary py-3 px-6 font-sans text-xs font-bold uppercase text-white shadow-sm  transition-all  hover:shadow-secondary  active:opacity-[0.85] active:shadow-none disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none"
                            data-ripple-light="true"
                            onClick={() => setOpenConfirmDelete(false)}
                        >
                            CANCELAR
                        </button>
                    </div>
                </div>
            </div>
            <div
                className={`fixed top-0 left-0 w-full h-full bg-black bg-opacity-30 transition-opacity duration-200 z-40`}
            ></div>
        </>
    );
};
