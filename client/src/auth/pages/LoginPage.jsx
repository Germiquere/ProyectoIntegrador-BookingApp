import { Link, useLocation, useNavigate } from "react-router-dom";
import Section from "../../bookingApp/components/Section";
import React, { useState } from "react";
import { VscEyeClosed, VscEye } from "react-icons/vsc";
import { useForm } from "../../hooks/useForm";
import { useUsersContext } from "../../context/UsersContext";
import { Loader } from "../../ui/Loader";
const formData = {
    email: "admin@example.com",
    password: "contraseña",
};
// const formData = {
//     email: "",
//     password: "",
// };
export const LoginPage = () => {
    const location = useLocation();
    const {
        formState,
        onInputChange,
        onResetForm,
        setFormState,
        email,
        password,
    } = useForm(formData);
    const { loginUser, loadingUser, fetchUserData, userData, errorAuth } =
        useUsersContext();
    const [showPassword, setShowPassword] = useState(false);
    const navigate = useNavigate();
    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            await loginUser(formState);
            const user = await fetchUserData();
            if (user) {
                navigate(location.state.prevUrl + location.state.prevSearch);
            }
        } catch (error) {
            // Maneja errores si ocurren durante la autenticación o la obtención de datos
        }
    };
    return (
        <>
            <Section>
                <form
                    className="max-w-[1200px] mx-auto flex flex-col items-center gap-11"
                    onSubmit={handleSubmit}
                >
                    <h2 className="text-primary font-bold text-2xl">
                        Iniciar sesión
                    </h2>

                    <div className="relative h-11  min-w-[230px] flex flex-col sm:min-w-[500px] ">
                        <label className="font-semibold mb-2">
                            Correo electronico
                        </label>
                        <input
                            id="email-address"
                            name="email"
                            type="email"
                            className="shadow-md rounded-lg peer h-full  p-2 font-sans text-sm font-normal text-blue-gray-700 outline outline-0 transition-all  focus:outline-0  disabled:bg-blue-gray-50"
                            placeholder="BikeMeNow@gmail.com"
                            value={email}
                            onChange={onInputChange}
                        />
                    </div>
                    <div className="relative h-11 min-w-[230px] flex flex-col sm:min-w-[500px]">
                        <label className="font-semibold mb-2">Contraseña</label>
                        <input
                            id="password"
                            name="password"
                            type={showPassword ? "text" : "password"}
                            className=" shadow-md rounded-lg peer h-full p-2 font-sans text-sm font-normal text-blue-gray-700 outline outline-0 transition-all  focus:outline-0  disabled:bg-blue-gray-50"
                            placeholder="bikemenoW123"
                            value={password}
                            onChange={onInputChange}
                        />
                        <span
                            className="absolute  right-0 top-10 pr-3 cursor-pointer"
                            onClick={() => setShowPassword(!showPassword)}
                        >
                            {showPassword ? <VscEyeClosed /> : <VscEye />}
                        </span>
                    </div>
                    <div className="flex flex-col gap-4 pt-6">
                        {errorAuth?.status === 403 && (
                            <p className="text-red-500 text-center">
                                Cuenta no encontrada
                            </p>
                        )}

                        <button
                            className="flex justify-center middle none center rounded-full bg-primary py-3 px-20 font-sans text-xs font-bold uppercase text-white shadow-sm  transition-all  hover:shadow-secondary  active:opacity-[0.85] active:shadow-none disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none"
                            data-ripple-light="true"
                            disabled={loadingUser}
                        >
                            {loadingUser ? (
                                <div className="flex justify-center items-center">
                                    <Loader />
                                    <p>Ingresando ...</p>
                                </div>
                            ) : (
                                "Ingresar"
                            )}
                        </button>
                        <div className="flex gap-2">
                            <p>¿Aún no tienes cuenta?</p>
                            <Link to="/auth/register" className="text-primary">
                                Registrate
                            </Link>
                        </div>
                    </div>
                </form>
            </Section>
        </>
    );
};
