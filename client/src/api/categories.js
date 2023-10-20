export const getCategories = async () => {
    try {
        // TODO: PASAR EL ENDPOINT DE NUESTRA API POR MEDIO DE LAS VARIABLES DE ENTORNO

        // const res = await fetch(import.meta.env.VITE_URL + "/");
        const res = await fetch(
            "https://pokeapi.co/api/v2/pokemon?limit=20&offset=0"
        );

        if (!res.ok) {
            throw new Error("Error en la solicitud GET");
        }
        const data = await res.json();
        return data;
    } catch (error) {
        throw error;
    }
};

export const postCategory = async (newBikeCategory) => {
    try {
        // PASAR EL ENDPOINT DE NUESTRA API
        const res = await fetch("URL", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(newBikeCategory),
        });
        if (!res.ok) {
            throw new Error("Error en la solicitud POST");
        }
        const data = await res.json();
        return data;
    } catch (error) {
        throw error;
    }
};
export const deleteCategory = async (id) => {
    try {
        const res = await fetch(`URL/${id}`, {
            method: "DELETE",
        });
        if (!res.ok) {
            throw new Error("Error en la solicitud POST");
        }
        const data = await res.json();
        return data;
    } catch (error) {
        throw error;
    }
};
