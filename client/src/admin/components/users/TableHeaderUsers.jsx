import React from "react";

export const TableHeaderUsers = () => {
    return (
        <div className="flex gap-8 justify-between items-center, rounded-full bg-tertiary font-semibold text-xs p-3">
            <p className="w-10">ID</p>
            <p className="flex-1">Nombre</p>
            <p className="flex-1">correo</p>
            <p className="w-24">Rol</p>
        </div>
    );
};
