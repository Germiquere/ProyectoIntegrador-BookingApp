import { createContext, useContext, useEffect, useState } from "react";
import {
    deleteBike,
    getBikeById,
    getBikes,
    getBikesByPagination,
    // getBikesByPagination,
    postBike,
    updateBike,
} from "../api/bikes";
import { useForm } from "../hooks/useForm";
import { deleteImage, postImage } from "../api/images";
import { useNavigate } from "react-router-dom";

const BikesContext = createContext();
// FUNCION PARA LLAMAR AL CONTEXTO EN EL COMPONENTE QUE QUERAMOS
export const useBikesContext = () => {
    return useContext(BikesContext);
};
const formData = {
    nombre: "",
    descripcion: "",
    precioAlquilerPorDia: "",
    categorias: [],
    imagenes: [],
    caracteristicas: [],
    politicas: [],
    // id: "",
};
export const BikesProvider = ({ children }) => {
    const [bikesData, setBikesData] = useState([]);
    const [bikesDataPaginated, setBikesDataPaginated] = useState([]);

    const [loading, setLoading] = useState(false);
    const [loadingPagination, setLoadingPagination] = useState(true);

    const [error, setError] = useState("");
    const [bikeById, setBikeById] = useState([]);
    const [openNewProductModal, setOpenNewProductModal] = useState(false);
    const [openEditProductModal, setOpenEditProductModal] = useState(false);
    const [openConfirmDelete, setOpenConfirmDelete] = useState(false);
    const { formState, onInputChange, onResetForm, setFormState } =
        useForm(formData);
    const navigate = useNavigate();
    // FUNCION PARA PODER PASAR EL ID A CATEGORIA COMO UN OBJETO
    const onCategoryChange = ({ target }) => {
        const { value } = target;
        const categoriaId = value === "" ? "" : parseFloat(value);
        setFormState({
            ...formState,
            categorias: [...formState.categorias, { categoriaId: categoriaId }],
        });
    };
    const onCaractChange = ({ target }) => {
        const { value } = target;
        const caracteristicaId = value === "" ? "" : parseFloat(value);
        setFormState({
            ...formState,
            caracteristicas: [
                ...formState.caracteristicas,
                { caracteristicaId: caracteristicaId },
            ],
        });
    };
    const onPolicyChange = ({ target }) => {
        const { value } = target;
        const politicaId = value === "" ? "" : parseFloat(value);
        setFormState({
            ...formState,
            politicas: [...formState.politicas, { politicaId: politicaId }],
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
    const fetchPaginatedData = async (page, query) => {
        setLoadingPagination(true);
        try {
            // LLAMO A LA FUNCION GET DEL ARCHIVO categories.js
            const data = await getBikesByPagination(page, query);
            // TENER EN CUENTA COMO VIENE MI DATA
            setBikesDataPaginated(data);
        } catch (err) {
            setError(err);
        } finally {
            // MANEJO EL ESTADO DEL LOADING EN FALSE UNA  VEZ TERMINADO EL FETCH YA SEA EXITOSO O NO
            setLoadingPagination(false);
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
            return newBike;
        } catch (err) {
            console.log(err.message);
            setError(err);
        } finally {
            setLoading(false);
        }
    };
    // FUNCION PARA BORRAR UNA BICICLETA
    const deleteABike = async (id) => {
        setLoading(true);
        try {
            const deletedBike = await deleteBike(id);
            fetchData();
            return deletedBike;
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
        } catch (err) {
            setError(err);
        } finally {
            setLoading(false);
        }
    };
    // FUNCION PARA BORRAR LAS IMAGENES
    const handleDeleteImages = async (images) => {
        setLoading(true);
        try {
            const imagePromises = [];
            for (const image of images) {
                imagePromises.push(deleteImage(image.key));
            }

            const deletedImages = await Promise.all(imagePromises);
            return deletedImages;
        } catch (err) {
            // TODO: CAMBIAR EL STATUS CODE CUANDO SE SOLUCIONE EL PROBLEMA EN EL BACK. DEBERIA SER UN 403 O UN 401
            if (err.status === 500) {
                navigate("/auth/login", { replace: true });
            }
        } finally {
            setLoading(false);
        }
    };
    //  FUNCION PARA ACTUALIZAR UNA BICICLETEA
    const updateABike = async (bike) => {
        setLoading(true);
        try {
            const updatedBike = await updateBike(bike);
            //   VUELVO A HACER EL FETCH DE LA DATA PARA ACTUALIZAR LAS CATEGORIAS
            fetchData();
            return updatedBike;
        } catch (err) {
            setError(err);
        } finally {
            setLoading(false);
        }
    };
    // const sarasa = [
    //     {
    //         key: "bikemenowImages/1698347489267_no-image-icon-23485.png",
    //     },
    //     {
    //         key: "bikemenowImages/1698348697742_no-image-icon-23485.png",
    //     },
    //     {
    //         key: "bikemenowImages/1698348704238_no-image-icon-23485.png",
    //     },
    // ];

    useEffect(() => {
        fetchData();
        fetchPaginatedData(0, "santos");
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
                openEditProductModal,
                openConfirmDelete,
                bikesDataPaginated,
                loadingPagination,
                //METODOS
                bikeByIdGet,
                addNewBike,
                deleteABike,
                onCategoryChange,
                onCaractChange,
                onPolicyChange,
                onInputChange,
                onResetForm,
                setOpenNewProductModal,
                setOpenEditProductModal,
                setOpenConfirmDelete,
                setFormState,
                handlePostImages,
                setError,
                updateABike,
                handleDeleteImages,
                fetchPaginatedData,
                setBikeById,
            }}
        >
            {children}
        </BikesContext.Provider>
    );
};
