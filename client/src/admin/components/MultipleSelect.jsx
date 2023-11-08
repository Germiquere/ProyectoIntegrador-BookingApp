import React from "react";
import { IoIosArrowDown } from "react-icons/io";
import { useCategoriesContext } from "../../context/CategoriesContext";
export const MultipleSelect = () => {
    const { categoriesData } = useCategoriesContext();
    console.log(categoriesData);
    const handleClick = () => {
        console.log("hola");
    };
    return (
        <>
            <select value={"hola"} className="block">
                <option value="german">Selecciona una categoria</option>
                {categoriesData.map((cat) => (
                    <option
                        value="german"
                        key={cat.categoriaId}
                        onClick={handleClick}
                    >
                        {cat.nombre}
                    </option>
                ))}
            </select>
            <div>
                <p>genial</p>
            </div>
        </>
    );
};
