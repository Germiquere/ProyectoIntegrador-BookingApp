export const getBikes = async () => {
    try {
        // TODO: PASAR EL ENDPOINT DE NUESTRA API POR MEDIO DE LAS VARIABLES DE ENTORNO

        // const res = await fetch(import.meta.env.VITE_URL + "/");
        const res = await fetch(
            "https://pokeapi.co/api/v2/pokemon?limit=20&offset=0"
        );

        if (!res.ok) {
            throw new Error("Error en la solicitud GET de las bicicletas");
        }
        const data = await res.json();
        return data;
    } catch (error) {
        throw error;
    }
};
export const getBikeById = async (id) => {
    try {
        // TODO: PASAR EL ENDPOINT DE NUESTRA API POR MEDIO DE LAS VARIABLES DE ENTORNO

        // const res = await fetch(import.meta.env.VITE_URL + "/");
        const res = await fetch(
            `https://pokeapi.co/api/v2/pokemon?limit=20&offset=0/${id}`
        );

        if (!res.ok) {
            throw new Error("Error en la solicitud GET de las bicicletas");
        }
        const data = await res.json();
        return data;
    } catch (error) {
        throw error;
    }
};
