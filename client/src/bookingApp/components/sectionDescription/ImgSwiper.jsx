import { Navigation, Pagination } from "swiper/modules";

import { Swiper, SwiperSlide } from "swiper/react";
import { BsXLg } from "react-icons/bs";
import { IoIosArrowDropleft, IoIosArrowDropright } from "react-icons/io";
// Import Swiper styles
import "swiper/css";
import "swiper/css/navigation";
import "swiper/css/pagination";
export const ImgSwiper = ({ handleToggleImgGallery, data }) => {
    console.log(data.imagenes);
    return (
        <Swiper
            className="relative hidden md:block "
            modules={[Navigation, Pagination]}
            // esto es para poder ponerle una clase a cualquier elemnto y que tenga la funcionalidad de siguiente y previo
            navigation={{
                nextEl: ".next-slide",
                prevEl: ".prev-slide",
            }}
            // es para que sea infinito
            // loop
            // es para que aparezcan las pelotitas de la paginacion
            pagination={{
                // para que pueda  clickear en las pelotitas
                clickable: true,
            }}
            slidesPerView={1}
            loop
        >
            {data.imagenes.map((item) => (
                <SwiperSlide key={item.id}>
                    <div
                    //     className="bg-contain bg-fixed
                    // bg-no-repeat
                    // bg-center w-full h-[500px] flex items-center "
                    //     style={{
                    //         backgroundImage: `url(${item.url})`,
                    //         // backgroundAttachment: "fixed",
                    //         // backgroundPosition: "center",
                    //         // backgroundSize: "cover",
                    //     }}
                    >
                        <img
                            className="h-[500px] w-full object-contain "
                            src={item.url}
                            alt="imagen de un producto"
                        />
                        {/* El contenido del contenedor */}
                    </div>

                    {/* <SkeletonCardsSweiper /> */}
                </SwiperSlide>
            ))}

            <IoIosArrowDropleft
                className="p-1 absolute prev-slide cursor-pointer text-gray-100 top-1/2 z-10 left-2.5 text-4xl transform -translate-y-1/2 ease-in-out duration-200 hidden sm:block"
                style={{ background: "rgba(0, 0, 0, 0.8)" }}
            />

            <IoIosArrowDropright
                style={{ background: "rgba(0, 0, 0, 0.8)" }}
                className="p-1 absolute next-slide cursor-pointer text-gray-100 z-10 top-1/2 right-2 text-4xl  transform -translate-y-1/2 ease-in-out duration-200 hidden sm:block"
            />
            <BsXLg
                style={{ background: "rgba(0, 0, 0, 0.8)" }}
                className="p-1 absolute next-slide cursor-pointer text-gray-100 z-10 top-5 right-2 text-4xl  transform -translate-y-1/2 ease-in-out duration-200 hidden sm:block"
                onClick={handleToggleImgGallery}
            />
        </Swiper>
    );
};
