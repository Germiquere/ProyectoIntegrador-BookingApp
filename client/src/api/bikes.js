export const getBikes = async () => {
    try {
        // TODO: PASAR EL ENDPOINT DE NUESTRA API POR MEDIO DE LAS VARIABLES DE ENTORNO

        // const res = await fetch(import.meta.env.VITE_URL + "/");
        const res = await fetch("http://localhost:8080/bike-me-now/bicicletas");
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
export const getBikeById = async (id) => {
    try {
        // TODO: PASAR EL ENDPOINT DE NUESTRA API POR MEDIO DE LAS VARIABLES DE ENTORNO

        // const res = await fetch(import.meta.env.VITE_URL + "/");
        const res = await fetch(
            `http://localhost:8080/bike-me-now/bicicletas/${id}`
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
export const postBike = async (bike) => {
    try {
        // PASAR EL ENDPOINT DE NUESTRA API
        const res = await fetch(
            "http://localhost:8080/bike-me-now/bicicletas",
            {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(bike),
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
        // throw new Error("Error al cargar el producto");
        throw error;
    }
};
export const deleteBike = async (id) => {
    try {
        const res = await fetch(
            `http://localhost:8080/bike-me-now/bicicletas/${id}`,
            {
                method: "DELETE",
            }
        );
        if (!res.ok) {
            // Crear un objeto de error personalizado con estado y ok
            const error = new Error("Error en la solicitud POST");
            error.status = res.status;
            error.ok = false;
            throw error;
        }
        console.log(res);
        const data = await res.json();
        return data;
    } catch (error) {
        console.log(error);
        throw error;
    }
};
export const updateBike = async (bike) => {
    try {
        // PASAR EL ENDPOINT DE NUESTRA API
        const res = await fetch(
            "http://localhost:8080/bike-me-now/bicicletas",
            {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(bike),
            }
        );
        console.log(res);
        if (!res.ok) {
            // Crear un objeto de error personalizado con estado y ok
            const error = new Error("Error en la solicitud PUT");
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
