import { useRef, useState } from "react";
import { BsXLg, BsCurrencyDollar, BsCloudUpload, BsX } from "react-icons/bs";
import { useEffect } from "react";
import { useBikesContext } from "../../context/BikesContext";
import { Loader } from "../../ui/Loader";
import { Tooltip } from "@mui/material";
import { useCategoriesContext } from "../../context/CategoriesContext";

import Select from "react-select";
import { useCharacteristicsContext } from "../../context/CharacteristicsContext";
export const CreateProductModal = () => {
    const {
        error,
        formState,
        onInputChange,
        onResetForm,
        onCategoryChange,
        addNewBike,
        loading: loadingBikes,
        setOpenNewProductModal,
        handlePostImages,
        handleDeleteImages,
        setError,
        setFormState,
        onCaractChange,
    } = useBikesContext();
    const { categoriesData } = useCategoriesContext();
    const { characteristicsData } = useCharacteristicsContext();
    const {
        nombre,
        descripcion,
        precioAlquilerPorDia,
        categorias,
        imagenes,
        caracteristicas,
    } = formState;
    const [imageChange, setImageChange] = useState([]);
    // const [selectedOption, setSelectedOption] = useState("");
    const fileInputRef = useRef(null);
    const selectRef = useRef(null);
    const selectCatRef = useRef(null);

    const [erros, setErros] = useState({
        nombre: false,
        categoria: false,
        precioAlquilerPorDia: false,
        imagenes: false,
        caracteristicas: false,
        descripcion: false,
    });
    const [hasErrorImg, setHasErrorImg] = useState(false);
    const [hasErrorCat, setHasErrorCat] = useState(false);
    const [hasErrorCaract, setHasErrorCaract] = useState(false);
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
    // FUNCION PARA MANEJAR EL CAMBIO DE CATEGORIA Y SUS ERRORES
    const handleCategoryChange = (e) => {
        const { name, value } = e.target;
        // COMPROBAMOS QUE NO ESTE LA CATEGORIA AGREGADA,
        const hasMatch = categorias.some(
            (categoria) => categoria.categoriaId == value
        );
        //     // setErros({
        //     //     ...erros,
        //     //     categoria: value.trim() === "",
        //     // });
        console.log(hasMatch);
        if (!hasMatch && value.trim()) {
            onCategoryChange(e);
        }
        setError("");
    };
    const handleCaracteristicsChange = (e) => {
        const { name, value } = e.target;
        // COMPROBAMOS QUE NO ESTE LA CATEGORIA AGREGADA,
        const hasMatch = caracteristicas.some(
            (caract) => caract.caracteristicaId == value
        );
        //     // setErros({
        //     //     ...erros,
        //     //     categoria: value.trim() === "",
        //     // });
        console.log(hasMatch);
        if (!hasMatch && value.trim()) {
            onCaractChange(e);
        }
    };
    const handleCategoryDeletefromFormstate = (id) => {
        const updatedCategorias = categorias.filter(
            (category) => category.categoriaId !== id
        );
        setFormState({
            ...formState,
            categorias: updatedCategorias,
        });
        selectCatRef.current.value = "";
    };
    const handleCharacteristicDeletefromFormstate = (id) => {
        const updatedCaracts = caracteristicas.filter(
            (caract) => caract.caracteristicaId !== id
        );
        setFormState({
            ...formState,
            caracteristicas: updatedCaracts,
        });
        selectRef.current.value = "";
    };
    // FUNCION PARA VALIDACIONES
    const handleValidations = () => {
        let hasError = false;
        if (categorias.length === 0) {
            setErros((prevErrors) => ({
                ...prevErrors,
                categoria: true,
            }));
            setHasErrorCat(true);
            hasError = true;
        }
        if (caracteristicas.length === 0) {
            setErros((prevErrors) => ({
                ...prevErrors,
                caracteristicas: true,
            }));
            setHasErrorCaract(true);
            hasError = true;
        }
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

        if (
            typeof precioAlquilerPorDia === "string" &&
            precioAlquilerPorDia.trim() === ""
        ) {
            setErros((prevErrors) => ({
                ...prevErrors,
                precioAlquilerPorDia: true,
            }));
            hasError = true;
        }
        if (imageChange.length < 2) {
            setErros((prevErrors) => ({
                ...prevErrors,
                imagenes: true,
            }));
            setHasErrorImg(true);
            hasError = true;
        }

        return hasError;
    };
    // FUNCION PARA CARGAR LAS IMAGENES A LA FUNCION DEL POST

    // FUNCION PARA GUARDAR Y CREAR UN NUEVO PRODUCTO

    const handleSaveAndNew = async () => {
        if (!handleValidations()) {
            // Cargar las imágenes y esperar a que se completen

            const imageUrls = await handlePostImages(imageChange);

            // MANEJAR EL ERROR PARA QUE NO SE ROMPA LA APLICACION
            if (imageUrls) {
                const data = {
                    ...formState,
                    imagenes: imageUrls,
                };
                const bike = await addNewBike(data);

                if (bike && bike.statusCode !== 409) {
                    setImageChange([]);
                    onResetForm();
                    setError(false);
                } else {
                    handleDeleteImages(imageUrls);
                }
            }
        }
    };

    const handleSave = async () => {
        if (!handleValidations()) {
            // Cargar las imágenes y esperar a que se completen

            const imageUrls = await handlePostImages(imageChange);
            console.log(imageUrls);
            // MANEJAR EL ERROR PARA QUE NO SE ROMPA LA APLICACION
            if (imageUrls) {
                console.log("entre aca");
                const data = {
                    ...formState,
                    imagenes: imageUrls,
                };
                const bike = await addNewBike(data);
                console.log(bike);

                if (bike && bike.statusCode !== 409) {
                    setImageChange([]);
                    onResetForm();
                    setError(false);
                    setOpenNewProductModal(false);
                } else {
                    handleDeleteImages(imageUrls);
                }
            }
        }
    };
    // FUNCION PARA COMPROBAR SI YA EXISTE UNA IMAGEN CON EL MISMO LASTMODIFIED EN EL ARRAY
    const isImageInArray = (imageArray, image) => {
        return imageArray.some(
            (existingImage) => existingImage.lastModified === image.lastModified
        );
    };
    // FUNCION PARA SOLAMENTE AGREGAR IMAGENES,Y QUE LAS MISMAS NO SE REPITAN EN EL CLIENTE
    const onFileInputChange = ({ target }) => {
        const selectedFiles = target.files;
        const validImages = [];

        for (let i = 0; i < selectedFiles.length; i++) {
            const file = selectedFiles[i];

            if (
                file.type.startsWith("image/") &&
                !isImageInArray(imageChange, file)
            ) {
                //  SOLO AGREGA LOS FILES QUE SEAN DEL TIPO IMAGEN
                validImages.push(file);
            }
        }

        // SOLO AGREGO LAS IMAGENES
        setImageChange((prevImageChange) => [
            ...prevImageChange,
            ...validImages,
        ]);
    };
    // FUNCION PARA BORRAR LAS IMAGENES DEL CLIENTE
    const deleteImageChange = (lastModifiedToDelete) => {
        setImageChange((prevImageChange) =>
            prevImageChange.filter(
                (image) => image.lastModified !== lastModifiedToDelete
            )
        );
    };
    useEffect(() => {
        if (imageChange.length >= 2) {
            setErros((prevErrors) => ({
                ...prevErrors,
                imagenes: false,
            }));
            // handleValidations();
            setHasErrorImg(false);
        }
    }, [imageChange]);
    useEffect(() => {
        if (categorias.length !== 0) {
            setErros((prevErrors) => ({
                ...prevErrors,
                categoria: false,
            }));
            // handleValidations();
            setHasErrorCat(false);
        }
    }, [categorias]);
    useEffect(() => {
        if (caracteristicas.length !== 0) {
            setErros((prevErrors) => ({
                ...prevErrors,
                caracteristicas: false,
            }));
            // handleValidations();
            setHasErrorCaract(false);
        }
    }, [caracteristicas]);
    // const options = categoriesData?.map((cat) => ({
    //     value: cat.categoriaId,
    //     label: cat.nombre,
    // }));
    // useEffect(() => {
    //     if (selectedOption) {
    //         const selectedCategories = selectedOption.map((option) => ({
    //             categoriaId: option.value,
    //         }));

    //         setFormState({
    //             ...formState,
    //             categorias: selectedCategories,
    //         });
    //     }
    // }, [selectedOption]);
    return (
        <>
            <div
                className={` rounded-xl max-h-[600px] overflow-hidden bg-white  fixed top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2  w-[700px] mx-auto transition-opacity duration-200 z-50 `}
            >
                {/* HEADER */}
                <div className=" w-full h-16  flex justify-between p-3 border-b-[1px] border-gray-300 bg-primary text-white">
                    <h2 className="text-xl font-semibold flex gap-5 items-center">
                        <p>Agregar nuevo producto</p>
                        {loadingBikes && <Loader className={"text-white"} />}
                    </h2>
                    <Tooltip title="Cerrar">
                        <button
                            className="flex  items-center justify-center middle none center rounded-full  h-10 w-10 font-sans text-xs font-bold uppercase  transition-all hover:bg-blackOpacity1 active:bg-tertiary disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none "
                            data-ripple-dark="true"
                            disabled={loadingBikes}
                            onClick={() => {
                                setOpenNewProductModal(false);
                                setImageChange([]);
                                onResetForm();
                                setError(false);
                            }}
                        >
                            <BsXLg className="text-lg" />
                        </button>
                    </Tooltip>
                </div>
                {/* MAIN */}
                <div className=" p-5 max-h-[450px] overflow-auto mb-16">
                    <div className="w-full">
                        <h2 className="text-lg font-semibold pb-3 border-b-[1px] border-gray-300">
                            Detalles del producto
                        </h2>
                    </div>
                    <div className="flex gap-5">
                        {/* div de la izquierda */}
                        <div className="flex flex-col gap-3 flex-1">
                            {/* NOMBRE DEL PRODUCTO */}
                            <div>
                                <label className="text-base font-semibold mb-2">
                                    Nombre del producto *
                                </label>
                                <div
                                    className={`relative h-11 w-full min-w-[200px] shadow-md rounded-xl border-[1px] border-gray-100 overflow-hidden `}
                                >
                                    <input
                                        className={` peer h-full w-full  p-2 font-sans text-sm font-normal text-blue-gray-700 outline outline-0 transition-all  focus:outline-0  disabled:bg-blue-gray-50`}
                                        placeholder="Text"
                                        type="Bicicleta de ciudad"
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
                                        error.status === 409
                                            ? "block"
                                            : "hidden"
                                    }`}
                                >
                                    Ya existe un producto con ese nombre
                                </p>
                            </div>
                            {/* ------------------------------------ */}
                            {/* <Select
                                    defaultInputValue={selectedOption}
                                    onChange={setSelectedOption}
                                    className="shadow-md"
                                    options={options}
                                    isMulti
                                    placeholder={"Seleciona categorias"}
                                    isSearchable
                                    noOptionsMessage={() =>
                                        "Ninguna categoria encontrada"
                                    }
                                    styles={{
                                        control: (baseStyles, state) => ({
                                            ...baseStyles,
                                            borderWidth: 0,
                                            borderRadius: 10,
                                            boxShadow: "none",
                                        }),
                                        container: (baseStyles, state) => ({
                                            ...baseStyles,

                                            borderRadius: 10,
                                        }),
                                        multiValueRemove: (
                                            baseStyles,
                                            state
                                        ) => ({
                                            ...baseStyles,
                                            color: "white",
                                            borderRadius: 10,
                                            "&:hover": {
                                                backgroundColor: "#0274AE",
                                                color: "white",
                                            },
                                        }),
                                        multiValue: (baseStyles, state) => ({
                                            ...baseStyles,
                                            backgroundColor: "#0274AE",
                                            borderRadius: 5,
                                            "&:hover": {
                                                backgroundColor: "#0274AE",
                                                color: "white",
                                            },
                                        }),
                                        multiValueLabel: (
                                            baseStyles,
                                            state
                                        ) => ({
                                            ...baseStyles,
                                            color: "white",
                                        }),
                                    }}
                                /> */}
                            {/* ------------------------------------ */}
                            {/* CATEGORIA */}
                            <div>
                                <label className="text-base font-semibold mb-2">
                                    Categorías *
                                </label>
                                {/* TODO: en base al value del select, cambia el color del texto a text-graty-400 o "" */}
                                <div
                                    className={`relative h-11 w-full min-w-[200px]  border-[1px]  border-gray-100 overflow-hidden shadow-md rounded-xl text-gray-400`}
                                >
                                    <select
                                        ref={selectCatRef}
                                        name="categoria"
                                        value={categorias.categoriaId}
                                        onChange={handleCategoryChange}
                                        className="peer h-full w-full p-2 font-sans text-sm font-normal  outline outline-0 transition-all focus:outline-0 disabled:bg-blue-gray-50"
                                    >
                                        <option
                                            value=""
                                            className="text-gray-400"
                                        >
                                            Selecciona una categoría
                                        </option>
                                        {/*TODO: hacer el map con los options */}

                                        {categoriesData.map((cat) => (
                                            <option
                                                key={cat.categoriaId}
                                                value={cat.categoriaId}
                                                className="text-black"
                                            >
                                                {cat.nombre}
                                            </option>
                                        ))}
                                    </select>
                                </div>
                                <p
                                    className={`pt-1 text-xs text-red-500 ${
                                        erros.categoria
                                            ? "opacity-100"
                                            : "opacity-0"
                                    }`}
                                >
                                    Campo obligatorio
                                </p>

                                <div className="flex gap-2 flex-wrap">
                                    {categoriesData.map((categoryData) => {
                                        const matchingCategory =
                                            categorias.find(
                                                (category) =>
                                                    category.categoriaId ===
                                                    categoryData.categoriaId
                                            );

                                        if (matchingCategory) {
                                            return (
                                                <div
                                                    className="flex gap-2 items-center text-white p-1 bg-primary rounded-md cursor-pointer hover:bg-secondary "
                                                    key={
                                                        categoryData.categoriaId
                                                    }
                                                    onClick={() =>
                                                        handleCategoryDeletefromFormstate(
                                                            categoryData.categoriaId
                                                        )
                                                    }
                                                >
                                                    <p>{categoryData.nombre}</p>
                                                    <BsX className="text-lg " />
                                                </div>
                                            );
                                        }
                                        return null; // O puedes devolver un elemento nulo si no hay coincidencia
                                    })}
                                </div>
                            </div>
                            {/* CARACTERISTICS */}
                            <div>
                                <label className="text-base font-semibold mb-2">
                                    Características *
                                </label>
                                {/* TODO: en base al value del select, cambia el color del texto a text-graty-400 o "" */}
                                <div
                                    className={`relative h-11 w-full min-w-[200px]  border-[1px]  border-gray-100 overflow-hidden shadow-md rounded-xl $`}
                                >
                                    <select
                                        ref={selectRef}
                                        name="caracteristica"
                                        value={caracteristicas.caracteristicaId}
                                        onChange={handleCaracteristicsChange}
                                        className={`peer h-full w-full p-2 font-sans text-sm font-normal outline outline-0 transition-all focus:outline-0 disabled:bg-blue-gray-50 text-gray-400`}
                                    >
                                        <option
                                            value=""
                                            className="text-gray-400"
                                        >
                                            Selecciona una característica
                                        </option>
                                        {characteristicsData.map((cat) => (
                                            <option
                                                key={cat.caracteristicaId}
                                                value={cat.caracteristicaId}
                                                className="text-black"
                                            >
                                                {cat.nombre}
                                            </option>
                                        ))}
                                    </select>
                                </div>
                                <p
                                    className={`pt-1 text-xs text-red-500 ${
                                        erros.caracteristicas
                                            ? "opacity-100"
                                            : "opacity-0"
                                    }`}
                                >
                                    Campo obligatorio
                                </p>

                                <div className="flex gap-2 flex-wrap">
                                    {characteristicsData.map(
                                        (characteristicData) => {
                                            const matchingCategory =
                                                caracteristicas.find(
                                                    (characteristic) =>
                                                        characteristic.caracteristicaId ===
                                                        characteristicData.caracteristicaId
                                                );

                                            if (matchingCategory) {
                                                return (
                                                    <div
                                                        className="flex gap-2 items-center text-white p-1 pl-2 bg-primary rounded-md cursor-pointer hover:bg-secondary "
                                                        key={
                                                            characteristicData.caracteristicaId
                                                        }
                                                        onClick={() =>
                                                            handleCharacteristicDeletefromFormstate(
                                                                characteristicData.caracteristicaId
                                                            )
                                                        }
                                                    >
                                                        <div className="flex gap-2 items-center">
                                                            <i
                                                                className={`fa-solid ${characteristicData.icono}`}
                                                            ></i>
                                                            <p>
                                                                {
                                                                    characteristicData.nombre
                                                                }
                                                            </p>
                                                        </div>

                                                        <BsX className="text-lg " />
                                                    </div>
                                                );
                                            }
                                            return null; // O puedes devolver un elemento nulo si no hay coincidencia
                                        }
                                    )}
                                </div>
                            </div>
                            {/* PRECIO POR DIA */}
                            <div>
                                <label className="text-base font-semibold mb-2">
                                    Precio por día *
                                </label>
                                <div className="relative h-11 w-full min-w-[200px] shadow-md rounded-xl border-[1px]  border-gray-100 overflow-hidden">
                                    {/*TODO: si el value del input es vacio que sea text-gray-400 sino "" */}
                                    <BsCurrencyDollar className="absolute text-gray-400 left-1 top-1/2 transform -translate-y-1/2" />
                                    <input
                                        type="number"
                                        className="peer h-full w-full   py-2 pl-5 pr-2 font-sans text-sm font-normal text-blue-gray-700 outline outline-0 transition-all  focus:outline-0  disabled:bg-blue-gray-50"
                                        placeholder="1500"
                                        value={precioAlquilerPorDia}
                                        name="precioAlquilerPorDia"
                                        onChange={(e) =>
                                            handleInputChange(e, true)
                                        }
                                    />
                                </div>
                                <p
                                    className={` pt-1 text-xs text-red-500 ${
                                        erros.precioAlquilerPorDia
                                            ? "opacity-100"
                                            : "opacity-0"
                                    }`}
                                >
                                    Campo obligatorio
                                </p>
                            </div>
                            {/* STOCK */}
                            {/* <div>
                                <label className="text-base font-semibold mb-2">
                                    Stock *
                                </label>
                                <div className="relative h-11 w-full min-w-[200px] shadow-md rounded-xl border-[1px]  border-gray-100 overflow-hidden">
                                    <input
                                        type="number"
                                        className="peer h-full w-full    p-2 font-sans text-sm font-normal text-blue-gray-700 outline outline-0 transition-all  focus:outline-0  disabled:bg-blue-gray-50"
                                        placeholder="5"
                                    />
                                </div>
                            </div> */}
                        </div>
                        <div className="flex flex-col gap-3 flex-1">
                            {/* TEXT AREA */}
                            <div>
                                <label className="text-base font-semibold mb-2">
                                    Descripción *
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
                                    className={` pt-1 text-xs text-red-500 ${
                                        erros.descripcion
                                            ? "opacity-100"
                                            : "opacity-0"
                                    }`}
                                >
                                    Campo obligatorio
                                </p>
                            </div>
                            {/* IMAGENES */}
                            <div className="relative">
                                <p className="text-base font-semibold mb-2 text-primary">
                                    Cargar imágenes *
                                </p>
                                <div className="grid grid-cols-4 grid-rows-1 gap-5 ">
                                    <input
                                        type="file"
                                        multiple
                                        accept="image/*"
                                        className="hidden"
                                        onChange={onFileInputChange}
                                        ref={fileInputRef}
                                    />
                                    <Tooltip title="Cargar imágenes">
                                        <div className=" w-16 h-16 flex items-center justify-center shadow-md rounded-xl  border-[1px] border-gray-100">
                                            <BsCloudUpload
                                                className="text-xl text-gray-400 cursor-pointer"
                                                onClick={() =>
                                                    fileInputRef.current.click()
                                                }
                                            />
                                        </div>
                                    </Tooltip>

                                    {/* MAPEAR ESTE POR CADA IMAGEN SELECCIONADA */}
                                    {imageChange &&
                                        imageChange.map((image) => (
                                            <div
                                                className="relative group  w-16 h-16  shadow-md border-[1px] border-gray-100 overflow-hidden text-white"
                                                key={image.lastModified}
                                            >
                                                <img
                                                    className="w-full  h-full object-cover pointer-events-none"
                                                    src={URL.createObjectURL(
                                                        image
                                                    )}
                                                    alt=""
                                                />
                                                <div className="w-full h-full absolute bg-grayTertiary top-0 opacity-0 group-hover:opacity-50 "></div>

                                                <BsXLg
                                                    className=" bg-primary  opacity-40 group-hover:opacity-100  p-1 rounded-full text-2xl   absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 z-20  cursor-pointer"
                                                    onClick={() => {
                                                        deleteImageChange(
                                                            image.lastModified
                                                        );
                                                        console.log(image);
                                                    }}
                                                />
                                            </div>
                                        ))}
                                </div>
                                <p
                                    className={`absolute top-24 pt-1 text-xs text-red-500 ${
                                        erros.imagenes
                                            ? "opacity-100"
                                            : "opacity-0"
                                    }`}
                                >
                                    Debes subir como minimo 2 imagenes
                                </p>
                            </div>
                        </div>
                    </div>
                    <p
                        className={`text-red-500 text-center ${
                            error && error?.status !== 409
                                ? "opacity-100"
                                : "opacity-0"
                        }`}
                    >
                        {error.message}
                    </p>
                </div>
                {/* FOOTER */}
                <div className="flex justify-between p-3 items-center border-t-[1px] border-gray-300 fixed bottom-0 right-0-0 w-full bg-white z-20 h-16">
                    <h2 className={``}> * Campos obligatorios</h2>
                    <div className="flex gap-2">
                        <button
                            className=" middle none center rounded-full border border-primary py-2 px-6 font-sans text-xs font-bold uppercase text-primary transition-all hover:opacity-75 focus:ring focus:ring-tertiary active:opacity-[0.85] disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none"
                            data-ripple-dark="true"
                            onClick={handleSaveAndNew}
                            disabled={loadingBikes}
                        >
                            GUARDAR Y NUEVO
                        </button>
                        <button
                            className="middle none center  rounded-full bg-primary py-2 px-6 font-sans text-xs font-bold uppercase text-white shadow-sm  transition-all  hover:shadow-secondary  active:opacity-[0.85] active:shadow-none disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none"
                            data-ripple-light="true"
                            disabled={loadingBikes}
                            onClick={handleSave}
                        >
                            GUARDAR
                        </button>
                    </div>
                </div>
            </div>

            <div
                className={`fixed top-0 left-0 w-full h-full bg-black bg-opacity-70 transition-opacity duration-200 z-40`}
            ></div>
        </>
    );
};
