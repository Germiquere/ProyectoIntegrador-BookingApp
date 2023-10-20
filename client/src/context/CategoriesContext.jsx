import { createContext, useContext, useEffect, useState } from "react";
import { deleteCategory, getCategories, postCategory } from "../api/categories";
// CREO UN CONTEXT
const CategoriesContext = createContext();
// LO EXPORTO DESDE ACA YA USANDO EL USECONTEXT PARA NO TENER QUE IMPORTAR AMBAS COSAS EN OTRO COMPONENTE
export const useCategoriesContext = () => {
    return useContext(CategoriesContext);
};
// CREO EL PROVIDER
export const CategoriesProvider = ({ children }) => {
    // CREO LAS PROPIEDAS Y METODOS QUE VOY A EXPORTAR Y HACER GLOBALES CON MI CONTEXTO
    const [categoriesData, setCategoriesData] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    // FUNCION PARA HACER  EL GET
    const fetchData = async () => {
        // MANEJO EL ESTADO  DEL LOADING EN TRUE
        setLoading(true);
        try {
            // LLAMO A LA FUNCION GET DEL ARCHIVO categories.js
            const data = await getCategories();
            console.log(data);
            // TENER EN CUENTA COMO VIENE MI DATA
            setCategoriesData(data);
        } catch (err) {
            setError(err);
        } finally {
            // MANEJO EL ESTADO DEL LOADING EN FALSE UNA  VEZ TERMINADO EL FETCH YA SEA EXITOSO O NO
            setLoading(false);
        }
    };
    // -----
    // COMPONENTE PARA HACER EL TEST DEL LOADING
    // -----
    // const fetchData = async () => {
    //     // MANEJO EL ESTADO DEL LOADING EN TRUE
    //     setLoading(true);

    //     // Simulamos una carga falsa con setTimeout
    //     setTimeout(async () => {
    //         try {
    //             // LLAMO A LA FUNCION GET DEL ARCHIVO categories.js
    //             const data = await getCategories();
    //             // TENER EN CUENTA COMO VIENE MI DATA
    //             setCategoriesData(data.results);
    //         } catch (err) {
    //             setError(err);
    //         } finally {
    //             // MANEJO EL ESTADO DEL LOADING EN FALSE UNA VEZ TERMINADO EL FETCH YA SEA EXITOSO O NO
    //             setLoading(false);
    //         }
    //     }, 5000); // Simulamos una carga de 2 segundos (puedes ajustar el tiempo según tus necesidades)
    // };

    // FUNCION PARA AGREGAR UNA CATEGORIA
    const addNewCategory = async (newBikeCategory) => {
        setLoading(true);
        try {
            const newCategory = await postCategory(newBikeCategory);
            //   VUELVO A HACER EL FETCH DE LA DATA PARA ACTUALIZAR LAS CATEGORIAS
            fetchData();
        } catch (err) {
            setError(err);
        } finally {
            setLoading(false);
        }
    };
    // FUNCION PARA BORRAR UNA CATEGORIA
    const deleteACategory = async (id) => {
        setLoading(true);
        try {
            const deletedCategory = await deleteCategory(id);
            fetchData();
        } catch (err) {
            setError(err);
        } finally {
            setLoading(false);
        }
    };
    useEffect(() => {
        // MANDO A LLAMAR LA FUNCCION FETCHDATA AL MOMENTO EN QUE SE MONTA EL COMPONENTE
        fetchData();
    }, []);

    return (
        <CategoriesContext.Provider
            value={{
                //PROPIEDADES
                categoriesData,
                loading,
                error,
                //METODOS
                addNewCategory,
                deleteACategory,
            }}
        >
            {children}
        </CategoriesContext.Provider>
    );
};
