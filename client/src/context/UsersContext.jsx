import { createContext, useContext, useEffect, useState } from "react";
import { getUser, getUsers, putUserRol } from "../api/users";
import { login, register, sendEmail } from "../api/auth";
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
        setFormState: setUsersFormState,
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
    const [loginbyBooking, setLoginbyBooking] = useState(false);
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
    const logoutTimer = () => {
        setTimeout(() => {
            // TODO: EJECUTAR LA FUNCION DE LOGOUT
            logout();
        }, 3600 * 1000);
    };
    const registerUser = async (user) => {
        setLoadingAuth(true);
        try {
            const data = await register(user);

            return data;
        } catch (err) {
            // if (err.status === 403) {
            //     navigate("/auth/login", { replace: true });
            // }
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
            const exp = Math.floor(Date.now() / 1000) + 3600;
            const accessToken = {
                token: data.token,
                exp,
            };
            localStorage.setItem("accessToken", JSON.stringify(accessToken));
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
    const editUserRole = async (user) => {
        // MANEJO EL ESTADO  DEL LOADING EN TRUE
        setLoadingAuth(true);
        try {
            const editedUser = await putUserRol(user);
            fetchUsersData();
            return editedUser;
        } catch (err) {
            if (err.status === 403) {
                navigate("/auth/login", { replace: true });
            }
            setErrorAuth(err);
        } finally {
            setLoadingAuth(false);
        }
    };
    const sendEmailAgain = async (email) => {
        // MANEJO EL ESTADO  DEL LOADING EN TRUE
        setLoadingAuth(true);
        try {
            const mail = await sendEmail(email);
            return mail;
        } catch (err) {
            if (err.status === 403) {
                navigate("/auth/login", { replace: true });
            }
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
            const time = token.exp - now;

            if (time > 0) {
                await fetchUserData();
                setTimeout(() => {
                    logout();
                }, time * 1000);
            } else {
                logout();
            }
        }
    };
    useEffect(() => {
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
                loginbyBooking,
                // METODOS
                onInputChange,
                onResetForm,
                setOpenEditUserModal,
                loginUser,
                fetchUserData,
                setUserEmail,
                setIsRegistered,
                registerUser,
                setErrorAuth,
                logout,
                fetchUsersData,
                editUserRole,
                setUsersFormState,
                sendEmailAgain,
                setLoginbyBooking,
            }}
        >
            {children}
        </UsersContext.Provider>
    );
}
