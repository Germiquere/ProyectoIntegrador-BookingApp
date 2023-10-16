// import Swiper core and required modules
import { Navigation } from "swiper/modules";
import { Swiper, SwiperSlide } from "swiper/react";
import {
    BsFillArrowLeftCircleFill,
    BsFillArrowRightCircleFill,
} from "react-icons/bs";
// Import Swiper styles
import "swiper/css";
import "swiper/css/navigation";
import { Link } from "react-router-dom";
import { SkeletonCardsSweiper } from "./SkeletonCardsSweiper";
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
            modules={[Navigation]}
            navigation={{
                nextEl: ".button-next-slide",
                prevEl: ".button-prev-slide",
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
                    <div className="cursor-pointer rounded-2xl overflow-hidden">
                        <Link to={item.category}>
                            <img
                                src={item.img}
                                alt="imagende un gato"
                                //  cambiar el tamanio a gusto
                                className="w-full h-40 object-cover object-center "
                            />
                            <div className=" bg-indigo-400 h-12 flex justify-center  items-center">
                                <h3 className="text-lg font-medium">
                                    {item.name}
                                </h3>
                            </div>
                        </Link>
                    </div>
                    {/* <SkeletonCardsSweiper /> */}
                </SwiperSlide>
            ))}

            <BsFillArrowLeftCircleFill className="absolute button-prev-slide cursor-pointer text-indigo-400 hover:text-indigo-600 top-1/2 z-10 left-2 text-2xl sm:text-3xl transform -translate-y-1/2" />

            <BsFillArrowRightCircleFill className="absolute button-next-slide cursor-pointer text-indigo-400 hover:text-indigo-600 z-10 top-1/2 right-2 text-2xl sm:text-3xl transform -translate-y-1/2" />
        </Swiper>
    );
};
