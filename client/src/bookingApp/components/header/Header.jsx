import React, { useState } from "react";
import { IoIosArrowDown } from "react-icons/io";
import { Link } from "react-router-dom";
import MenuHamburger from "./MenuHamburger";
import { useUsersContext } from "../../../context/UsersContext";

export const Header = () => {
    const { userData, isAuthenticated, rol, logout } = useUsersContext();
    const [isDropdownOpen, setIsDropdownOpen] = useState(false);
    const toggleDropdown = () => {
        setIsDropdownOpen(!isDropdownOpen);
    };

    const renderUserLinks = () => {
        if (isAuthenticated) {
            return (
                <div
                    className="flex items-center relative"
                    onClick={toggleDropdown}
                >
                    <p className="cursor-pointer w-10 h-10 bg-primary rounded-full flex items-center justify-center text-white text-md hover:bg-secondary mr-2">
                        {userData.nombre.charAt(0).toUpperCase()}
                    </p>
                    <p className="cursor-pointer text-black mr-2 font-semibold">
                        {userData.nombre.charAt(0).toUpperCase() +
                            userData.nombre.slice(1)}
                    </p>
                    <div className="cursor-pointer">
                        <IoIosArrowDown />
                    </div>

                    {isDropdownOpen && (
                        <div
                            className={`absolute top-[55px] right-0 bg-white border border-gray-300 p-2 rounded-lg shadow-lg transition-opacity  ${
                                isDropdownOpen ? "opacity-100" : "opacity-0"
                            }duration-300 ease-in-out`}
                        >
                            <div className="flex flex-col items-center">
                                {rol === "admin" && (
                                    <Link to="/admin">
                                        <button
                                            className="min-w-[180px] middle none center  rounded-full border border-primary py-3 px-6 font-sans text-xs font-bold uppercase text-primary transition-all hover:opacity-75 focus:ring focus:ring-tertiary active:opacity-[0.85] disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none "
                                            data-ripple-dark="true"
                                        >
                                            Administracion
                                        </button>
                                    </Link>
                                )}
                                {rol === "user" && (
                                    <Link to="/favorites">
                                        <button
                                            className="min-w-[180px] middle none center  rounded-full border border-primary py-3 px-6 font-sans text-xs font-bold uppercase text-primary transition-all hover:opacity-75 focus:ring focus:ring-tertiary active:opacity-[0.85] disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none mt-2"
                                            data-ripple-dark="true"
                                        >
                                            Favoritos
                                        </button>
                                    </Link>
                                )}

                                <button
                                    className="min-w-[180px] middle none center  rounded-full border border-primary py-3 px-6 font-sans text-xs font-bold uppercase text-primary transition-all hover:opacity-75 focus:ring focus:ring-tertiary active:opacity-[0.85] disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none mt-2"
                                    data-ripple-dark="true"
                                    onClick={logout}
                                >
                                    Cerrar sesión
                                </button>
                            </div>
                        </div>
                    )}
                </div>
            );
        } else {
            return (
                <div>
                    <MenuHamburger />
                </div>
            );
        }
    };

    return (
        <header className="px-[5%] lg:px-[2%] shadow-lg top-0 w-full bg-white sticky z-30 ">
            <div className="flex items-center justify-between max-w-[1200px] mx-auto  ">
                <div>
                    <Link to="/">
                        <img
                            className="w-full h-[50px] "
                            src="/src/assets/BikeMeNow_BlueAlpha.png"
                            alt=""
                        />
                    </Link>
                </div>
                {renderUserLinks()}
            </div>
        </header>
    );
};

export default Header;
