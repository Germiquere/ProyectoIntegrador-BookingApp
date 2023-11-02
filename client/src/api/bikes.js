export const getBikes = async () => {
    try {
        // TODO: PASAR EL ENDPOINT DE NUESTRA API POR MEDIO DE LAS VARIABLES DE ENTORNO

        // const res = await fetch(import.meta.env.VITE_URL + "/");
        const res = await fetch("http://localhost:8080/bike-me-now/bicicletas");
        if (!res.ok) {
            // Crear un objeto de error personalizado con estado y ok
            const error = new Error("Error en la solicitud Get");
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
// export const getBikes = async () => {
//     try {
//         // TODO: PASAR EL ENDPOINT DE NUESTRA API POR MEDIO DE LAS VARIABLES DE ENTORNO
//         const token =
//             "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBleGFtcGxlLmNvbSIsInJvbGVzIjpbIkFETUlOIl0sImlhdCI6MTY5ODgwMzA2MiwiZXhwIjoxNjk4ODA2NjYyfQ.-dTpGNf8beFfU7o2gC2XjP3GT-3RezJWB0aNR69Q2JE"; // Reemplaza "tu_token_aqui" con el token real que desees usar.

//         const headers = new Headers({
//             Authorization: `Bearer ${token}`, // Agregar el token en el encabezado "Authorization"
//         });

//         const requestOptions = {
//             method: "GET",
//             headers: headers,
//         };

//         // Reemplaza la URL con tu URL real
//         const res = await fetch(
//             "http://localhost:8080/bike-me-now/bicicletas",
//             requestOptions
//         );

//         if (!res.ok) {
//             // Crear un objeto de error personalizado con estado y ok
//             const error = new Error("Error en la solicitud Get");
//             error.status = res.status;
//             error.ok = false;
//             throw error;
//         }
//         const data = await res.json();
//         return data;
//     } catch (error) {
//         throw error;
//     }
// };

export const getBikeById = async (id) => {
    try {
        // TODO: PASAR EL ENDPOINT DE NUESTRA API POR MEDIO DE LAS VARIABLES DE ENTORNO

        // const res = await fetch(import.meta.env.VITE_URL + "/");
        const res = await fetch(
            `http://localhost:8080/bike-me-now/bicicletas/${id}`
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
export const postBike = async (bike) => {
    try {
        // PASAR EL ENDPOINT DE NUESTRA API
        const res = await fetch(
            "http://localhost:8080/bike-me-now/api/bicicletas",
            {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    Authorization:
                        "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBleGFtcGxlLmNvbSIsInJvbGVzIjpbIkFETUlOIl0sImlhdCI6MTY5ODg5NDc4MiwiZXhwIjoxNjk4ODk4MzgyfQ.BAyceQh4OhYiNTxXAUNwjkff3FU5ZUXap2nzkpg-i8E",
                },

                body: JSON.stringify(bike),
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
        // throw new Error("Error al cargar el producto");
        throw error;
    }
};
export const deleteBike = async (id) => {
    try {
        const res = await fetch(
            `http://localhost:8080/bike-me-now/api/bicicletas/${id}`,
            {
                method: "DELETE",
                headers: {
                    // "Content-Type": "application/json",
                    Authorization:
                        "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBleGFtcGxlLmNvbSIsInJvbGVzIjpbIkFETUlOIl0sImlhdCI6MTY5ODg5NDc4MiwiZXhwIjoxNjk4ODk4MzgyfQ.BAyceQh4OhYiNTxXAUNwjkff3FU5ZUXap2nzkpg-i8E",
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
export const updateBike = async (bike) => {
    console.log(bike);
    try {
        // PASAR EL ENDPOINT DE NUESTRA API
        const res = await fetch(
            "http://localhost:8080/bike-me-now/api/bicicletas",
            {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                    Authorization:
                        "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBleGFtcGxlLmNvbSIsInJvbGVzIjpbIkFETUlOIl0sImlhdCI6MTY5ODg5NDc4MiwiZXhwIjoxNjk4ODk4MzgyfQ.BAyceQh4OhYiNTxXAUNwjkff3FU5ZUXap2nzkpg-i8E",
                },
                body: JSON.stringify(bike),
            }
        );
        console.log(res.status);
        if (!res.ok) {
            // Crear un objeto de error personalizado con estado y ok
            const error = new Error("Error en la solicitud PUT");
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
