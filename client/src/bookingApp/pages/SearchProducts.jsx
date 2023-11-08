import React, { useContext, useEffect, useState } from "react";
import { SearchBar } from "../components/sectionCalendarAndSearch/SearchBar";
import Section from "../components/Section";
import { useNavigate, useLocation, Link } from "react-router-dom";
import { Calendar } from "../components/sectionCalendarAndSearch/Calendar/Calendar";
import { BsSearch } from "react-icons/bs";
import { CalendarAndSearchContext } from "../../context/CalendarSearchContext";
import queryString from "query-string";
import { useBikesContext } from "../../context/BikesContext";
import { useCategoriesContext } from "../../context/CategoriesContext";
import Fuse from "fuse.js";
export const SearchProducts = () => {
    const { handleOpenCalendarAndSearch, formState, setFormState } = useContext(
        CalendarAndSearchContext
    );
    const { bikesData } = useBikesContext();
    const { categoriesData } = useCategoriesContext();
    const location = useLocation();
    const {
        search,
        startDate = "",
        endDate = "",
    } = queryString.parse(location.search);
    const navigate = useNavigate();
    const [filteredBicycles, setFilteredBicycles] = useState([]);
    const [selectedCategories, setSelectedCategories] = useState([]);
    // FUSE SEARCH
    const [fuse, setFuse] = useState(null);
    const fuseOptions = {
        keys: ["nombre"],
        includeScore: true,
        threshold: 0.3,
    };

    // FUNCION PARA MANEJAR EL SUBMIT
    const handleSubmit = (e) => {
        e.preventDefault();
        if (formState.search.length === 0) return;
        const filteredResults = filterBicycles();
        setFilteredBicycles(filteredResults);
        // setSelectedCategories([]);
        navigate(
            `/items?search=${formState.search}${
                formState.startDate ? `&startDate=${formState.startDate}` : ""
            }${formState.endDate ? `&endDate=${formState.endDate}` : ""}`
        );
    };
    // FUNCION PARA FILTRAR LAS BICICLETAS POR CATEGORIAS Y NOMBRE
    const filterBicycles = () => {
        let filteredBicycles = bikesData;
        if (formState.search && fuse) {
            const searchResults = fuse.search(formState.search);

            const matchedBikeIds = searchResults.map(
                (result) => result.item.bicicletaId
            );
            filteredBicycles = filteredBicycles.filter((bike) =>
                matchedBikeIds.includes(bike.bicicletaId)
            );
        }

        // if (selectedCategories.length > 0) {
        //     filteredBicycles = filteredBicycles.filter((bike) =>
        //         selectedCategories.includes(bike.categoria.nombre.toLowerCase())
        //     );
        // }
        if (selectedCategories.length > 0) {
            filteredBicycles = filteredBicycles.filter((bike) => {
                return bike.categorias.some((categoria) => {
                    return selectedCategories.includes(
                        categoria.nombre.toLowerCase()
                    );
                });
            });
        }
        return filteredBicycles;
    };
    // FUNCION PARA MOSTRAR LAS BICIS SEGUN EL FILTRO
    const handleCategoryChange = (category) => {
        if (selectedCategories.includes(category)) {
            setSelectedCategories(
                selectedCategories.filter((c) => c !== category)
            );
        } else {
            setSelectedCategories([...selectedCategories, category]);
        }
    };
    useEffect(() => {
        setFormState({
            search,
            startDate,
            endDate,
        });
    }, []);
    useEffect(() => {
        const fuseInstance = new Fuse(bikesData, fuseOptions);
        setFuse(fuseInstance);
    }, [bikesData]);
    useEffect(() => {
        const filteredResults = filterBicycles();
        setFilteredBicycles(filteredResults);
    }, [fuse]);

    useEffect(() => {
        const filteredResults = filterBicycles();
        setFilteredBicycles(filteredResults);
    }, [selectedCategories]);
    useEffect(() => {
        const fuseInstance = new Fuse(bikesData, fuseOptions);
        setFuse(fuseInstance);
    }, [bikesData]);
    return (
        <>
            {/* SECTION HERO */}
            <Section
                className={`bg-[url('https://enduro-mtb.com/wp-content/uploads/2016/11/Affrodable-bike-group-test-ENDURO-magazine-7685-122-2000x500.jpg')] bg-cover bg-no-repeat bg-center sm:bg-none`}
            >
                <div className="h-48 flex items-center justify-center max-w-[1200px] mx-auto relative  bg-[url('https://enduro-mtb.com/wp-content/uploads/2016/11/Affrodable-bike-group-test-ENDURO-magazine-7685-122-2000x500.jpg')] bg-cover bg-no-repeat bg-center sm:mt-5 sm:rounded-xl  ">
                    <form
                        className="flex items-center justify-center absolute bottom-[-22px] shadow-2xl shadow-primary  rounded-full"
                        onSubmit={handleSubmit}
                    >
                        <div className="relative w-full min-w-[200px] flex-1">
                            <SearchBar />
                            <div
                                className="absolute top-0 w-full  h-11 bg-transparent cursor-pointer z-30 sm:hidden sm:cursor-not-allowed"
                                // TODO AL HACER CLICK QUE SE ABRA EL RESPONSIVECALENDARANDSEARCH
                                onClick={handleOpenCalendarAndSearch}
                            ></div>
                        </div>
                        <div className="hidden sm:block flex-1">
                            <Calendar />
                        </div>

                        <button
                            className="hidden sm:flex sm:items-center sm:justify-end h-11 middle none center  rounded-r-full bg-white py-3 px-3 font-sans text-xs font-bold uppercase text-white shadow-sm transition-all  active:opacity-[0.85] active:shadow-none disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none"
                            data-ripple-light="true"
                        >
                            <div className="h-8 w-8 rounded-full bg-primary hover:shadow-secondary flex items-center justify-center hover:shadow-xl font-normal transition-all">
                                <BsSearch />
                            </div>
                        </button>
                    </form>
                </div>
            </Section>
            {/* SECTION PRODUCTS AND FILTER */}
            <Section>
                <div className={` max-w-[1200px] mx-auto flex `}>
                    {/* FILTROS */}
                    <div className="flex flex-col gap-2 w-60">
                        <h3 className="text-lg sm:text-2xl font-semibold w-full">
                            Filtros
                        </h3>
                        <div>
                            <h4 className="text-lg font-semibold">
                                Categorías
                            </h4>
                            {categoriesData.map((category) => (
                                <div
                                    key={category.categoriaId}
                                    onClick={() => {
                                        handleCategoryChange(
                                            category.nombre.toLowerCase()
                                        );
                                        setFormState({
                                            ...formState,
                                            search: "",
                                        });
                                    }}
                                    className={`flex gap-2 items-center cursor-pointer
                                `}
                                >
                                    <input
                                        type="checkbox"
                                        checked={selectedCategories.nombre.includes(
                                            category.nombre
                                        )}
                                    />
                                    <p>{category.nombre}</p>
                                </div>
                            ))}
                        </div>
                    </div>
                    {/* CARDS */}
                    <div className="flex flex-col gap-2">
                        <h4 className="text-lg font-semibold">
                            {`${filteredBicycles.length} artículos`}
                        </h4>
                        <div className="grid grid-cols-1  gap-4  ssm:grid-cols-2  sm:grid-cols-3  md:grid-cols-4 ">
                            {filteredBicycles.map((bike) => (
                                <div
                                    className={` mt-8 mb-8 rounded-xl transition-transform transform-gpu duration-300 shadow-md hover:-translate-y-1 hover:scale-105 
`}
                                    key={bike.bicicletaId}
                                >
                                    <Link
                                        to={`/description/${bike.bicicletaId}`}
                                        className=""
                                    >
                                        <div>
                                            <img
                                                className="rounded-t-xl  w-full h-48 object-contain"
                                                src={bike.imagenes[0].url}
                                                alt={bike.nombre}
                                            />
                                        </div>

                                        <p className="pl-4 pt-4">
                                            {bike.nombre}
                                        </p>
                                        <p className="p-4 font-bold ">
                                            Desde ${bike.precioAlquilerPorDia}
                                            /día
                                        </p>
                                    </Link>
                                </div>
                            ))}
                        </div>
                    </div>
                </div>
            </Section>
        </>
    );
};
