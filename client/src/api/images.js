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
                        "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBleGFtcGxlLmNvbSIsInJvbGVzIjpbIkFETUlOIl0sImlhdCI6MTY5ODg2NzY1NiwiZXhwIjoxNjk4ODcxMjU2fQ.t1g39PM2gs6Fy-7mzDsLlhhDEtLyHPBENAw69kEg-ek",
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
                        "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBleGFtcGxlLmNvbSIsInJvbGVzIjpbIkFETUlOIl0sImlhdCI6MTY5ODg2NzY1NiwiZXhwIjoxNjk4ODcxMjU2fQ.t1g39PM2gs6Fy-7mzDsLlhhDEtLyHPBENAw69kEg-ek",
                },
            }
        );
        console.log(res);
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
        throw new Error("Error al borrar las imagenes");
    }
};
