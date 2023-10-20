import { Link } from "react-router-dom";
import { FaFacebookF, FaInstagram, FaYoutube, FaTiktok } from "react-icons/fa";

export const Footer = () => {
    return (
        <footer className="  bg-tertiary ">
            <div className="flex flex-col p-2 gap-4 md:flex-row items-center max-w-[1200px] mx-auto justify-between">
                <div className="flex flex-col items-center">
                    <Link to="/">
                        <img
                            className="w-full h-[50px]"
                            src="/src/assets/BikeMeNow_BlueAlpha.png"
                            alt=""
                        />
                    </Link>
                </div>

                <div className="flex gap-4 pr-4 justify-center text-[20px] text-primary">
                    <Link
                        href="https://www.facebook.com/"
                        target="_blank"
                        className="hover:text-gray-400 duration-500"
                    >
                        <FaFacebookF />
                    </Link>
                    <Link
                        href="https://www.instagram.com/"
                        target="_blank"
                        className="hover:text-gray-400 duration-500"
                    >
                        <FaInstagram />
                    </Link>
                    <Link
                        href="https://www.youtube.com/"
                        target="_blank"
                        className="hover:text-gray-400 duration-500"
                    >
                        <FaYoutube />
                    </Link>
                    <Link
                        href="https://www.tiktok.com/"
                        target="_blank"
                        className="hover:text-gray-400 duration-500"
                    >
                        <FaTiktok />
                    </Link>
                </div>
            </div>
            <div className="flex items-center justify-center bg-primary w-full  text-white text-xs text-center h-10  font-bold  md:text-sm">
                © Copyright 2023 BikeMeNow. All Rights Reserved
            </div>
        </footer>
    );
};
