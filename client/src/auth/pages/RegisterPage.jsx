import React, { useState } from "react";
import { VscEyeClosed, VscEye } from "react-icons/vsc";
import { Link, useNavigate } from "react-router-dom";
import Section from "../../bookingApp/components/Section";
import { useUsersContext } from "../../context/UsersContext";
import { Loader } from "../../ui/Loader";

export const RegisterPage = () => {
    const {
        setIsRegistered,
        setUserEmail,
        registerUser,
        errorAuth,
        setErrorAuth,
        loadingAuth,
    } = useUsersContext();
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        nombre: "laura",
        apellido: "blandon",
        email: "joaquintbraicovich@gmail.com",
        password: "asdasd123",
        confirmPassword: "asdasd123",
    });

    const [fieldErrors, setFieldErrors] = useState({
        name: "",
        lname: "",
        email: "",
        password: "",
        confirmPassword: "",
    });

    const [showPassword, setShowPassword] = useState(false);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value,
        });
    };
    const handleErrors = () => {
        let hasError = false;
        const newFieldErrors = { ...fieldErrors };
        if (formData.nombre.length < 3 || /\d/.test(formData.nombre)) {
            newFieldErrors.name = "Ingresa un nombre válido.";
            hasError = true;
        } else {
            newFieldErrors.name = "";
            hasError = false;
        }

        if (formData.apellido.length < 3 || /\d/.test(formData.apellido)) {
            newFieldErrors.lname = "Ingresa un apellido válido.";
            hasError = true;
        } else {
            newFieldErrors.lname = "";
            hasError = false;
        }

        if (!/\S+@\S+\.\S+/.test(formData.email)) {
            newFieldErrors.email = "Ingresa un correo electrónico válido.";
            hasError = true;
        } else {
            newFieldErrors.email = "";
            hasError = false;
        }

        if (
            formData.password.length < 8 ||
            !/[A-Za-z]/.test(formData.password) ||
            !/\d/.test(formData.password)
        ) {
            newFieldErrors.password = "Debe contener 8 min y un número.";
            hasError = true;
        } else {
            newFieldErrors.password = "";
            hasError = false;
        }

        if (formData.confirmPassword !== formData.password) {
            newFieldErrors.confirmPassword = "La contraseña no coincide.";
            hasError = true;
        } else {
            newFieldErrors.confirmPassword = "";
            hasError = false;
        }

        setFieldErrors(newFieldErrors);
        return hasError;
    };

    const handleSubmit = async (e) => {
        setErrorAuth(false);
        e.preventDefault();
        if (!handleErrors()) {
            console.log(formData);
            const userRegistered = await registerUser(formData);
            if (userRegistered) {
                setIsRegistered(true);
                setUserEmail(formData.email);
                navigate("/auth/register/verification");
            }
        }
    };

    return (
        <Section>
            <form onSubmit={handleSubmit}>
                <div className="max-w-[1200px] mx-auto flex flex-col items-center gap-4">
                    <h2 className="text-primary font-bold text-2xl m-6">
                        Crear cuenta
                    </h2>
                    <div className="relative w-full  min-w-[250px] grid gap-3 grid-cols-1  sm:min-w-[500px] sm:grid-cols-2">
                        <div className="flex flex-col w-full">
                            <label className="font-semibold mb-2">
                                Nombre *
                            </label>
                            <input
                                id="name"
                                name="nombre"
                                type="text"
                                required
                                value={formData.nombre}
                                onChange={handleInputChange}
                                className="shadow-md rounded-lg peer h-full p-2 font-sans text-sm font-normal text-blue-gray-700 outline outline-0 transition-all  focus:outline-0  disabled:bg-blue-gray-50"
                                placeholder="John"
                            />
                            {fieldErrors.name && (
                                <span className="text-red-500 w-full text-xs pt-1">
                                    {fieldErrors.name}
                                </span>
                            )}
                        </div>
                        <div className="flex flex-col w-full">
                            <label className="font-semibold mb-2">
                                Apellido *
                            </label>
                            <input
                                id="lname"
                                name="apellido"
                                type="text"
                                required
                                className="shadow-md rounded-lg peer h-full  p-2 font-sans text-sm font-normal text-blue-gray-700 outline outline-0 transition-all  focus:outline-0  disabled:bg-blue-gray-50"
                                placeholder="Doe"
                                value={formData.apellido}
                                onChange={handleInputChange}
                            />
                            {fieldErrors.lname && (
                                <span className="text-red-500 w-full text-xs pt-1">
                                    {fieldErrors.lname}
                                </span>
                            )}
                        </div>
                    </div>
                    <div className="relative min-w-[250px] flex flex-col sm:min-w-[500px]">
                        <label className="font-semibold mb-2">
                            Correo electrónico *
                        </label>
                        <input
                            id="email-address"
                            name="email"
                            type="email"
                            required
                            className="shadow-md rounded-lg peer h-full  p-2 font-sans text-sm font-normal text-blue-gray-700 outline outline-0 transition-all  focus:outline-0  disabled:bg-blue-gray-50"
                            placeholder="BikeMeNow@mail.com"
                            value={formData.email}
                            onChange={handleInputChange}
                        />
                        {fieldErrors.email && (
                            <span className="text-red-500 w-full text-xs pt-1">
                                {fieldErrors.email}
                            </span>
                        )}
                    </div>
                    <div className="relative min-w-[250px] flex flex-col sm:min-w-[500px]">
                        <label className="font-semibold mb-2">
                            Contraseña *
                        </label>
                        <input
                            id="password"
                            name="password"
                            type={showPassword ? "text" : "password"}
                            required
                            className=" shadow-md rounded-lg peer h-full p-2 font-sans text-sm font-normal text-blue-gray-700 outline outline-0 transition-all  focus:outline-0  disabled:bg-blue-gray-50"
                            placeholder="********"
                            value={formData.password}
                            onChange={handleInputChange}
                        />
                        {fieldErrors.password && (
                            <span className="text-red-500 w-full text-xs pt-1">
                                {fieldErrors.password}
                            </span>
                        )}

                        <span
                            className="absolute  right-0 top-10 pr-3 cursor-pointer text-lg"
                            onClick={() => setShowPassword(!showPassword)}
                        >
                            {showPassword ? <VscEyeClosed /> : <VscEye />}
                        </span>
                    </div>
                    <div className="relative min-w-[250px] flex flex-col sm:min-w-[500px]">
                        <label className="font-semibold mb-2">
                            Confirmar contraseña *
                        </label>
                        <input
                            id="confirmPassword"
                            name="confirmPassword"
                            type={showPassword ? "text" : "password"}
                            required
                            className=" shadow-md rounded-lg peer h-full p-2 font-sans text-sm font-normal text-blue-gray-700 outline outline-0 transition-all  focus:outline-0  disabled:bg-blue-gray-50"
                            placeholder="********"
                            value={formData.confirmPassword}
                            onChange={handleInputChange}
                        />
                        {fieldErrors.confirmPassword && (
                            <span className="text-red-500 w-full text-xs pt-1">
                                {fieldErrors.confirmPassword}
                            </span>
                        )}
                    </div>
                    <div className="flex flex-col gap-4 pt-6 items-center">
                        {errorAuth?.status === 409 && (
                            <p className="text-red-500">
                                Ya existe un usuario registrado con ese correo
                                electrónico
                            </p>
                        )}
                        <button
                            type="submit"
                            className="max-w-[260px] middle none center rounded-full bg-primary py-3 px-20 font-sans text-xs font-bold uppercase text-white shadow-sm  transition-all  hover:shadow-secondary  active:opacity-[0.85] active:shadow-none disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none"
                            data-ripple-light="true"
                            disabled={loadingAuth}
                        >
                            {loadingAuth ? (
                                <div className="flex gap-1 items-center">
                                    <Loader />
                                    <p>Creando...</p>
                                </div>
                            ) : (
                                "Crear cuenta"
                            )}
                        </button>
                        <div className="flex gap-2">
                            <p>¿Ya tienes una cuenta?</p>
                            <Link to="/auth/login" className="text-primary">
                                Iniciar sesión
                            </Link>
                        </div>
                    </div>
                </div>
            </form>
        </Section>
    );
};
