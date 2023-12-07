import React, { useEffect } from "react";
import { useBookingsContext } from "../../../context/BookingContext";
import BookingHistoryModal from "./BookingHistoryModal";
import { Helmet } from "react-helmet";
import Section from "../Section";
import { format, parse, parseISO } from "date-fns";
import { useUsersContext } from "../../../context/UsersContext";
import { es } from "date-fns/locale";
import {
    BsXLg,
    BsCurrencyDollar,
    BsCloudUpload,
    BsX,
    BsStar,
    BsStarFill,
} from "react-icons/bs";
import { Tooltip } from "@mui/material";
import { SkeletonBookingHistory } from "./SkeletonBookingHistory";

export const BookingHistory = () => {
    const {
        openBookingHistoryModal,
        handleOpenHistoryModal,
        bookingByUserId,
        bookingByUserIdGet,
        loading,
        formState,
        setFormState,
        setBookingDetail,
    } = useBookingsContext();
    const { userData } = useUsersContext();
    // FUNCION PARA FORMATEAR LAS FECHAS
    const formatDates = (date) => {
        if (date.length > 0) {
            const parsedDate = parseISO(date, "d-MM-yyyy", new Date());
            const formatedParsedDate = format(parsedDate, "dd MMM yyyy", {
                locale: es,
            });
            return formatedParsedDate;
        }
        return date;
    };
    useEffect(() => {
        bookingByUserIdGet(userData.usuarioId);
    }, []);

    return (
        <Section>
            <Helmet>
                <title>Mis reservas Bike Me Now</title>
                {/* <link rel="canonical" href="http://mysite.com/example" /> */}

                <meta
                    property="og:image"
                    content="https://res.cloudinary.com/djslo5b3u/image/upload/v1698971497/BikeMeNow_BlueAlpha_svidg9.png"
                />
                <meta property="og:title" content="Mis reservas Bike Me Now" />
                <meta
                    property="og:description"
                    content="Descubre la libertad sobre dos ruedas con nuestro servicio de alquiler de bicicletas. Explora tu ciudad o destinos increíbles mientras reservas la bicicleta perfecta para cada aventura. ¡Siente el viento en tu rostro y pedalea hacia tus próximas experiencias inolvidables!"
                />
            </Helmet>
            {loading ? (
                <div className="max-w-[1200px] mx-auto ">
                    <div className="grid grid-cols-1 gap-4  sm:grid-cols-2 lg:grid-cols-3 md:mt-3 pt-3 md:pt-0  ">
                        {[1, 2, 3, 4, 5, 6].map((item) => (
                            <SkeletonBookingHistory key={item} />
                        ))}
                    </div>
                </div>
            ) : (
                <div className="max-w-[1200px] mx-auto ">
                    <div className="md:mt-3 pt-3 md:pt-0">
                        {/* {favorites.length !== 0 && ( */}
                        <h2 className="text-lg sm:text-2xl font-semibold">
                            Historial de reservas
                        </h2>
                    </div>

                    {bookingByUserId.length === 0 ? (
                        <div className="flex justify-center items-center min-h-[calc(100vh-350px)] gap-2">
                            <p className="text-lg sm:text-2xl font-semibold text-gray-300">
                                No tienes ninguna reserva
                            </p>
                        </div>
                    ) : (
                        <div className="grid grid-cols-1 gap-4  sm:grid-cols-2 lg:grid-cols-3 cursor-pointer ">
                            {bookingByUserId.map((booking) => (
                                <div
                                    key={booking.reservaId}
                                    onClick={() => {
                                        setBookingDetail(booking);
                                        setFormState({
                                            ...formState,
                                            reserva: {
                                                reservaId: booking.reservaId,
                                            },
                                        });
                                        handleOpenHistoryModal();
                                    }}
                                    className=" hover:bg-gray-100 relative flex gap-3 md:min-w-[300px] bg-white border border-gray-100 rounded-md shadow-md p-4 items-center"
                                >
                                    <div className="min-w-[64px] min-h-[64px] rounded-md border border-gray-100 overflow-hidden w-16 h-16">
                                        <img
                                            className="object-contain h-16 w-16"
                                            src={
                                                booking.bicicleta.imagenes[0]
                                                    .url
                                            }
                                            alt={booking.bicicleta.nombre}
                                        />
                                    </div>

                                    <div className="flex flex-col gap-2 h-16 p-1">
                                        <h3 className="font-semibold text-xs ssm:hidden">
                                            {booking.bicicleta.nombre.length >
                                            40
                                                ? booking.bicicleta.nombre.slice(
                                                      0,
                                                      40
                                                  ) + "..."
                                                : booking.bicicleta.nombre}
                                        </h3>
                                        <h3 className="font-semibold text-xs hidden ssm:block lg:hidden">
                                            {booking.bicicleta.nombre.length >
                                            55
                                                ? booking.bicicleta.nombre.slice(
                                                      0,
                                                      55
                                                  ) + "..."
                                                : booking.bicicleta.nombre}
                                        </h3>
                                        <h3 className="font-semibold text-xs hidden lg:block">
                                            {booking.bicicleta.nombre.length >
                                            90
                                                ? booking.bicicleta.nombre.slice(
                                                      0,
                                                      90
                                                  ) + "..."
                                                : booking.bicicleta.nombre}
                                        </h3>
                                        <p className="text-[10px] ssm:text-xs">
                                            {`${formatDates(
                                                booking.fechaInicio
                                            )} - ${formatDates(
                                                booking.fechaFin
                                            )}`}
                                        </p>
                                    </div>
                                    {/* <div className="absolute top-2 right-2 text-primary cursor-pointer">
                                        <Tooltip title="Puntuar">
                                            <div
                                                onClick={() => {
                                                    setFormState({
                                                        ...formState,
                                                        reserva: {
                                                            reservaId:
                                                                booking.reservaId,
                                                        },
                                                    });
                                                    handleOpenHistoryModal();
                                                }}
                                            >
                                                <BsStarFill />
                                            </div>
                                        </Tooltip>
                                    </div> */}
                                </div>
                            ))}
                        </div>
                    )}
                </div>
            )}
            <div>{openBookingHistoryModal && <BookingHistoryModal />}</div>
        </Section>
    );
};
