import React, { useState } from "react";
import { FaBars } from "react-icons/fa";
import { Link } from "react-router-dom";

export const Header = () => {
    let [open, setOpen] = useState(false);
    return (
        <header className="px-[5%] lg:px-[2%] shadow-lg top-0 w-full bg-white sticky z-30">
            <div className="flex items-center justify-between max-w-[1200px] mx-auto h-[64px] ">
                <div>
                    <Link to="/">
                        <img
                            className="w-full h-[50px] "
                            src="https://res.cloudinary.com/djslo5b3u/image/upload/v1698971497/BikeMeNow_BlueAlpha_svidg9.png"
                            alt=""
                        />
                    </Link>
                </div>
            </div>
        </header>
    );
};

export default Header;
