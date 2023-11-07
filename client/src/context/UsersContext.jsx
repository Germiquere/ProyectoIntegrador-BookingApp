import { createContext, useContext, useEffect, useState } from "react";
import { getUser, getUsers } from "../api/users";
import { login, register } from "../api/auth";
import { useForm } from "../hooks/useForm";
import { useNavigate } from "react-router-dom";

export const UsersContext = createContext();
export const useUsersContext = () => {
    return useContext(UsersContext);
};
const usersFormData = {
    usuarioId: "",
    nombre: "",
    apellido: "",
    email: "",
    rol: "",
};
export function UsersProvider({ children }) {
    const navitage = useNavigate();
    // USUARIOS
    const [usersData, setUsersData] = useState([]);
    const [loadingUsers, setLoadingUsers] = useState(false);
    const [errorUsers, setErrorUsers] = useState(null);
    const [openEditUserModal, setOpenEditUserModal] = useState(false);
    const {
        formState: usersFormState,
        onInputChange,
        onResetForm,
        setFormState,
    } = useForm(usersFormData);
    // USUARIO
    const [userData, setUserData] = useState([]);
    const [loadingUser, setLoadingUser] = useState(false);
    const [errorUser, setErrorUser] = useState(null);

    // AUTH
    const [loadingAuth, setLoadingAuth] = useState(false);
    const [errorAuth, setErrorAuth] = useState(null);
    const [authUser, setAuthUser] = useState({
        isAuthenticated: false,
        rol: "",
    });
    // REGISTER
    const [userEmail, setUserEmail] = useState("");
    const [isRegistered, setIsRegistered] = useState(false);
    const { isAuthenticated, rol } = authUser;

    // -----------USUARIO-----------
    // FUNCION PARA OBTENER EL USUARIO LOGUEADO
    const fetchUserData = async () => {
        // MANEJO EL ESTADO  DEL LOADING EN TRUE
        setLoadingUser(true);
        try {
            const data = await getUser();
            setUserData(data);
            // SETEO EL ROL Y QUE ESTA AUTENTICADO
            setAuthUser({
                isAuthenticated: true,
                rol: data.rol,
            });
            return data;
        } catch (err) {
            // if (err.status === 403) {
            //     navigate("/auth/login", { replace: true });
            // }
            console.log("estoy por tirar un error al loguear");
            setErrorUser(err);
        } finally {
            setLoadingUser(false);
        }
    };
    // -----------USUARIOS-----------
    // FUNCION PARA OBTENER TODOS LOS USUARIOS
    const fetchUsersData = async () => {
        // MANEJO EL ESTADO  DEL LOADING EN TRUE
        setLoadingUsers(true);
        try {
            const data = await getUsers();
            setUsersData(data);
        } catch (err) {
            if (err.status === 403) {
                navigate("/auth/login", { replace: true });
            }
            setErrorUsers(err);
        } finally {
            setLoadingUsers(false);
        }
    };
    // --------AUTH--------
    // FUNCION PARA LOGOUT DEL USUARIO
    const logout = () => {
        localStorage.removeItem("accessToken");
        // TODO: QUITAR TAMBIEN LA INFORMACION DEL USUARIO
        setUserData([]);
        setAuthUser({
            isAuthenticated: false,
            rol: "",
        });
        navitage("/");
    };
    // FUNCION DEL TIMER DEL TOKEN
    // const logoutTimer = () => {
    //     setTimeout(() => {
    //         // TODO: EJECUTAR LA FUNCION DE LOGOUT
    //         logout();
    //         alert("token expired");
    //     }, 300 * 1000);
    // };
    const registerUser = async (user) => {
        setLoadingAuth(true);
        try {
            const data = await register(user);
            console.log(data);
            return data;
        } catch (err) {
            // if (err.status === 403) {
            //     navigate("/auth/login", { replace: true });
            // }
            console.log(err.status);
            setErrorAuth(err);
        } finally {
            setLoadingAuth(false);
        }
    };
    const loginUser = async (user) => {
        // MANEJO EL ESTADO  DEL LOADING EN TRUE
        setLoadingAuth(true);
        try {
            const data = await login(user);
            const exp = Math.floor(Date.now() / 1000) + 300;
            const accessToken = {
                token: data.token,
                exp,
            };
            localStorage.setItem("accessToken", JSON.stringify(accessToken));
            // logoutTimer();
        } catch (err) {
            // if (err.status === 403) {
            //     navigate("/auth/login", { replace: true });
            // }
            setErrorAuth(err);
        } finally {
            setLoadingAuth(false);
        }
    };
    // FUNCION PARA VER EN CUANTO TIEMPO EXPIRA EL TOKEN
    const tokenExpTime = async () => {
        const token = JSON.parse(localStorage.getItem("accessToken"));
        const now = Math.floor(Date.now() / 1000);

        if (token) {
            console.log("hay token");
            const time = token.exp - now;
            console.log(token.exp);
            console.log(now);
            console.log(time);

            if (time > 0) {
                await fetchUserData();
                setTimeout(() => {
                    logout();
                    console.log("token expired");
                }, time * 1000);
            } else {
                console.log("hice el logout sin estar con tiempo");
                logout();
            }
        }
    };
    // SOLO HACER EL FETCH DE LOS USUARIOS SI HAY UN USUARIO LOGUEADO Y SI EL ROLL ES ADMIN
    // useEffect(() => {
    //     fetchUsersData();
    // }, []);
    // SOLO HACER EL FETCH DEL USUARIO SI ESTA LOGUEADO,ES DECIR AUTENTICADO
    const user = {
        email: "admin@example.com",
        password: "contraseña",
    };
    useEffect(() => {
        // fetchUserData();
        // loginUser(user);
        tokenExpTime();
    }, []);
    return (
        <UsersContext.Provider
            value={{
                // PROPIEDADES
                loadingUser,
                userData,
                errorUser,
                usersData,
                loadingUsers,
                errorUsers,
                usersFormState,
                openEditUserModal,
                isAuthenticated,
                rol,
                errorAuth,
                userEmail,
                isRegistered,
                loadingAuth,
                // METODOS
                onInputChange,
                onResetForm,
                setFormState,
                setOpenEditUserModal,
                loginUser,
                fetchUserData,
                setUserEmail,
                setIsRegistered,
                registerUser,
                setErrorAuth,
                logout,
            }}
        >
            {children}
        </UsersContext.Provider>
    );
}
