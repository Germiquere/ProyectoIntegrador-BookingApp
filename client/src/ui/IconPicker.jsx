import React, { useState } from "react";

export const IconPicker = () => {
    const [iconSelected, setIconSelected] = useState("");
    const icons = [{ name: "fa-bicycle" }, { name: "fa-person-biking" }];
    return (
        <div>
            <input
                type="text"
                placeholder="sleccionar tu icono"
                name="icon"
                value={iconSelected}
            />
            <div className="bg-indigo-400 w-200px h-10 flex items-center">
                <p>Selecciona un icono</p>
                {icons.map((icon) => (
                    <i
                        className={`fa-solid ${icon.name}`}
                        key={icon.name}
                        onClick={() => {
                            setIconSelected(icon.name);
                        }}
                    ></i>
                ))}
            </div>
        </div>
    );
};
