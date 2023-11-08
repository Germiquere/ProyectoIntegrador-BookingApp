import React from "react";

export const SentEmailModal = ({ setOpenSendEmail }) => {
    return (
        <>
            <div
                className={` w-96 rounded-xl  overflow-hidden bg-white  fixed top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2  mx-auto transition-opacity duration-200 z-50 `}
            >
                {/* <div className="bg-primary w-full h-44 flex justify-center items-center text-white">
                 
                </div> */}
                <div className="flex flex-col items-center justify-around p-5 text-center gap-5">
                    <h2 className="text-2xl font-semibold">
                        Email reenviado con éxito
                    </h2>

                    <button
                        className="middle none center mr-3 rounded-full bg-primary py-3 px-6 font-sans text-xs font-bold uppercase text-white shadow-sm  transition-all  hover:shadow-secondary  active:opacity-[0.85] active:shadow-none disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none"
                        data-ripple-light="true"
                        onClick={() => setOpenSendEmail(false)}
                    >
                        ACEPTAR
                    </button>
                </div>
            </div>
            <div
                className={`fixed top-0 left-0 w-full h-full bg-black bg-opacity-30 transition-opacity duration-200 z-40`}
            ></div>
        </>
    );
};
