// USERS
export const getUser = async () => {
    try {
        const token = localStorage.getItem("token");
        console.log(token);
        const res = await fetch(
            "http://localhost:8080/bike-me-now/api/usuarios/buscar-por-token",
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
        const token = localStorage.getItem("token");
        const res = await fetch(
            "http://localhost:8080/bike-me-now/api/usuarios",
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
        const token = localStorage.getItem("token");
        const res = await fetch(
            `http://localhost:8080/bike-me-now/bicicletas/`,
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
        const token = localStorage.getItem("token");
        const res = await fetch(
            `http://localhost:8080/bike-me-now/bicicletas/${id}`,
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
