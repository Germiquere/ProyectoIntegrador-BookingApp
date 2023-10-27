import React from "react";

export const TableHeader = () => {
    return (
        <div className="flex gap-8 justify-between items-center, rounded-full bg-tertiary font-semibold text-xs p-3">
            <p className="w-10">ID</p>
            <p className="flex-1">Producto</p>
            <p className="w-20">Precio</p>
            <p className="w-16">Acciones</p>
        </div>
    );
};
