import Section from "../../bookingApp/components/Section";
import { Link } from "react-router-dom";
import { useUsersContext } from "../../context/UsersContext";
import { SentEmailModal } from "../components/verification/SentEmailModal";
import { useState } from "react";
import { Loader } from "../../ui/Loader";

export const VerificationRegisterPage = () => {
    const { userEmail, setIsRegistered, sendEmailAgain, loadingAuth } =
        useUsersContext();
    const [openSendEmail, setOpenSendEmail] = useState(false);
    const handleSendEmail = async () => {
        const sentEmail = await sendEmailAgain(userEmail);
        if (sentEmail) {
            setOpenSendEmail(true);
        }
    };
    return (
        <Section>
            <div className="max-w-[1200px] mx-auto flex flex-col items-center gap-8">
                <div className="flex flex-col items-center gap-4">
                    <img
                        src="https://res.cloudinary.com/djslo5b3u/image/upload/v1699321812/ThanksImage_fmi6pn.svg"
                        alt="Imagen de verificacion"
                    />
                    <h1 className="text-primary font-bold text-5xl">
                        Muchas gracias!
                    </h1>
                    <p className="w-1/2 text-center text-base text-grayTertiary">
                        Ahora eres un miembro de BikeMeNow. Te hemos enviado un
                        mensaje de verificación al correo{" "}
                        <strong>{userEmail}</strong>
                    </p>
                </div>
                <div className="flex gap-2">
                    <p>¿No te llego el correo?</p>
                    <button className="text-primary" onClick={handleSendEmail}>
                        Volver a enviar
                    </button>
                    {loadingAuth && <Loader className={"text-primary"} />}
                </div>
                <div className="flex flex-col items-center gap-3">
                    <p>¿Deseas ingresar?</p>
                    <Link to={"/auth/login"}>
                        <button
                            className="middle none center rounded-full bg-primary py-3 px-8 font-sans text-xs font-bold uppercase text-white shadow-sm  transition-all  hover:shadow-secondary  active:opacity-[0.85] active:shadow-none disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none"
                            data-ripple-light="true"
                            onClick={() => setIsRegistered(false)}
                        >
                            Iniciar sesión
                        </button>
                    </Link>
                </div>
                {openSendEmail && (
                    <SentEmailModal setOpenSendEmail={setOpenSendEmail} />
                )}
            </div>
        </Section>
    );
};
