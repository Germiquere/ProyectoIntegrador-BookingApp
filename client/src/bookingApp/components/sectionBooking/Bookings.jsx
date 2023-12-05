import React, { useState } from "react";
import Section from "../Section";
import { useBookingsContext } from "../../../context/BookingContext";
import { useBikesContext } from "../../../context/BikesContext";
import { useUsersContext } from "../../../context/UsersContext";
import { IoIosArrowBack } from "react-icons/io";
import { Link, useNavigate } from "react-router-dom";
import { Loader } from "../../../ui/Loader";
import { useCalendarAndSearchContext } from "../../../context/CalendarSearchContext";
import { parse, format, differenceInDays } from "date-fns";

export const Bookings = () => {
    const { addNewBooking, loading, setIsReserved } = useBookingsContext();
    const { formState, setFormState } = useCalendarAndSearchContext();
    const { bikeById } = useBikesContext();
    const { userData } = useUsersContext();
    const navigate = useNavigate();

    const goBack = () => {
        navigate(-1);
    };

    console.log(formState.startDate);

    const parsedStartDate = parse(formState.startDate, "d-MM-yyyy", new Date());
    const parsedEndDate = parse(formState.endDate, "d-MM-yyyy", new Date());

    // Maneja el envío del formulario de reserva
    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            // Llamada a la función para agregar una nueva reserva

            const postingBooking = await addNewBooking(
                userData.usuarioId,
                bikeById.bicicletaId,
                format(parsedStartDate, "yyyy-MM-dd"),
                format(parsedEndDate, "yyyy-MM-dd")
            );

            if (postingBooking) {
                setFormState({
                    search: "",
                    startDate: "",
                    endDate: "",
                });
                navigate("/bookings/verification");
            }
        } catch (error) {
            console.error("Error al agregar reserva:", error);
        }
    };

    return (
        <Section>
            <div className="max-w-[1200px] mx-auto flex flex-col gap-6 md:mt-3 pt-3 md:pt-0 ">
                <div className="flex gap-2 items-center">
                    <div>
                        <button onClick={goBack} className="flex items-center">
                            <IoIosArrowBack />
                        </button>
                    </div>
                    <div>
                        <h2 className="text-lg sm:text-2xl font-semibold p-4">
                            Solicitar reserva
                        </h2>
                    </div>
                </div>

                <div className="flex flex-col gap-8 md:flex-row">
                    <div className="border border-gray-100 shadow-md p-4 rounded-lg w-auto flex-1">
                        <div className="flex flex-col sm:flex-row gap-3">
                            <div className="min-w-[224px] min-h-[224px]">
                                <img
                                    className="object-contain h-56 w-56"
                                    src={bikeById.imagenes[0].url}
                                    alt={`Imagen principal`}
                                />
                            </div>

                            {/* Detalles de la bicicleta */}
                            <h3 className="font-extrabold pt-2">
                                {bikeById.nombre}
                            </h3>
                        </div>

                        <div className="flex flex-col pt-2">
                            <p className="border-t border-gray-200 pt-4 font-semibold">
                                Total (ARG)
                                <span className=" pl-2">
                                    $
                                    {differenceInDays(
                                        parse(
                                            formState.endDate,
                                            "dd-MM-yyyy",
                                            new Date()
                                        ),
                                        parse(
                                            formState.startDate,
                                            "dd-MM-yyyy",
                                            new Date()
                                        )
                                    ) * bikeById.precioAlquilerPorDia}
                                </span>
                            </p>
                        </div>
                    </div>

                    {/* Formulario de reserva */}
                    <div className=" flex flex-col w-auto relative gap-2 md:w-96 ">
                        <h3 className="font-extrabold">Datos de la reserva</h3>
                        <form onSubmit={handleSubmit}>
                            <div className="flex justify-between">
                                <p className="font-semibold">
                                    Fecha de inicio:
                                </p>
                                <p> {formState.startDate}</p>
                            </div>
                            <div className="flex  justify-between">
                                <p className="font-semibold">Fecha de fin:</p>
                                <p> {formState.endDate}</p>
                            </div>

                            {/* Detalles del usuario */}
                            <div className="border-t border-gray-200">
                                <div className="flex justify-between">
                                    <p className="font-semibold">Usuario:</p>
                                    <p>
                                        {userData.nombre &&
                                            userData.nombre
                                                .charAt(0)
                                                .toUpperCase() +
                                                userData.nombre.slice(1)}
                                    </p>
                                </div>
                                <div className="flex flex-col ssm:flex-row justify-between">
                                    <p className="font-semibold">Email:</p>
                                    <p>{userData.email}</p>
                                </div>
                            </div>

                            {/* Detalles de pago y precio total */}
                            <div className="border-t border-gray-200">
                                <div className="flex justify-between">
                                    <p className="font-semibold">
                                        Método de pago:
                                    </p>
                                    <p>Efectivo</p>
                                </div>
                                <div className="flex justify-between">
                                    <p className="font-semibold">
                                        Precio total:
                                    </p>
                                    <p className="font-semibold">
                                        $
                                        {differenceInDays(
                                            parse(
                                                formState.endDate,
                                                "dd-MM-yyyy",
                                                new Date()
                                            ),
                                            parse(
                                                formState.startDate,
                                                "dd-MM-yyyy",
                                                new Date()
                                            )
                                        ) * bikeById.precioAlquilerPorDia}
                                    </p>
                                </div>
                            </div>

                            <div className="flex flex-col md:flex-row md:justify-center xl:flex-row xl:justify-evenly items-center gap-2  pt-12">
                                <button
                                    className=" middle none center  rounded-full bg-primary py-3 px-6 font-sans text-xs font-bold uppercase text-white shadow-sm  transition-all  hover:shadow-secondary  active:opacity-[0.85] active:shadow-none disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none w-full self-end
                  absolute bottom-0"
                                    data-ripple-light="true"
                                    type="submit"
                                    disabled={loading}
                                >
                                    {loading ? (
                                        <div className="flex justify-center">
                                            <Loader />
                                            <p>RESERVANDO ...</p>
                                        </div>
                                    ) : (
                                        "ALQUILAR"
                                    )}
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </Section>
    );
};
