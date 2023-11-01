import { BsImages } from "react-icons/bs";
import ImgsSwiper from "./ImgsSwiper";

export const ImgGallery = ({ data, handleToggleImgGallery, loadingBikes }) => {
    let images = data.imagenes;
    if (images.length > 5) {
        images = images.slice(1, 5);
    } else if (images.length > 1) {
        images = images.slice(1);
    }
    while (images.length < 4) {
        images.push({
            id: images.length + 1,
            url: "https://res.cloudinary.com/djslo5b3u/image/upload/v1698001521/no-image-icon-23485-removebg-preview_ch9maq.png",
        });
    }

    return (
        <div>
            <div className="md:grid grid-cols-2 max-h-[580px] min-h-[335px] gap-2 rounded-2xl overflow-hidden relative hidden ">
                {/* Div con imagen a la izquierda */}
                <div className="md:col-span-1 max-h-[580px]  ">
                    <img
                        src={data.imagenes[0].url}
                        alt="Imagen"
                        className="h-full w-full object-cover"
                    />
                </div>

                {/* Div a la derecha */}
                <div className="md:col-span-1">
                    <div className="grid grid-cols-2 grid-rows-2 h-full gap-2">
                        {images.map((img, index) => (
                            <div
                                key={index * 2345}
                                className="col-span-1 row-span-1 bg-gray-100"
                            >
                                <img
                                    className="h-full w-full object-cover"
                                    src={img.url}
                                    alt="una imagen de una bicicleta"
                                />
                            </div>
                        ))}
                        {/* Primer div de la grilla */}
                    </div>
                </div>
                <p
                    onClick={handleToggleImgGallery}
                    className="absolute bottom-4 right-4 font-semibold flex items-center gap-2 cursor-pointer border-grayTertiary border-[1px] rounded-md p-1 bg-white hover:bg-gray-100"
                >
                    <span>
                        <BsImages />
                    </span>
                    Mostrar todas las fotos
                </p>
            </div>
            <div className="md:hidden">
                <ImgsSwiper data={data} />
            </div>
        </div>
    );
};

// import React, { useEffect, useState } from "react";

// export const ImgGallery = ({ data, handleToggleImgGallery, loadingBikes }) => {
//     return (

//         <div className="flex  flex-col lg:flex-row items-center gap-3 lg:justify-center max-h-[580px]">
//             {/* <div className="   w-48 h-48 lg:w-80 lg:h-80 xl:w-96 xl:h-96  "> */}
//             <div className="  rounded-xl shadow-xl overflow-hidden ">
//                 <img
//                     src={data.imagenes[0].url}
//                     alt=""
//                     className=" max-h-[580px]  object-cover "
//                 />
//             </div>
//             <div className="flex gap-3 justify-center flex-col sm:flex-row lg:flex-col flex-1 ">
//                 {/* DIV PARES */}
//                 <div className="flex gap-3 flex-1">
//                     {data.imagenes.slice(1, 5).map((item, index) => {
//                         if (index % 2 === 0) {
//                             return (
//                                 <div
//                                     key={item.id}
//                                     className="bg-red-200 flex-1 rounded-xl shadow-xl overflow-hidden hidden md:block "
//                                 >
//                                     <img
//                                         src={item.url}
//                                         alt=""
//                                         className="w-full max-h-[250px] object-cover"
//                                     />
//                                 </div>
//                             );
//                         } else {
//                             return null; // No se mostrará nada en los div impares
//                         }
//                     })}
//                 </div>
//                 {/* DIV INPARES */}
//                 <div className="flex gap-3 flex-1">
//                     {data.imagenes.slice(1, 5).map((item, index) => {
//                         const isLastItem = index === 4;
//                         if (index % 2 === 1) {
//                             return (
//                                 <div
//                                     key={item.id}
//                                     className={`bg-red-200 flex-1 rounded-xl shadow-xl overflow-hidden ${
//                                         index === 3 ? "relative group " : ""
//                                     }`}
//                                 >
//                                     <img
//                                         src={item.url}
//                                         alt=""
//                                         className="w-full max-h-[250px]  object-cover"
//                                     />

//                                     {index === 3 && (
//                                         <button
//                                             className={` min-w-[80px] absolute opacity-80 group-hover:opacity-100 top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 rounded-full bg-primary py-3 px-3 font-sans text-xs  uppercase text-white shadow-sm transition-all  active:opacity-[0.85] active:shadow-none disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none`}
//                                             data-ripple-light="true"
//                                             onClick={handleToggleImgGallery}
//                                         >
//                                             ver mas
//                                         </button>
//                                     )}
//                                 </div>
//                             );
//                         } else {
//                             return null; // No se mostrará nada en los div impares
//                         }
//                     })}
//                 </div>
//             </div>
//         </div>
//     );
// };
