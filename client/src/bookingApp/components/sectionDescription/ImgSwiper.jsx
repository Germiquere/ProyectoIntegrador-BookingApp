import { Navigation, Pagination } from "swiper/modules";

import { Swiper, SwiperSlide } from "swiper/react";
import { BsXLg } from "react-icons/bs";
import { IoIosArrowDropleft, IoIosArrowDropright } from "react-icons/io";
// Import Swiper styles
import "swiper/css";
import "swiper/css/navigation";
import "swiper/css/pagination";
export const ImgSwiper = ({ handleToggleImgGallery, data }) => {
    return (
        <Swiper
            className="relative"
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
            {data.map((item) => (
                <SwiperSlide key={item.id}>
                    <div className=" overflow-hidden mx-10">
                        <div className=" w-full">
                            <img
                                src={item.img}
                                alt="imagen de galeria"
                                //  cambiar el tamanio a gusto
                                className=" w-full object-contain h-56 sm:h-72 md:h-96 transition-transform group-hover:scale-105 ease-in-out duration-200"
                            />
                        </div>
                    </div>
                    {/* <SkeletonCardsSweiper /> */}
                </SwiperSlide>
            ))}
            <div
                className="flex gap-2 w-full justify-center mt-5
            "
            >
                {/* <IoIosArrowDropleft className="prev-slide text-2xl sm:text-3xl text-neutral-800 hover:text-primary cursor-pointer ease-in-out duration-200 hidden sm:block" />
                <IoIosArrowDropright className="next-slide text-2xl sm:text-3xl text-neutral-800 hover:text-primary cursor-pointer ease-in-out duration-200 hidden sm:block" /> */}
            </div>
            <IoIosArrowDropleft className="absolute prev-slide cursor-pointer text-primary  top-1/2 z-10 left-2 text-2xl  transform -translate-y-1/2 ease-in-out duration-200 hidden sm:block" />

            <IoIosArrowDropright className="absolute next-slide cursor-pointer text-primary  z-10 top-1/2 right-2 text-2xl  transform -translate-y-1/2 ease-in-out duration-200 hidden sm:block" />
            <BsXLg
                className="absolute next-slide cursor-pointer text-primary  z-10 top-5 right-2 text-2xl  transform -translate-y-1/2 ease-in-out duration-200 hidden sm:block"
                onClick={handleToggleImgGallery}
            />
        </Swiper>
    );
};
