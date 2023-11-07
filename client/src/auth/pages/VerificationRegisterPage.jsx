import Section from "../../bookingApp/components/Section";
import { Link } from "react-router-dom";

export const VerificationRegisterPage = () => {

    return (
            <Section>
                <div className="max-w-[1200px] mx-auto flex flex-col items-center gap-8">
                    <div className="flex flex-col items-center gap-4">
                        <img src="/src/assets/ThanksImage.svg" alt="Imagen de verificacion"/>
                        <h1 className="text-primary font-bold text-5xl">
                            Muchas gracias!
                        </h1>
                        <p className="w-1/2 text-center text-base text-grayTertiary">
                            Ahora eres un miembro de BikeMeNow. Te hemos enviado un mensaje de verificación al correo <strong>JhonDoe@mail.com</strong>
                        </p>
                    </div>
                    <div className="flex flex-col items-center gap-3">
                        <p className="text-sm">¿Deseas ingresar?</p>
                        <Link to={"/auth/login"}>
                            <button
                                className="middle none center rounded-full bg-primary py-3 px-8 font-sans text-xs font-bold uppercase text-white shadow-sm  transition-all  hover:shadow-secondary  active:opacity-[0.85] active:shadow-none disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none"
                                data-ripple-light="true"
                                >
                                Iniciar sesión
                            </button>
                        </Link>
                    </div>
                </div>

                
            </Section>
        )
};