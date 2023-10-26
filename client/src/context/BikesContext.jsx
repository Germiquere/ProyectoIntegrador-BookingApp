import { createContext, useContext, useEffect, useState } from "react";
import { deleteBike, getBikeById, getBikes, postBike } from "../api/bikes";
import { useForm } from "../hooks/useForm";
import { postImage } from "../api/images";

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
    imagenes: [],
    // id: "",
};
export const BikesProvider = ({ children }) => {
    const [bikesData, setBikesData] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [bikeById, setBikeById] = useState([]);
    const [openNewProductModal, setOpenNewProductModal] = useState(true);
    const { formState, onInputChange, onResetForm, setFormState } =
        useForm(formData);
    // FUNCION PARA PODER PASAR EL ID A CATEGORIA COMO UN OBJETO
    const onCategoryChange = ({ target }) => {
        const { value } = target;
        const categoriaId = value === "" ? "" : parseFloat(value);
        setFormState({
            ...formState,
            categoria: {
                categoriaId: categoriaId,
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
    const handlePostImages = async (images) => {
        setLoading(true);
        try {
            const imagePromises = [];
            for (const image of images) {
                imagePromises.push(postImage(image));
            }
            const imageUrls = await Promise.all(imagePromises);
            return imageUrls;
        } catch (er) {
            setError(err);
        } finally {
            setLoading(false);
        }
        // CREAR UN ARRAY DE PROMESAS
    };

    const bike = {
        nombre: "SARASITA",
        descripcion: "SARASA",
        precioAlquilerPorDia: 30,
        categoria: {
            categoriaId: 1,
        },
        imagenes: [
            // {
            //     key: "SARASA",
            //     url: "https://res.cloudinary.com/djslo5b3u/image/upload/v1697908608/electrica1_bcv3pj.png",
            // },
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
                handlePostImages,
            }}
        >
            {children}
        </BikesContext.Provider>
    );
};
