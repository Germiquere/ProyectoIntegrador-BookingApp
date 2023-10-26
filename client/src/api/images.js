export const postImage = async (file) => {
    if (!file) throw new Error("No hay ningúna imagen para subir");
    const formData = new FormData();
    formData.append("bucketName", "bikemenowbucket");
    formData.append("filePath", "bikemenowImages/");
    formData.append("file", file);
    try {
        const res = await fetch(`http://localhost:8080/s3/uploadFile`, {
            method: "POST",
            body: formData,
        });
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
        throw new Error(error.message);
    }
};
//TODO: VER COMO TERMINA SIENDO ESTO
export const deleteImage = async (id) => {
    try {
        const res = await fetch(`URL/${id}`, {
            method: "DELETE",
        });
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
