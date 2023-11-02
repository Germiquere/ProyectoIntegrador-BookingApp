import React from "react";
import Section from "../Section";
import CardsSwiper from "./CardsSwiper";
import { SkeletonCardsSweiper } from "./SkeletonCardsSweiper";

export const CategoriesAndRecommended = () => {
    return (
        <Section>
            <div className="max-w-[1200px] mx-auto">
                <h2 className="text-lg sm:text-2xl font-semibold pb-2">
                    Busca por tipo de bicicleta
                </h2>
                <CardsSwiper />
            </div>
        </Section>
    );
};
