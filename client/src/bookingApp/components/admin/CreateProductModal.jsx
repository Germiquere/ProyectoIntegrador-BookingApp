import { useRef, useState } from "react";
import { BsXLg, BsCurrencyDollar, BsCloudUpload } from "react-icons/bs";
import { useBikesContext } from "../../../context/BikesContext";
import { Loader } from "../../../ui/loader";
import { postImage } from "../../../api/images";

export const CreateProductModal = () => {
    const {
        formState,
        onInputChange,
        onResetForm,
        onCategoryChange,
        addNewBike,
        loading: loadingBikes,
        setFormState,
        setOpenNewProductModal,
    } = useBikesContext();
    const { nombre, descripcion, precioAlquilerPorDia, categoria, imagenes } =
        formState;
    const [imageChange, setImageChange] = useState([]);
    const fileInputRef = useRef(null);
    console.log(formState);
    // FUNCION PARA SUBIR IMAGENES A S3
    const handlePostImages = async (images) => {
        // CREAR UN ARRAY DE PROMESAS
        const imagePromises = [];
        console.log(images);
        for (const image of images) {
            imagePromises.push(postImage(image));
        }
        const imageUrls = await Promise.all(imagePromises);
        console.log(imageUrls);
        setFormState({
            ...formState,
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
        });
    };
    // FUNCION PARA GUARDAR Y CREAR UN NUEVO PRODUCTO
    const handleSaveAndNew = () => {
        // EJECUTAR LA FUNCION QUE ME TRAE LAS URLS
        // handlePostImages(imageChange);
        console.log(formState);
        addNewBike(formState);
        // onResetForm();
    };
    const handleSave = () => {
        // EJECUTAR LA FUNCION QUE ME TRAE LAS URLS
        // addNewBike(formState);
        console.log(formState);
        // onResetForm();
        setOpenNewProductModal(false);
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

    return (
        <>
            <div
                className={` rounded-xl max-h-[600px] overflow-hidden bg-white  fixed top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 max-w-[1200px] min-w-[700px] mx-auto transition-opacity duration-200 z-50 `}
            >
                {/* HEADER */}
                <div className=" w-full h-16  flex justify-between p-3 border-b-[1px] border-gray-300 bg-primary text-white">
                    <h2 className="text-xl font-semibold flex gap-5 items-center">
                        <p>Agregar nuevo producto</p>
                        {loadingBikes && <Loader className={"text-white"} />}
                    </h2>
                    <button
                        className="flex  items-center justify-center middle none center rounded-full  h-7 w-7 font-sans text-xs font-bold uppercase  transition-all hover:bg-blackOpacity1 active:bg-tertiary disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none "
                        data-ripple-dark="true"
                        disabled={loadingBikes}
                        onClick={() => setOpenNewProductModal(false)}
                    >
                        <BsXLg className="text-lg" />
                    </button>
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
                                <div className="relative h-11 w-full min-w-[200px] shadow-md rounded-xl border-[1px] border-gray-100 overflow-hidden">
                                    <input
                                        className="peer h-full w-full  p-2 font-sans text-sm font-normal text-blue-gray-700 outline outline-0 transition-all  focus:outline-0  disabled:bg-blue-gray-50"
                                        placeholder="Text"
                                        type="text"
                                        value={nombre}
                                        name="nombre"
                                        onChange={onInputChange}
                                    />
                                </div>
                            </div>
                            {/* CATEGORIA */}
                            <div>
                                <label className="text-base font-semibold mb-2">
                                    Categoria *
                                </label>
                                {/* TODO: en base al value del select, cambia el color del texto a text-graty-400 o "" */}
                                <div
                                    className={`relative h-11 w-full min-w-[200px]  border-[1px]  border-gray-100 overflow-hidden shadow-md rounded-xl ${
                                        categoria.categoriaId
                                            ? ""
                                            : "text-gray-400"
                                    }`}
                                >
                                    <select
                                        name="categoria"
                                        value={categoria.categoriaId}
                                        onChange={onCategoryChange}
                                        className="peer h-full w-full p-2 font-sans text-sm font-normal  outline outline-0 transition-all focus:outline-0 disabled:bg-blue-gray-50"
                                    >
                                        <option
                                            value=""
                                            className="text-gray-400"
                                        >
                                            Selecciona una categoria
                                        </option>
                                        {/*TODO: hacer el map con los options */}
                                        <option
                                            value="1"
                                            className="text-black"
                                        >
                                            Ruta
                                        </option>
                                        <option
                                            value="2"
                                            className="text-black"
                                        >
                                            Electrica
                                        </option>
                                    </select>
                                </div>
                            </div>

                            {/* PRECIO POR DIA */}
                            <div>
                                <label className="text-base font-semibold mb-2">
                                    Precio por dia *
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
                                        onChange={(e) => onInputChange(e, true)}
                                    />
                                </div>
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
                                    Descripcion
                                </label>
                                <textarea
                                    className=" p-2 w-full outline outline-0 shadow-md border-[1px] rounded-xl overflow-hidden border-gray-100 "
                                    style={{ resize: "none" }}
                                    placeholder="Descripcion del producto"
                                    rows={4}
                                    value={descripcion}
                                    name="descripcion"
                                    onChange={onInputChange}
                                ></textarea>
                            </div>
                            {/* IMAGENES */}
                            <div>
                                <p className="text-base font-semibold mb-2 text-primary">
                                    Cargar imagenes *
                                </p>
                                <div className="grid grid-cols-4 grid-rows-2 gap-5 ">
                                    <input
                                        type="file"
                                        multiple
                                        accept="image/*"
                                        className="hidden"
                                        onChange={onFileInputChange}
                                        ref={fileInputRef}
                                    />
                                    <div className=" w-16 h-16 flex items-center justify-center shadow-md rounded-xl  border-[1px] border-gray-100">
                                        <BsCloudUpload
                                            className="text-xl text-gray-400 cursor-pointer"
                                            onClick={() =>
                                                fileInputRef.current.click()
                                            }
                                        />
                                    </div>
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
                            </div>
                        </div>
                    </div>
                </div>
                {/* FOOTER */}
                <div className="flex justify-between p-3 border-t-[1px] border-gray-300 fixed bottom-0 right-0-0 w-full bg-white z-20 h-16">
                    <h2> * Campos obligatorios</h2>
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
