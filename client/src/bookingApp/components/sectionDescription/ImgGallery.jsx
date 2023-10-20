import React, { useEffect, useState } from "react";

export const ImgGallery = ({ data, handleToggleImgGallery }) => {
    const [producto, setProducto] = useState({});

    const fetchProducts = async () => {
        try {
            const res = await fetch(
                `https://api.mercadolibre.com/items/MLA1391221853`
            );

            if (!res.ok) {
                throw new Error("Error al cargar los productos");
            }
            const data = await res.json();
            setProducto(data);
            console.log(data);
            return data;
        } catch (error) {
            console.log(error);
        }
    };

    useEffect(() => {
        fetchProducts();
    }, []);
    return (
        <div className="flex  flex-col lg:flex-row items-center gap-3 lg:justify-center">
            {/* <div className="   w-48 h-48 lg:w-80 lg:h-80 xl:w-96 xl:h-96  "> */}
            <div className=" flex-1 rounded-xl shadow-xl overflow-hidden ">
                <img
                    src={data[0].img}
                    alt=""
                    className="object-contain w-full h-full "
                />
            </div>
            <div className="flex gap-3 justify-center flex-col sm:flex-row lg:flex-col flex-1">
                {/* DIV PARES */}
                <div className="flex gap-3">
                    {data.slice(1).map((item, index) => {
                        if (index % 2 === 0) {
                            return (
                                <div
                                    key={item.id}
                                    className="bg-red-200 flex-1 rounded-xl shadow-xl overflow-hidden hidden md:block"
                                >
                                    <img
                                        src={item.img}
                                        alt=""
                                        className="w-full h-full object-cover"
                                    />
                                </div>
                            );
                        } else {
                            return null; // No se mostrará nada en los div impares
                        }
                    })}
                </div>
                {/* DIV INPARES */}
                <div className="flex gap-3">
                    {data.slice(1).map((item, index) => {
                        if (index % 2 === 1) {
                            return (
                                <div
                                    key={item.id}
                                    className={`bg-red-200 flex-1 rounded-xl shadow-xl overflow-hidden ${
                                        item.showButton ? "relative group " : ""
                                    }`}
                                >
                                    <img
                                        src={item.img}
                                        alt=""
                                        className="w-full h-full object-cover"
                                    />

                                    {item.showButton && (
                                        <button
                                            className={` min-w-[80px] absolute opacity-80 group-hover:opacity-100 top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 rounded-full bg-primary py-3 px-3 font-sans text-xs  uppercase text-white shadow-sm transition-all  active:opacity-[0.85] active:shadow-none disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none`}
                                            data-ripple-light="true"
                                            onClick={handleToggleImgGallery}
                                        >
                                            ver mas
                                        </button>
                                    )}
                                </div>
                            );
                        } else {
                            return null; // No se mostrará nada en los div impares
                        }
                    })}
                </div>
            </div>
        </div>
    );
};
