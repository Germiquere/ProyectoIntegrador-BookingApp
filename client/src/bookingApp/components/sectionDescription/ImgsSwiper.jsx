// import Swiper core and required modules
import { Navigation, Pagination } from "swiper/modules";
import { Swiper, SwiperSlide } from "swiper/react";

import { IoIosArrowDropleft, IoIosArrowDropright } from "react-icons/io";
// Import Swiper styles
import "swiper/css";
import "swiper/css/navigation";
import "swiper/css/pagination";
import { SkeletonCardsSweiper } from "../sectionCategoriesAndRecommended/SkeletonCardsSweiper";

import { useBikesContext } from "../../../context/BikesContext";

export default ({ data }) => {
    const { loading: loadingBikes } = useBikesContext();
    return (
        <Swiper
            className="relative"
            modules={[Navigation, Pagination]}
            slidesPerView={1}
            // esto es para poder ponerle una clase a cualquier elemnto y que tenga la funcionalidad de siguiente y previo
            // es para que sea infinito
            // loop
            // es para que aparezcan las pelotitas de la paginacion
            pagination={{
                // para que pueda  clickear en las pelotitas
                clickable: true,
            }}
        >
            {loadingBikes
                ? [1, 2, 3, 4, 5].map((item) => (
                      <SwiperSlide key={item}>
                          <SkeletonCardsSweiper />
                      </SwiperSlide>
                  ))
                : data.imagenes.map((item) => (
                      <SwiperSlide key={item.id}>
                          <div className="h-full w-full">
                              <img
                                  className="max-h-[400px]  w-full object-contain "
                                  src={item.url}
                                  alt=""
                              />
                          </div>
                      </SwiperSlide>
                  ))}

            <></>
        </Swiper>
    );
};
