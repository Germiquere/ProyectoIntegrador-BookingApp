import { Link, useLocation } from "react-router-dom";
import { useCategoriesContext } from "../../context/CategoriesContext";
import Section from "../components/Section";
import { useBikesContext } from "../../context/BikesContext";
import { SkeletonGridProducts } from "../components/sectionProducts/SkeletonGridProducts";

export const Categories = () => {
    //TRAIGO EL HOOK PERSONALIZADO PARA TRAER LAS BICIS DESDE EL CONTEXT
    const { bikesData, loading, bikeByIdGet, bikeById } = useBikesContext();
    const { categoriesData, loading: loadingCategories } =
        useCategoriesContext();
    const { pathname } = useLocation();
    // DECODIFICAR LOS CARACTERES RAROS
    const decodedPathname = decodeURIComponent(pathname);
    // QUITAR LA BARRA  DEL COMIENZO DEL PATHNAME
    const currentPath = decodedPathname.substring(1);
    console.log(currentPath);
    // FUNCION PARA FILTRAR PRODUCTOS POR EL CURRENTPATH
    const filterBikes = (arr, bikeCategory) => {
        // TODO: CHECKEAR COMO VIENE EL OBJETO DE LA CATEGORIA Y CAMBIAR EN BASE A ESO EL ITEM.NAME
        if (!arr) {
            return [];
        }

        const res = arr.filter(
            (item) => item.categoria.nombre.toLowerCase() == bikeCategory
        );
        return res;
    };
    const filteredBikes = filterBikes(bikesData, currentPath);

    const filterCategory = (arr, bikeCategory) => {
        // TODO: CHECKEAR COMO VIENE EL OBJETO DE LA CATEGORIA Y CAMBIAR EN BASE A ESO EL ITEM.NAME
        if (!arr) {
            return [];
        }

        const res = arr.find(
            (item) => item.nombre.toLowerCase() == bikeCategory
        );
        return res;
    };
    console.log(filteredBikes);
    const filteredCategory = filterCategory(categoriesData, currentPath);
    return (
        <Section>
            <div className="max-w-[1200px] mx-auto mt-3">
                {loading && loadingCategories ? (
                    <div className="grid grid-cols-1  gap-4  ssm:grid-cols-2  sm:grid-cols-3  md:grid-cols-4  lg:grid-cols-5  ">
                        {[1, 2, 3, 4, 5, 6, 7, 8, 9, 10].map((item, index) => (
                            <SkeletonGridProducts key={index} />
                        ))}
                    </div>
                ) : (
                    <>
                        <h2 className="text-lg sm:text-2xl font-semibold pb-2">
                            Bicicletas
                        </h2>
                        <h2 className="text-lg sm:text-xl  pb-2">
                            {filteredCategory.descripcion}
                        </h2>
                        <div className="grid grid-cols-1  gap-4  ssm:grid-cols-2  sm:grid-cols-3  md:grid-cols-4  lg:grid-cols-5  ">
                            {filteredBikes.map((item) => (
                                <div
                                    className={`border border-gray-100 mt-8 mb-8 rounded-xl transition-transform transform-gpu duration-300 shadow-md hover:-translate-y-1 hover:scale-105 
                `}
                                    key={item.bicicletaId}
                                >
                                    <Link
                                        to={`/description/${item.bicicletaId}`}
                                        className=""
                                    >
                                        <div>
                                            <img
                                                className="rounded-t-xl  w-full h-48 object-contain"
                                                src={item.imagenes[0].url}
                                                alt={item.nombre}
                                            />
                                        </div>

                                        <p className="pl-4 pt-4">
                                            {item.nombre}
                                        </p>
                                        <p className="p-4 font-bold ">
                                            Desde ${item.precioAlquilerPorDia}
                                            /día
                                        </p>
                                    </Link>
                                </div>
                            ))}
                        </div>
                    </>
                    // filteredBikes.map((item) => (
                    //     <div className="" key={item.nombre}>
                    //         {item.nombre}
                    //     </div>
                    // ))
                )}
            </div>
        </Section>
    );
};
