// USERS
export const getUser = async () => {
    try {
        const { token } = JSON.parse(localStorage.getItem("accessToken"));
        const res = await fetch(
            "https://bikemenowapi.3utilities.com/bike-me-now/api/usuarios/buscar-por-token",
            {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            }
        );

        if (!res.ok) {
            let error = {
                status: res.status,
                ok: false,
                message: "Error en la solicitud GET",
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

export const getUsers = async () => {
    try {
        const { token } = JSON.parse(localStorage.getItem("accessToken"));
        const res = await fetch(
            "https://bikemenowapi.3utilities.com/bike-me-now/api/usuarios",
            {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            }
        );

        if (!res.ok) {
            let error = {
                status: res.status,
                ok: false,
                message: "Error en la solicitud GET",
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
export const updateUser = async (user) => {
    try {
        const { token } = JSON.parse(localStorage.getItem("accessToken"));
        const res = await fetch(
            `https://bikemenowapi.3utilities.com/bike-me-now/bicicletas/`,
            {
                method: "PUT",
                headers: {
                    Authorization: `Bearer ${token}`,
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(user),
            }
        );

        if (!res.ok) {
            let error = {
                status: res.status,
                ok: false,
                message: "Error en la solicitud PUT",
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
export const deleteUser = async (id) => {
    try {
        const { token } = JSON.parse(localStorage.getItem("accessToken"));
        const res = await fetch(
            `https://bikemenowapi.3utilities.com/bike-me-now/bicicletas/${id}`,
            {
                method: "DELETE",
                headers: {
                    Authorization: `Bearer ${token}`,
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(user),
            }
        );

        if (!res.ok) {
            let error = {
                status: res.status,
                ok: false,
                message: "Error en la solicitud DELETE",
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
export const putUserRol = async (user) => {
    try {
        const { token } = JSON.parse(localStorage.getItem("accessToken"));
        const res = await fetch(
            `https://bikemenowapi.3utilities.com/bike-me-now/api/usuarios/${user.usuarioId}/cambiar-rol?rol=${user.rol}`,
            {
                method: "PUT",
                headers: {
                    Authorization: `Bearer ${token}`,
                    // "Content-Type": "application/json",
                },
                // body: JSON.stringify(user),
            }
        );

        if (!res.ok) {
            let error = {
                status: res.status,
                ok: false,
                message: "Error en la solicitud PUT",
            };

            throw error;
        }

        const data = await res.json();
        return data;
    } catch (error) {
        throw error;
    }
};
