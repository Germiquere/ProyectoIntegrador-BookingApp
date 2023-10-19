import React, { useEffect, useState } from "react";

export const ImgGallery = () => {
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
        <div className="flex  flex-col sm:flex-row">
            <div className="  bg-red-400 w-48 h-48 ">
                {/* <img
                    src="https://alquiler-deporte.decathlon.es/storage/3550/gallery-iWIwE-ELOPS-VELO-VILLE-ELOPS-520-LF-BLEU---000-----Expires-on-25-04-2031-(1).jpg"
                    alt=""
                    className=""
                /> */}
            </div>
            <div className="flex gap-3 justify-center sm:flex-col ">
                <div className="flex gap-3">
                    <div className="bg-blue-200 w-20 h-20">
                        <img
                            src="https://alquiler-deporte.decathlon.es/storage/3594/gallery-H7C2I-Explore500-0.PNG"
                            alt=""
                            className="w-full h-full object-cover"
                        />
                    </div>

                    <div className="bg-green-200  w-20 h-20">
                        <img
                            src="https://alquiler-deporte.decathlon.es/storage/3598/gallery-czl1Z-Explore-500---4.PNG"
                            alt=""
                            className="w-full h-full object-cover"
                        />
                    </div>
                </div>
                <div className="flex gap-3">
                    <div className="bg-red-200  w-20 h-20">
                        <img
                            src="https://alquiler-deporte.decathlon.es/storage/3596/gallery-SQzoB-Explore500-2.PNG"
                            alt=""
                            className="w-full h-full object-cover"
                        />
                    </div>
                    <div className="bg-yellow-200  w-20 h-20">
                        <img
                            src="https://alquiler-deporte.decathlon.es/storage/3597/gallery-iC29s-Explore500---3.PNG"
                            alt=""
                            className="w-full h-full object-cover"
                        />
                    </div>
                </div>
            </div>
        </div>
    );
};
