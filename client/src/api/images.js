export const postImage = async (file) => {
    const { token } = JSON.parse(localStorage.getItem("accessToken"));

    if (!file) throw new Error("No hay ningúna imagen para subir");
    const formData = new FormData();
    formData.append("bucketName", "bucket-bikemenow");
    formData.append("filePath", "bikemenowImages/");
    formData.append("file", file);
    try {
        const res = await fetch(
            `https://tasteless-hope-production.up.railway.app/bike-me-now/api/s3/uploadFile`,
            {
                method: "POST",
                headers: {
                    Authorization: `Bearer ${token}`,
                },
                body: formData,
            }
        );
        if (!res.ok) {
            // Crear un objeto de error personalizado con estado y ok
            const error = new Error("Error en al subir las imagenes");
            error.status = res.status;
            error.ok = false;
            throw error;
        }
        const data = await res.json();
        return data;
    } catch (error) {
        throw new Error("Error al subir las imagenes");
    }
};
//TODO: VER COMO TERMINA SIENDO ESTO
export const deleteImage = async (key) => {
    const { token } = JSON.parse(localStorage.getItem("accessToken"));

    try {
        const res = await fetch(
            `https://tasteless-hope-production.up.railway.app/bike-me-now/api/s3/deleteFile?bucketName=bikemenowbucket&fileName=${key}`,
            {
                method: "DELETE",
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            }
        );
        if (!res.ok) {
            let error = {
                status: res.status,
                ok: false,
                message: "Error en la solicitud DELETE",
            };

            // TODO:MODIFICAR EN CADA UNO DE LAS SOLICITUDES LOS ERRORES
            if (res.status === 400) {
                error.message = "Error: Solicitud incorrecta";
            } else if (res.status === 401) {
                error.message = "Error: No autorizado";
            } else if (res.status === 404) {
                error.message = "Error: Recurso no encontrado";
            }

            throw error;
        }
        const data = await res.json();
        return data;
    } catch (error) {
        throw error;
    }
};
