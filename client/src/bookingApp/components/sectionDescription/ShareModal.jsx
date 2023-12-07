import { Tooltip } from "@mui/material";
import {
    BsXLg,
    BsCurrencyDollar,
    BsCloudUpload,
    BsX,
    BsFacebook,
    BsWhatsapp,
    BsTwitter,
    BsEnvelope,
} from "react-icons/bs";
import { useLocation } from "react-router-dom";

import {
    FacebookShareButton,
    LinkedinShareButton,
    TwitterShareButton,
    EmailShareButton,
    WhatsappShareButton,
} from "react-share";
export const ShareModal = ({ setOpenShareModal, bikeById }) => {
    const currentUrl = `${window.location.href}${window.location.pathname}`;
    return (
        <>
            <div
                className={` md:rounded-xl h-screen md:h-auto overflow-hidden overflow-y-auto bg-white  fixed top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2   w-full md:max-w-[700px] mx-auto transition-opacity duration-200 z-50 `}
            >
                <div className="flex flex-col gap-2 p-3 ">
                    <div className="flex justify-between items-center">
                        <h3 className="text-lg sm:text-2xl font-semibold ">
                            Compartí este producto
                        </h3>
                        <Tooltip title="Cerrar">
                            <button
                                className="flex  items-center justify-center middle none center rounded-full  h-10 w-10 font-sans text-xs font-bold uppercase  transition-all hover:bg-blackOpacity1 active:bg-tertiary disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none "
                                data-ripple-dark="true"
                                onClick={() => {
                                    setOpenShareModal(false);
                                }}
                            >
                                <BsXLg className="text-lg" />
                            </button>
                        </Tooltip>
                    </div>
                    <div className="flex gap-2 items-center">
                        <div className="min-w-[64px] min-h-[64px] rounded-lg overflow-hidden border border-gray-300 ">
                            <img
                                className="h-16 w-16"
                                src={bikeById.imagenes[0].url}
                                alt={bikeById.nombre}
                            />
                        </div>
                        <p className="hidden sm:block">
                            {bikeById.nombre.length > 165
                                ? bikeById.nombre.slice(0, 165) + "..."
                                : bikeById.nombre}
                        </p>
                        <p className="sm:hidden">
                            {bikeById.nombre.length > 55
                                ? bikeById.nombre.slice(0, 55) + "..."
                                : bikeById.nombre}
                        </p>
                    </div>
                    {/* TEXT AREA */}
                    {/* <div className="flex flex-col gap-3 flex-1">
                        <div>
                            <label className="text-base font-semibold mb-2">
                                Añadí una descripcion a tu publicación:
                            </label>
                            <textarea
                                className=" p-2 w-full outline outline-0 shadow-md border-[1px] rounded-xl overflow-hidden border-gray-100 "
                                style={{ resize: "none" }}
                                placeholder="Miren este increible producto"
                                rows={4}
                                // value={descripcion}
                                name="descripcion"
                                // onChange={handleInputChange}
                            ></textarea>
                        </div>
                    </div> */}
                    <div className="grid grid-cols-1 gap-2 md:grid-cols-2">
                        <FacebookShareButton url={currentUrl}>
                            <div
                                className=" flex gap-2 items-center justify-center middle none center rounded-md   py-3 px-3 sm:px-6 font-sans text-xs font-bold uppercase  text-primary transition-all hover:bg-tertiary active:bg-tertiary disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none border border-gray-300 "
                                data-ripple-dark="true"
                            >
                                <BsFacebook className="text-lg" />

                                <p>facebook</p>
                            </div>
                        </FacebookShareButton>
                        <WhatsappShareButton url={currentUrl}>
                            <div
                                className="cursor-pointer flex gap-2 items-center justify-center middle none center rounded-md   py-3 px-3 sm:px-6 font-sans text-xs font-bold uppercase text-primary transition-all hover:bg-tertiary active:bg-tertiary disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none border border-gray-300 "
                                data-ripple-dark="true"
                            >
                                <BsWhatsapp className="text-lg" />

                                <p>whatsapp</p>
                            </div>
                        </WhatsappShareButton>
                        <TwitterShareButton url={currentUrl}>
                            <div
                                className="cursor-pointer flex gap-2 items-center justify-center middle none center rounded-md   py-3 px-3 sm:px-6 font-sans text-xs font-bold uppercase text-primary transition-all hover:bg-tertiary active:bg-tertiary disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none border border-gray-300 "
                                data-ripple-dark="true"
                            >
                                <BsTwitter className="text-lg" />

                                <p>twtitter</p>
                            </div>
                        </TwitterShareButton>
                        <EmailShareButton url={currentUrl}>
                            <div
                                className=" cursor-pointer flex gap-2 items-center justify-center middle none center rounded-md   py-3 px-3 sm:px-6 font-sans text-xs font-bold uppercase text-primary transition-all hover:bg-tertiary active:bg-tertiary disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none border border-gray-300 "
                                data-ripple-dark="true"
                            >
                                <BsEnvelope className="text-lg" />

                                <p>correo</p>
                            </div>
                        </EmailShareButton>
                    </div>
                </div>
            </div>
            <div
                className={`fixed top-0 left-0 w-full h-full bg-black bg-opacity-70 transition-opacity duration-200 z-40`}
            ></div>
        </>
    );
};
