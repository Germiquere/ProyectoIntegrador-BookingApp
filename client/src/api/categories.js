export const getCategories = async () => {
    try {
        // TODO: PASAR EL ENDPOINT DE NUESTRA API POR MEDIO DE LAS VARIABLES DE ENTORNO

        // const res = await fetch(import.meta.env.VITE_URL + "/");
        const res = await fetch(
            "https://tasteless-hope-production.up.railway.app/bike-me-now/categorias"
        );

        if (!res.ok) {
            // Crear un objeto de error personalizado con estado y ok
            const error = new Error("Error en la solicitud POST");
            error.status = res.status;
            error.ok = false;
            throw error;
        }
        const data = await res.json();
        return data;
    } catch (error) {
        throw error;
    }
};

export const postCategory = async (newBikeCategory) => {
    const { token } = JSON.parse(localStorage.getItem("accessToken"));
    try {
        // PASAR EL ENDPOINT DE NUESTRA API
        const res = await fetch(
            "https://tasteless-hope-production.up.railway.app/bike-me-now/api/categorias",
            {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${token}`,
                },

                body: JSON.stringify(newBikeCategory),
            }
        );
        if (!res.ok) {
            // Crear un objeto de error personalizado con estado y ok
            const error = new Error("Error en la solicitud POST");
            error.status = res.status;
            error.ok = false;
            throw error;
        }
        const data = await res.json();
        return data;
    } catch (error) {
        throw error;
    }
};
export const deleteCategory = async (id) => {
    const { token } = JSON.parse(localStorage.getItem("accessToken"));
    try {
        const res = await fetch(
            `https://tasteless-hope-production.up.railway.app/bike-me-now/api/categorias/${id}`,
            {
                method: "DELETE",
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            }
        );
        if (!res.ok) {
            // Crear un objeto de error personalizado con estado y ok
            const error = new Error("Error en la solicitud POST");
            error.status = res.status;
            error.ok = false;
            throw error;
        }
        const data = await res.json();
        return data;
    } catch (error) {
        throw error;
    }
};
