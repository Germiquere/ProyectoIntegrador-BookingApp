import { useContext } from "react";
import { useLocation } from "react-router-dom";
import { useCategoriesContext } from "../../context/CategoriesContext";
import { SkeletonCardsSweiper } from "../components/sectionCategoriesAndRecommended/SkeletonCardsSweiper";
import Section from "../components/Section";
import { useBikesContext } from "../../context/BikesContext";

export const Categories = () => {
    //TRAIGO EL HOOK PERSONALIZADO PARA TRAER LAS BICIS DESDE EL CONTEXT
    const { bikesData, loading } = useBikesContext();
    console.log(bikesData);
    const { pathname } = useLocation();
    // QUITAR LA BARRA  DEL COMIENZO DEL PATHNAME
    const currentPath = pathname.substring(1);
    // FUNCION PARA FILTRAR PRODUCTOS POR EL CURRENTPATH
    const filterBikes = (arr, bikeCategory) => {
        // TODO: CHECKEAR COMO VIENE EL OBJETO DE LA CATEGORIA Y CAMBIAR EN BASE A ESO EL ITEM.NAME
        if (!arr) {
            return [];
        }
        const res = arr.filter(
            (item) => item.nombre.toLowerCase() === bikeCategory
        );
        return res;
    };
    const filteredBikes = filterBikes(bikesData, currentPath);
    return (
        <Section>
            <div className="max-w-[1200px] mx-auto mt-3">
                {loading ? (
                    // VER CUANDO TENGA TIEMPO DE HACERLE UN SKELETON
                    <div className="h-32 w-ful">
                        <p></p>
                    </div>
                ) : (
                    filteredBikes.map((item) => (
                        <div className="" key={item.nombre}>
                            {item.nombre}
                        </div>
                    ))
                )}
            </div>
        </Section>
    );
};
