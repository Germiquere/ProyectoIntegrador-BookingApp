import { BsXLg } from "react-icons/bs";

export const CreateProductModal = () => {
    return (
        <>
            <div
                className={` rounded-xl max-h-[600px] overflow-hidden bg-white  fixed top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 max-w-[1200px] min-w-[700px] mx-auto transition-opacity duration-200 z-50 `}
            >
                {/* HEADER */}
                <div className=" w-full h-16  flex justify-between p-3 border-b-[1px] border-gray-300 bg-primary text-white">
                    <h2 className="text-xl font-semibold flex gap-5 items-center">
                        <p>Administrar Categorias</p>
                        {/* {loadingBikes && <Loader className={"text-white"} />} */}
                    </h2>
                    <button
                        className="flex  items-center justify-center middle none center rounded-full  h-7 w-7 font-sans text-xs font-bold uppercase  transition-all hover:bg-blackOpacity1 active:bg-tertiary disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none "
                        data-ripple-dark="true"
                        disabled={loadingBikes}
                        onClick={() => {
                            setOpenNewProductModal(false);
                            setImageChange([]);
                            onResetForm();
                            setError(false);
                        }}
                    >
                        <BsXLg className="text-lg" />
                    </button>
                </div>
                {/* MAIN */}
                <div className=" p-5 max-h-[450px] overflow-auto mb-16"></div>
            </div>

            <div
                className={`fixed top-0 left-0 w-full h-full bg-black bg-opacity-70 transition-opacity duration-200 z-40`}
            ></div>
        </>
    );
};
