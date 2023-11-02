export const postImage = async (file) => {
    if (!file) throw new Error("No hay ningúna imagen para subir");
    const formData = new FormData();
    formData.append("bucketName", "bikemenowbucket");
    formData.append("filePath", "bikemenowImages/");
    formData.append("file", file);
    try {
        const res = await fetch(
            `http://localhost:8080/bike-me-now/api/s3/uploadFile`,
            {
                method: "POST",
                headers: {
                    Authorization:
                        "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBleGFtcGxlLmNvbSIsInJvbGVzIjpbIkFETUlOIl0sImlhdCI6MTY5ODg4OTU0MiwiZXhwIjoxNjk4ODkzMTQyfQ.7uAZbjI1I5q1l3ttzZcgpuz3Jq0oQbgyWLw2i039Kxk",
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
    try {
        const res = await fetch(
            `http://localhost:8080/bike-me-now/api/s3/deleteFile?bucketName=bikemenowbucket&fileName=${key}`,
            {
                method: "DELETE",
                headers: {
                    Authorization:
                        "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBleGFtcGxlLmNvbSIsInJvbGVzIjpbIkFETUlOIl0sImlhdCI6MTY5ODg4OTU0MiwiZXhwIjoxNjk4ODkzMTQyfQ.7uAZbjI1I5q1l3ttzZcgpuz3Jq0oQbgyWLw2i039Kxk",
                },
            }
        );
        console.log(res);
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
