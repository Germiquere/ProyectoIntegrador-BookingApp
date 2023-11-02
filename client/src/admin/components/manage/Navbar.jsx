import React from "react";

export const Navbar = ({ setCurrentComponent, currentComponent }) => {
    return (
        <nav>
            <ul className="flex">
                <li
                    className={`p-1 pb-0  rounded-t-md ${
                        currentComponent === "Categorias"
                            ? "bg-white text-primary"
                            : ""
                    }`}
                >
                    <button onClick={() => setCurrentComponent("Categorias")}>
                        Categorías
                    </button>
                </li>
                {/* <li
                    className={`p-1  rounded-t-md ${
                        currentComponent === "Algomas"
                            ? "bg-white text-primary"
                            : ""
                    }`}
                >
                    <button onClick={() => setCurrentComponent("Algomas")}>
                        Algomas
                    </button>
                </li> */}
            </ul>
        </nav>
    );
};
