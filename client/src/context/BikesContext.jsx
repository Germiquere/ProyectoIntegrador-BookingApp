import { createContext, useContext, useEffect, useState } from "react";
import { deleteBike, getBikeById, getBikes, postBike } from "../api/bikes";
import { useForm } from "../hooks/useForm";

const BikesContext = createContext();
// FUNCION PARA LLAMAR AL CONTEXTO EN EL COMPONENTE QUE QUERAMOS
export const useBikesContext = () => {
    return useContext(BikesContext);
};
const formData = {
    nombre: "",
    descripcion: "",
    precioAlquilerPorDia: "",

    categoria: {
        categoriaId: "",
    },
    imagenes: [
        {
            url: "https://res.cloudinary.com/djslo5b3u/image/upload/v1697908608/electrica1_bcv3pj.png",
        },
        {
            url: "https://res.cloudinary.com/djslo5b3u/image/upload/v1697908609/electrica2_pdvxvw.png",
        },
        {
            url: "https://res.cloudinary.com/djslo5b3u/image/upload/v1697908609/electrica4_j8kbe8.png",
        },
        {
            url: "https://res.cloudinary.com/djslo5b3u/image/upload/v1697908608/electrica3_leu8lc.png",
        },
        {
            url: "https://a0.muscache.com/im/pictures/c3f1fd94-ab7e-409a-bfea-a20f3931d8a8.jpg?im_w=480",
        },
        {
            url: "https://res.cloudinary.com/djslo5b3u/image/upload/v1697908608/electrica3_leu8lc.png",
        },
    ],
    id: "",
};
export const BikesProvider = ({ children }) => {
    const [bikesData, setBikesData] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [bikeById, setBikeById] = useState([]);
    const [openNewProductModal, setOpenNewProductModal] = useState(false);
    const { formState, onInputChange, onResetForm, setFormState } =
        useForm(formData);
    // FUNCION PARA PODER PASAR EL ID A CATEGORIA COMO UN OBJETO
    const onCategoryChange = ({ target }) => {
        const { value } = target;
        setFormState({
            ...formState,
            categoria: {
                categoriaId: parseFloat(value),
            },
        });
    };
    const fetchData = async () => {
        // MANEJO EL ESTADO  DEL LOADING EN TRUE
        setLoading(true);
        try {
            // LLAMO A LA FUNCION GET DEL ARCHIVO categories.js
            const data = await getBikes();
            // TENER EN CUENTA COMO VIENE MI DATA
            setBikesData(data);
        } catch (err) {
            setError(err);
        } finally {
            // MANEJO EL ESTADO DEL LOADING EN FALSE UNA  VEZ TERMINADO EL FETCH YA SEA EXITOSO O NO
            setLoading(false);
        }
    };
    const bikeByIdGet = async (id) => {
        // MANEJO EL ESTADO  DEL LOADING EN TRUE
        setLoading(true);
        try {
            // LLAMO A LA FUNCION GET DEL ARCHIVO categories.js
            const data = await getBikeById(id);
            // TENER EN CUENTA COMO VIENE MI DATA
            setBikeById(data);
        } catch (err) {
            setError(err);
        } finally {
            // MANEJO EL ESTADO DEL LOADING EN FALSE UNA  VEZ TERMINADO EL FETCH YA SEA EXITOSO O NO
            setLoading(false);
        }
    };
    const addNewBike = async (bike) => {
        setLoading(true);
        try {
            const newBike = await postBike(bike);
            //   VUELVO A HACER EL FETCH DE LA DATA PARA ACTUALIZAR LAS CATEGORIAS
            fetchData();
        } catch (err) {
            setError(err);
        } finally {
            setLoading(false);
        }
    };
    // FUNCION PARA BORRAR UNA CATEGORIA
    const deleteABike = async (id) => {
        setLoading(true);
        try {
            const deletedBike = await deleteBike(id);
            fetchData();
        } catch (err) {
            setError(err);
        } finally {
            setLoading(false);
        }
    };
    const bike = {
        nombre: "SARASITA",
        descripcion: "SARASA",
        precioAlquilerPorDia: 30,
        categoria: {
            categoriaId: 1,
        },
        imagenes: [
            {
                nombre: "SARASA",
                url: "https://res.cloudinary.com/djslo5b3u/image/upload/v1697908608/electrica1_bcv3pj.png",
            },
        ],
    };

    useEffect(() => {
        fetchData();
        // addNewBike(bike);
    }, []);

    return (
        <BikesContext.Provider
            value={{
                //PROPIEDADES
                bikesData,
                loading,
                error,
                bikeById,
                formState,
                openNewProductModal,
                //METODOS
                bikeByIdGet,
                addNewBike,
                deleteABike,
                onCategoryChange,
                onInputChange,
                onResetForm,
                setOpenNewProductModal,
                setFormState,
            }}
        >
            {children}
        </BikesContext.Provider>
    );
};
