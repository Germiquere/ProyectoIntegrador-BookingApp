import { createContext, useContext, useEffect, useState } from "react";
import { getUser, getUsers } from "../api/users";
import { login } from "../api/auth";
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
    // -----------USUARIO-----------
    // FUNCION PARA OBTENER EL USUARIO LOGUEADO
    const fetchUserData = async () => {
        // MANEJO EL ESTADO  DEL LOADING EN TRUE
        setLoadingUsers(true);
        try {
            const data = await getUser();
            setUserData(data);
        } catch (err) {
            if (err.status === 403) {
                navigate("/auth/login", { replace: true });
            }
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
        localStorage.removeItem("token");
        // TODO: QUITAR TAMBIEN LA INFORMACION DEL USUARIO
        setUserData([]);
        navitage("/");
    };
    // FUNCION DEL TIMER DEL TOKEN
    const logoutTimer = () => {
        setTimeout(() => {
            // TODO: EJECUTAR LA FUNCION DE LOGOUT
            logout();
            alert("token expired");
        }, 3600 * 1000);
    };
    const loginUser = async (user) => {
        // MANEJO EL ESTADO  DEL LOADING EN TRUE
        setLoadingAuth(true);
        try {
            const data = await login(user);
            localStorage.setItem("token", data.token);
            logoutTimer();
        } catch (err) {
            // if (err.status === 403) {
            //     navigate("/auth/login", { replace: true });
            // }
            setErrorAuth(err);
        } finally {
            setLoadingAuth(false);
        }
    };

    // SOLO HACER EL FETCH DE LOS USUARIOS SI HAY UN USUARIO LOGUEADO Y SI EL ROLL ES ADMIN
    useEffect(() => {
        fetchUsersData();
    }, []);
    // SOLO HACER EL FETCH DEL USUARIO SI ESTA LOGUEADO,ES DECIR AUTENTICADO
    const user = {
        email: "admin@example.com",
        password: "contraseña",
    };
    useEffect(() => {
        // fetchUserData();
        loginUser(user);
    }, []);
    return (
        <UsersContext.Provider
            value={{
                // PROPIEDADES
                usersData,
                loadingUsers,
                errorUsers,
                usersFormState,
                openEditUserModal,
                // METODOS
                onInputChange,
                onResetForm,
                setFormState,
                setOpenEditUserModal,
            }}
        >
            {children}
        </UsersContext.Provider>
    );
}
