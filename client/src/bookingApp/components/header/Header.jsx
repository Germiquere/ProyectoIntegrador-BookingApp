import React, { useState } from "react";
import { FaBars } from "react-icons/fa";

export const Header = () => {
    let Links = [
        { name: "HOME", link: "/" },
        { name: "PRODUCTS", link: "/" },
        { name: "CATEGORIES", link: "/" },
        { name: "CONTACT", link: "/" },
    ];
    let [open, setOpen] = useState(false);
    return (
        <header className="px-[5%] lg:px-[2%] flex items-center justify-around shadow-lg top-0 w-full bg-white sticky z-30 overflow-hidden">
            <div className="flex w-full justify-between md:pl-[20px] ">
                <div>
                    <a href="/">
                        <img
                            className="w-30 h-20 "
                            src="/src/assets/BikeMeNow_BlueAlpha.png"
                            alt=""
                        />
                    </a>
                </div>

                <div
                    onClick={() => setOpen(!open)}
                    className=" text-3xl absolute right-8 cursor-pointer lg:hidden h-full flex top-10 text-primary"
                >
                    <FaBars name={open ? "close" : "menu"}></FaBars>
                </div>

                <ul
                    className={`lg:flex lg:items-center lg:pb-0 pb-6 absolute lg:static  bg-white lg:z-auto lg:gap-4  z-50 left-0 w-full lg:w-auto md:pl-0 pl-9 transition-all duration-500 ease-in ${
                        open ? "top-[113px] " : "top-[-490px]"
                    }`}
                >
                    {Links.map((link) => (
                        <li
                            key={link.name}
                            className="lg:ml-2 text-l lg:my-0 my-6 font-bold "
                        >
                            <a
                                href={link.link}
                                className="text-neutral-800 hover:text-primary duration-500 "
                            >
                                {link.name}
                            </a>
                        </li>
                    ))}
                    <button
                        className="middle none center mr-2 rounded-full border border-primary py-3 px-6 font-sans text-xs font-bold uppercase text-primary transition-all hover:opacity-75 focus:ring focus:ring-tertiary active:opacity-[0.85] disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none"
                        data-ripple-dark="true"
                    >
                        Login
                    </button>
                    <button
                        className="middle none center mr-2 rounded-full bg-primary py-3 px-6 font-sans text-xs font-bold uppercase text-white shadow-sm  transition-all  hover:shadow-secondary  active:opacity-[0.85] active:shadow-none disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none"
                        data-ripple-light="true"
                    >
                        Register
                    </button>
                </ul>
            </div>
        </header>
    );
};

export default Header;
