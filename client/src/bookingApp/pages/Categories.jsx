import { Link, useLocation } from "react-router-dom";
import { useCategoriesContext } from "../../context/CategoriesContext";
import Section from "../components/Section";
import { useBikesContext } from "../../context/BikesContext";
import { SkeletonGridProducts } from "../components/sectionProducts/SkeletonGridProducts";
import { Helmet } from "react-helmet";
import { useUsersContext } from "../../context/UsersContext";
import { useFavoritesContext } from "../../context/FavoritesContext";
import { useEffect, useState } from "react";
import { FaRegHeart, FaHeart } from "react-icons/fa6";
export const Categories = () => {
    //TRAIGO EL HOOK PERSONALIZADO PARA TRAER LAS BICIS DESDE EL CONTEXT
    const { bikesData, loading, bikeByIdGet, bikeById } = useBikesContext();
    const { categoriesData, loading: loadingCategories } =
        useCategoriesContext();
    const { userData, isAuthenticated, rol } = useUsersContext();
    const { favorites, handleFav } = useFavoritesContext();
    const [loadingFav, setLoadingFav] = useState(false);
    const [favorite, setFavorite] = useState([]);
    const { pathname } = useLocation();
    // DECODIFICAR LOS CARACTERES RAROS
    const decodedPathname = decodeURIComponent(pathname);
    // QUITAR LA BARRA  DEL COMIENZO DEL PATHNAME
    const currentPath = decodedPathname.substring(1);
    // FUNCION PARA FILTRAR PRODUCTOS POR EL CURRENTPATH
    const filterBikes = (arr, bikeCategory) => {
        // TODO: CHECKEAR COMO VIENE EL OBJETO DE LA CATEGORIA Y CAMBIAR EN BASE A ESO EL ITEM.NAME
        if (!arr) {
            return [];
        }

        const res = arr.filter((item) =>
            item.categorias.some(
                (cat) => cat.nombre.toLowerCase() === bikeCategory.toLowerCase()
            )
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

    const filteredCategory = filterCategory(categoriesData, currentPath);
    // FUNCION PARA PINTAR O DESPINTAR CORAZON
    const handleToggleFavorite = (id) => {
        setFavorite((prevState) => {
            const newStatus = { ...prevState };
            newStatus[id] = !newStatus[id];
            console.log(newStatus);
            return newStatus;
        });
    };
    //FUNCION PARA QUE SE PINTE EL CORAZON FAVORITOS
    useEffect(() => {
        if (!loadingFav && favorites.length > 0) {
            setFavorite(() => {
                const initialStatus = favorites.reduce((status, fav) => {
                    status[fav.bicicleta.bicicletaId] = true;
                    return status;
                }, {});
                return initialStatus;
            });
            setLoadingFav(true);
        }
    }, [favorites]);

    return (
        <Section>
            <Helmet>
                <title>Categorias Bike Me Now</title>
                {/* <link rel="canonical" href="http://mysite.com/example" /> */}

                <meta
                    property="og:image"
                    content="https://res.cloudinary.com/djslo5b3u/image/upload/v1698971497/BikeMeNow_BlueAlpha_svidg9.png"
                />
                <meta property="og:title" content="Categorias Bike Me Now" />
                <meta
                    property="og:description"
                    content="Descubre la libertad sobre dos ruedas con nuestro servicio de alquiler de bicicletas. Explora tu ciudad o destinos increíbles mientras reservas la bicicleta perfecta para cada aventura. ¡Siente el viento en tu rostro y pedalea hacia tus próximas experiencias inolvidables!"
                />
            </Helmet>
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
                                    {/*  Boton de favoritos */}
                                    <div className="absolute text-primary right-2 top-2 text-[25px]">
                                        {isAuthenticated && rol === "user" && (
                                            <button
                                                onClick={() => {
                                                    handleFav(
                                                        item.bicicletaId,
                                                        userData
                                                    );
                                                    handleToggleFavorite(
                                                        item.bicicletaId
                                                    );
                                                }}
                                            >
                                                {favorite[item.bicicletaId] ? (
                                                    <FaHeart />
                                                ) : (
                                                    <FaRegHeart />
                                                )}
                                            </button>
                                        )}
                                    </div>
                                    <Link
                                        to={`/description/${item.bicicletaId}`}
                                        className=""
                                    >
                                        <div className="pb-2">
                                            <img
                                                className="rounded-t-xl  w-full h-48 object-contain"
                                                src={item.imagenes[0].url}
                                                alt={item.nombre}
                                            />
                                        </div>
                                        <p className="px-4 font-bold ">
                                            Desde ${item.precioAlquilerPorDia}
                                            /día
                                        </p>
                                        <p className="px-4 pb-4">
                                            {item.nombre.length > 50
                                                ? item.nombre.slice(0, 50) +
                                                  "..."
                                                : item.nombre}
                                        </p>
                                    </Link>
                                </div>
                            ))}
                        </div>
                    </>
                )}
            </div>
        </Section>
    );
};
