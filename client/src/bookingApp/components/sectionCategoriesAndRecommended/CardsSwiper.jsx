// import Swiper core and required modules
import { Navigation, Pagination } from "swiper/modules";
import { Swiper, SwiperSlide } from "swiper/react";
import {
    BsFillArrowLeftCircleFill,
    BsFillArrowRightCircleFill,
} from "react-icons/bs";
import { IoIosArrowDropleft, IoIosArrowDropright } from "react-icons/io";
// Import Swiper styles
import "swiper/css";
import "swiper/css/navigation";
import "swiper/css/pagination";
import { Link } from "react-router-dom";

// DATA HARCODEADA
export const data = [
    {
        id: 1,
        img: "https://res.cloudinary.com/djslo5b3u/image/upload/v1697378131/ieh6ab40liiz1y3t5wbo.png",
        category: "city",
        name: "Ciudad",
    },
    {
        id: 2,
        img: "https://res.cloudinary.com/djslo5b3u/image/upload/v1697378561/electricbikesi_swxvvy.webp",
        category: "electric",
        name: "Electricas",
    },
    {
        id: 3,
        img: "https://res.cloudinary.com/djslo5b3u/image/upload/v1697378118/bjlchzsoybajjimpz7aa.jpg",
        category: "kids",
        name: "Niños",
    },
    {
        id: 4,
        img: "https://res.cloudinary.com/djslo5b3u/image/upload/v1697378112/wi8jmppkyqdewiw7dvu9.jpg",
        category: "mountain",
        name: "Montaña",
    },
    {
        id: 5,
        img: "https://res.cloudinary.com/djslo5b3u/image/upload/v1697379549/roadbike1_ksivkw.jpg",
        category: "road",
        name: "Ruta",
    },
];

export default () => {
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
            breakpoints={{
                640: {
                    slidesPerView: 1,
                    spaceBetween: 20,
                },
                768: {
                    slidesPerView: 2,
                    spaceBetween: 30,
                },
                1024: {
                    slidesPerView: 4,
                    spaceBetween: 30,
                },
            }}
        >
            {data.map((item) => (
                <SwiperSlide key={item.id}>
                    <div className="cursor-pointer rounded-2xl overflow-hidden mx-10 group ">
                        <Link to={item.category}>
                            <img
                                src={item.img}
                                alt="imagende un gato"
                                //  cambiar el tamanio a gusto
                                className="w-full h-40 object-cover object-center transition-transform group-hover:scale-105 ease-in-out duration-200"
                            />
                            <div className=" bg-neutral-800 h-12 flex justify-center  items-center text-white group-hover:bg-primary ease-in-out duration-200">
                                <h3 className="text-lg font-medium">
                                    {item.name}
                                </h3>
                            </div>
                        </Link>
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
            <IoIosArrowDropleft className="absolute prev-slide cursor-pointer text-neutral-800 hover:text-primary top-1/2 z-10 left-2 text-2xl  transform -translate-y-1/2 ease-in-out duration-200 hidden sm:block" />

            <IoIosArrowDropright className="absolute next-slide cursor-pointer text-neutral-800 hover:text-primary z-10 top-1/2 right-2 text-2xl  transform -translate-y-1/2 ease-in-out duration-200 hidden sm:block" />
        </Swiper>
    );
};
