import React from "react";
import Section from "../Section";
import GridProducts from "./GridProducts";

export const Products = () => {
    return (
        <Section>
            <div className="max-w-[1200px] mx-auto">
                <h2 className="text-lg sm:text-2xl font-semibold pb-2">
                    Productos destacados
                </h2>
                <GridProducts />
            </div>
        </Section>
    );
};
