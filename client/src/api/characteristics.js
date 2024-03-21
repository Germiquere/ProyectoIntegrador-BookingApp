export const getCharacteristics = async () => {
    try {
        const { token } = JSON.parse(localStorage.getItem("accessToken"));
        const res = await fetch(
            "https://tasteless-hope-production.up.railway.app/bike-me-now/api/caracteristicas",
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
