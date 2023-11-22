// BUSCAR TODAS LAS POLITICAS
export const getPolicies = async () => {
    try {
        const { token } = JSON.parse(localStorage.getItem("accessToken"));
        const res = await fetch(`http://localhost:8080/bike-me-now/politicas`, {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        });
        if (!res.ok) {
            const error = new Error("Error en la solicitud POST");
            error.status = res.status;
            error.ok = false;
            throw error;
        }
        const data = await res.json();
        console.log(data);
        return data;
    } catch (error) {
        throw error;
    }
};
// BUSCAR POR ID
export const getPoliciesId = async (id) => {
    try {
        const res = await fetch(
            `http://localhost:8080/bike-me-now/politicas/${id}`
        );

        if (!res.ok) {
            // Crear un objeto de error personalizado con estado y ok
            const error = new Error("Error en la solicitud POST");
            error.status = res.status;
            error.ok = false;
            throw error;
        }
        const data = await res.json();
        console.log(data);
        return data;
    } catch (error) {
        throw error;
    }
};

// CREAR POLITICA
export const postPolicies = async (titulo, descripcion) => {
    try {
        const { token } = JSON.parse(localStorage.getItem("accessToken"));

        const res = await fetch(
            "http://localhost:8080/bike-me-now/api/politicas",
            {
                method: "POST",
                headers: {
                    Authorization: `Bearer ${token}`,
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    titulo,
                    descripcion,
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

// ELIMINAR POLITICA
export const deletePolicies = async (id) => {
    console.log("ID antes de la solicitud DELETE:", id);
    const { token } = JSON.parse(localStorage.getItem("accessToken"));
    try {
        const res = await fetch(
            `http://localhost:8080/bike-me-now/api/politicas/${id}`,
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
