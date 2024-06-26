// TRAE TODAS LAS RESERVAS
export const getBookings = async () => {
    try {
        const { token } = JSON.parse(localStorage.getItem("accessToken"));
        const res = await fetch(
            "https://tasteless-hope-production.up.railway.app/bike-me-now/api/reservas",
            {
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${token}`,
                },
            }
        );

        if (!res.ok) {
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

// BUSCAR RESERVAS POR ID
export const getBookingsByUserId = async (id) => {
    try {
        const { token } = JSON.parse(localStorage.getItem("accessToken"));
        const res = await fetch(
            `https://tasteless-hope-production.up.railway.app/bike-me-now/api/reservas/usuarios/${id}`,
            {
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${token}`,
                },
            }
        );

        if (!res.ok) {
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

// CREAR RESERVA
export const postBookings = async (userId, bikeId, fechaInicio, fechaFin) => {
    try {
        const { token } = JSON.parse(localStorage.getItem("accessToken"));
        const res = await fetch(
            "https://tasteless-hope-production.up.railway.app/bike-me-now/api/reservas",
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
                    fechaInicio,
                    fechaFin,
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
        // Agregar el console.log después de recibir la respuesta exitosa
        return data;
    } catch (error) {
        throw error;
    }
};

// CREAR VALORACION
export const postRating = async (rating) => {
    try {
        const { token } = JSON.parse(localStorage.getItem("accessToken"));
        const res = await fetch(
            "https://tasteless-hope-production.up.railway.app/bike-me-now/api/valoraciones",
            {
                method: "POST",
                headers: {
                    Authorization: `Bearer ${token}`,
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(rating),
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
        // Agregar el console.log después de recibir la respuesta exitosa
        return data;
    } catch (error) {
        throw error;
    }
};
export const deleteBooking = async (id) => {
    const { token } = JSON.parse(localStorage.getItem("accessToken"));
    try {
        const res = await fetch(
            `https://tasteless-hope-production.up.railway.app/bike-me-now/api/reservas/${id}`,
            {
                method: "DELETE",
                headers: {
                    // "Content-Type": "application/json",
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
        console.log(res);
        const data = await res.json();
        return data;
    } catch (error) {
        console.log(error);
        throw error;
    }
};
