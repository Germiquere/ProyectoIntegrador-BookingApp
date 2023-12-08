import Section from "../Section";
import { useUsersContext } from "../../../context/UsersContext";
import { BsCheck2 } from "react-icons/bs";
import { Link } from "react-router-dom";

export const VerificationBookingPage = () => {
    const { userData } = useUsersContext();
    return (
        <Section>
            <div className="max-w-[1200px] mx-auto flex flex-col gap-6 items-center  md:mt-3 justify-center pt-3 md:pt-0">
                <BsCheck2 className="text-9xl text-primary bg-blue-100 rounded-full p-2" />
                <h2 className="text-primary text-5xl text-center">
                    ¡Tu reserva está confirmada!
                </h2>
                <div className="text-center text-lg">
                    <p className="">
                        Te hemos enviado un correo electrónico con la
                        informacion a
                    </p>
                    <p className="font-bold">{userData.email}</p>
                </div>

                <Link to={"/*"}>
                    <button
                        className="middle none center mr-3 rounded-full bg-primary py-3 px-6 font-sans text-xs font-bold uppercase text-white shadow-sm  transition-all  hover:shadow-secondary  active:opacity-[0.85] active:shadow-none disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none"
                        data-ripple-light="true"
                    >
                        INICIO
                    </button>
                </Link>
            </div>
        </Section>
    );
};
