export const getFavorite = async (id) => {
    try {
        const { token } = JSON.parse(localStorage.getItem("accessToken"));
        // const res = await fetch(import.meta.env.VITE_URL + "/");
        const res = await fetch(
            `https://bikemenowapi.3utilities.com/bike-me-now/api/favoritos/usuarios/${id}`,
            {
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

export const postFavorites = async (userId, bikeId) => {
    try {
        const { token } = JSON.parse(localStorage.getItem("accessToken"));

        const res = await fetch(
            "https://bikemenowapi.3utilities.com/bike-me-now/api/favoritos",
            {
                method: "POST",
                headers: {
                    Authorization: `Bearer ${token}`,
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    usuario: {
                        usuarioId: userId,
                    },
                    bicicleta: {
                        bicicletaId: bikeId,
                    },
                }),
            }
        );

        if (!res.ok) {
            let error = {
                status: res.status,
                ok: false,
                message: "Error en la solicitud POST",
            };
            if (res.status === 403) {
                error.message = "Error: No autorizado";
            }
            throw error;
        }

        const data = await res.json();
        return data;
    } catch (error) {
        throw error;
    }
};

export const deleteFavorite = async (id) => {
    const { token } = JSON.parse(localStorage.getItem("accessToken"));
    try {
        const res = await fetch(
            `https://bikemenowapi.3utilities.com/bike-me-now/api/favoritos/${id}`,
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
