import React from "react";
import { Link, useParams, useNavigate } from "react-router-dom";
import Section from "../components/Section";
import { IoIosArrowDropleft, IoIosArrowDropright } from "react-icons/io";
import { ImgGallery } from "../components/sectionDescription/ImgGallery";
import { Calendar } from "../components/sectionCalendarAndSearch/Calendar/Calendar";
export const Description = () => {
    // hacer el fetch con ese id
    const { id } = useParams();
    const navigate = useNavigate();
    const goBack = () => {
        navigate(-1);
    };
    return (
        <Section>
            <div className="flex gap-3 flex-col relative md:flex-row md:justify-center">
                <div className="">
                    <h2>Nombre del producto</h2>
                    <p>
                        Lorem, ipsum dolor sit amet consectetur adipisicing
                        elit. Quod dicta enim reprehenderit sint laboriosam ut,
                        inventore est dolorem, voluptates, sit autem quasi
                        impedit! Ullam, nesciunt voluptate modi cupiditate
                        corporis voluptatum!
                    </p>
                    <ImgGallery />
                </div>
                {/* seccion del costado izquierdo */}
                <div className=" flex flex-col border-[1px] border-gray-200 rounded-xl md:w-96 p-3 gap-3">
                    <div
                        className="flex gap-3 items-center justify-end
                        cursor-pointer
                        absolute top-0  right-0
                        md:relative
                        md:top-auto
                        md:right-auto
                    "
                        onClick={goBack}
                    >
                        <h3>Volver</h3>
                        <IoIosArrowDropleft />
                    </div>
                    <div>
                        <h3>Precio por dia</h3>
                        <Calendar />
                    </div>
                </div>
            </div>
        </Section>
    );
};
