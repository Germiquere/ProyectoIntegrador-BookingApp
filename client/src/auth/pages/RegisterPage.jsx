import React, { useState } from "react";
import { VscEyeClosed, VscEye } from "react-icons/vsc";
import { Link } from "react-router-dom";
import Section from "../../bookingApp/components/Section";


export const RegisterPage = () => {
    const [formData, setFormData] = useState({
        name: "",
        lname: "",
        email: "",
        password: "",
        confirmPassword: "",
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

    const handleSubmit = (e) => {
        e.preventDefault();

        const newFieldErrors = { ...fieldErrors };

        if (formData.name.length < 3 || /\d/.test(formData.name)) {
            newFieldErrors.name = "Ingresa un nombre válido.";
        } else {
            newFieldErrors.name = "";
        }

        if (formData.lname.length < 3 || /\d/.test(formData.lname)) {
            newFieldErrors.lname = "Ingresa un apellido válido.";
        } else {
            newFieldErrors.lname = "";
        }

        if (!/\S+@\S+\.\S+/.test(formData.email)) {
            newFieldErrors.email = "Ingresa un correo electrónico válido.";
        } else {
            newFieldErrors.email = "";
        }

        if (formData.password.length < 8 || !/[A-Za-z]/.test(formData.password) || !/\d/.test(formData.password)) {
            newFieldErrors.password = "Debe contener 8 min y un número.";
        } else {
            newFieldErrors.password = "";
        }

        if (formData.confirmPassword !== formData.password) {
            newFieldErrors.confirmPassword = "La contraseña no coincide.";
        } else {
            newFieldErrors.confirmPassword = "";
        }

        setFieldErrors(newFieldErrors);

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
                            <label className="font-semibold mb-2">Nombre *</label>
                            <input
                                id="name"
                                name="name"
                                type="text"
                                required
                                value={formData.name}
                                onChange={handleInputChange}
                                className="shadow-md rounded-lg peer h-full p-2 font-sans text-sm font-normal text-blue-gray-700 outline outline-0 transition-all  focus:outline-0  disabled:bg-blue-gray-50"
                                placeholder="John"/>
                            {fieldErrors.name && <span className="text-red-500 w-full text-xs pt-1">{fieldErrors.name}</span>}
                        </div>
                        <div className="flex flex-col w-full">
                            <label className="font-semibold mb-2">Apellido *</label>
                            <input
                                id="lname"
                                name="lname"
                                type="text"
                                required
                                className="shadow-md rounded-lg peer h-full  p-2 font-sans text-sm font-normal text-blue-gray-700 outline outline-0 transition-all  focus:outline-0  disabled:bg-blue-gray-50"
                                placeholder="Doe"
                                value={formData.lname}
                                onChange={handleInputChange}
                            />
                            {fieldErrors.lname && <span className="text-red-500 w-full text-xs pt-1">{fieldErrors.lname}</span>}
                        </div>
                    </div>
                    <div className="relative min-w-[250px] flex flex-col sm:min-w-[500px]">
                        <label className="font-semibold mb-2">Correo electrónico *</label>
                        <input
                            id="email-address"
                            name="email"
                            type="email"
                            required
                            className="shadow-md rounded-lg peer h-full  p-2 font-sans text-sm font-normal text-blue-gray-700 outline outline-0 transition-all  focus:outline-0  disabled:bg-blue-gray-50"
                            placeholder="BikeMeNow@mail.com"
                            value={formData.email}
                            onChange={handleInputChange}/>
                        {fieldErrors.email && <span className="text-red-500 w-full text-xs pt-1">{fieldErrors.email}</span>}
                    </div>
                    <div className="relative min-w-[250px] flex flex-col sm:min-w-[500px]">
                        <label className="font-semibold mb-2">Contraseña *</label>
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
                        {fieldErrors.password && <span className="text-red-500 w-full text-xs pt-1">{fieldErrors.password}</span>}

                        <span
                            className="absolute  right-0 top-10 pr-3 cursor-pointer text-lg"
                            onClick={() => setShowPassword(!showPassword)}>
                                {showPassword ? <VscEyeClosed /> : <VscEye />}
                        </span>
                    </div>
                    <div className="relative min-w-[250px] flex flex-col sm:min-w-[500px]">
                        <label className="font-semibold mb-2">Confirmar contraseña *</label>
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
                        {fieldErrors.confirmPassword && <span className="text-red-500 w-full text-xs pt-1">{fieldErrors.confirmPassword}</span>}
                    </div>
                    <div className="flex flex-col gap-4 pt-6">
                        <button
                            type="submit"
                            className="middle none center rounded-full bg-primary py-3 px-20 font-sans text-xs font-bold uppercase text-white shadow-sm  transition-all  hover:shadow-secondary  active:opacity-[0.85] active:shadow-none disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none"
                            data-ripple-light="true">
                                <Link to={"/auth/register/verification"}>
                                    Crear cuenta
                                </Link>
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





