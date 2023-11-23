import { useEffect, useRef, useState } from "react";
import { Tooltip } from "@mui/material";
import {
    BsXLg,
    BsCurrencyDollar,
    BsCloudUpload,
    BsPlusLg,
} from "react-icons/bs";
import { PiArchiveBoxLight } from "react-icons/pi";
import { TableCategories } from "./TableCategories";
import { useForm } from "../../../hooks/useForm";
import { useCategoriesContext } from "../../../context/CategoriesContext";

const formData = {
    nombre: "",
    descripcion: "",
    imagen: "",
};
export const ManageCategories = () => {
    const {
        error,
        setError,
        handlePostImages,
        addNewCategory,
        handleDeleteImages,
        loading,
    } = useCategoriesContext();
    const [imageChange, setImageChange] = useState([]);
    const [hasErrorImg, setHasErrorImg] = useState(false);
    const fileInputRef = useRef(null);
    const [erros, setErros] = useState({
        nombre: false,
        categoria: false,
        descripcion: false,
        imagen: false,
    });
    const {
        formState,
        onInputChange,
        onResetForm,
        setFormState,
        imagen,
        nombre,
        descripcion,
    } = useForm(formData);
    // FUNCION PARA MANEJAR EL CAMBIO DE INPUT Y SUS ERRORES
    const handleInputChange = (e, toNumber = false) => {
        const { name, value } = e.target;
        onInputChange(e, toNumber);
        setErros({
            ...erros,
            [name]: value.trim() === "",
        });
        setError("");
    };
    // FUNCION PARA MOSTRAR LA IMAGEN EN EL CLIENTE
    const onFileInputChange = ({ target }) => {
        const selectedFiles = target.files;

        // SOLO AGREGO LAS IMAGENES
        setImageChange((prevImageChange) => [
            ...prevImageChange,
            ...selectedFiles,
        ]);
    };
    // FUNCION PARA VALIDACIONES AL PRECIONAR GUARDAR
    const handleValidations = () => {
        let hasError = false;

        if (nombre.trim() === "") {
            setErros((prevErrors) => ({
                ...prevErrors,
                nombre: true,
            }));
            hasError = true;
        }
        if (descripcion.trim() === "") {
            setErros((prevErrors) => ({
                ...prevErrors,
                descripcion: true,
            }));
            hasError = true;
        }

        if (imageChange.length === 0) {
            setErros((prevErrors) => ({
                ...prevErrors,
                imagen: true,
            }));
            setHasErrorImg(true);
            hasError = true;
        }

        return hasError;
    };
    const handleSave = async () => {
        if (!handleValidations()) {
            // Cargar las imágenes y esperar a que se completen

            const imageUrl = await handlePostImages(imageChange);
            // MANEJAR EL ERROR PARA QUE NO SE ROMPA LA APLICACION
            if (imageUrl) {
                const data = {
                    ...formState,
                    imagen: imageUrl,
                };
                const category = await addNewCategory(data);
                if (category && category.statusCode !== 409) {
                    setImageChange([]);

                    onResetForm();
                    setError(false);
                } else {
                    handleDeleteImages(imageUrl);
                    console.log("se borraron las imagenes");
                }
            }
        }
    };
    useEffect(() => {
        if (imageChange.length > 0) {
            setErros((prevErrors) => ({
                ...prevErrors,
                imagen: false,
            }));
            handleValidations();
            setHasErrorImg(false);
        }
    }, [imageChange]);
    return (
        <div className="flex flex-col gap-5">
            <div className="w-full">
                <h2 className="text-lg font-semibold pb-3 border-b-[1px] border-gray-300">
                    Detalles de la categoría
                </h2>
            </div>
            <div className="flex gap-5 ">
                <div className="flex flex-col gap-2 flex-1">
                    <div className="w-full">
                        <label className="text-base font-semibold mb-2">
                            Categoría *
                        </label>
                        <div
                            className={`relative h-11 w-full min-w-[200px] shadow-md rounded-xl border-[1px] border-gray-100 overflow-hidden `}
                        >
                            <input
                                className={` peer h-full w-full  p-2 font-sans text-sm font-normal text-blue-gray-700 outline outline-0 transition-all  focus:outline-0  disabled:bg-blue-gray-50`}
                                placeholder="Montaña"
                                type="text"
                                value={nombre}
                                name="nombre"
                                onChange={handleInputChange}
                            />
                        </div>
                        <p
                            className={`pt-1 text-xs text-red-500 ${
                                erros.nombre ? "block" : "hidden"
                            }`}
                        >
                            Campo obligatorio
                        </p>
                        <p
                            className={`pt-1 text-xs text-red-500 ${
                                error?.status === 409 ? "block" : "hidden"
                            }`}
                        >
                            Ya existe una categoria con ese nombre
                        </p>
                    </div>
                    <div>
                        <label className="text-base font-semibold mb-2">
                            Descripcion *
                        </label>
                        <textarea
                            className=" p-2 w-full outline outline-0 shadow-md border-[1px] rounded-xl overflow-hidden border-gray-100 "
                            style={{ resize: "none" }}
                            placeholder="Descripcion del producto"
                            rows={4}
                            value={descripcion}
                            name="descripcion"
                            onChange={handleInputChange}
                        ></textarea>
                        <p
                            className={`pt-1 text-xs text-red-500 ${
                                erros.descripcion ? "block" : "hidden"
                            }`}
                        >
                            Campo obligatorio
                        </p>
                    </div>
                </div>
                {/* div de la derecha */}

                <div className="flex flex-1 gap-20 items-center">
                    <div>
                        <p className="text-base font-semibold ">
                            Cargar imágen *
                        </p>
                        <div className="grid grid-cols-2 gap-2">
                            <input
                                type="file"
                                multiple
                                accept="image/*"
                                className="hidden"
                                onChange={onFileInputChange}
                                ref={fileInputRef}
                                disabled={loading}
                            />
                            <Tooltip title="Cargar imágen">
                                <div className=" w-16 h-16 flex items-center justify-center shadow-md rounded-xl  border-[1px] border-gray-100">
                                    <BsCloudUpload
                                        className="text-xl text-gray-400 cursor-pointer"
                                        onClick={() =>
                                            fileInputRef.current.click()
                                        }
                                    />
                                </div>
                            </Tooltip>
                            {imageChange.length > 0 && (
                                <div className="relative group  w-16 h-16  shadow-md border-[1px] border-gray-100 rounded-xl overflow-hidden text-white">
                                    <img
                                        className="w-full  h-full object-cover pointer-events-none"
                                        src={URL.createObjectURL(
                                            imageChange[0]
                                        )}
                                        alt=""
                                    />
                                    <div className="w-full h-full absolute bg-grayTertiary top-0 opacity-0 group-hover:opacity-50 "></div>

                                    <BsXLg
                                        className=" bg-primary  opacity-40 group-hover:opacity-100  p-1 rounded-full text-2xl   absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 z-20  cursor-pointer"
                                        onClick={() => {
                                            setImageChange("");
                                        }}
                                    />
                                </div>
                            )}
                        </div>
                        <p
                            className={`pt-1 text-xs text-red-500 ${
                                erros.imagen ? "block" : "hidden"
                            }`}
                        >
                            Campo obligatorio
                        </p>
                    </div>

                    <Tooltip title="Guardar Categoría">
                        <button
                            className="flex text-white items-center justify-center middle none center rounded-full  h-10 w-10 font-sans text-xs font-bold uppercase  bg-primary transition-all  active:bg-tertiary disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none "
                            data-ripple-dark="true"
                            disabled={loading}
                            onClick={() => {
                                handleSave();
                            }}
                        >
                            <PiArchiveBoxLight className="text-2xl" />
                        </button>
                    </Tooltip>
                </div>
            </div>
            {/* CATEGORIAS */}

            <TableCategories />
        </div>
    );
};
