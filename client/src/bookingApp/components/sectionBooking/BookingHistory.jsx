import React from "react";

export const BookingHistory = () => {
    const bookingHistory = [{
        id: 1,
        image: 'https://www.sanferbike.com/videostv/wp-content/uploads/2020/10/SPORT-600x400-1.jpg',
        producto: "Bicicleta de montaña",
        fechaInicio: "8 de Noviembre de 2023",
        fechaFinalizacion: "10 de Noviembre de 2023",
        precioAlquilerTotal: 5000
    },
    {
        id: 2,
        image: 'https://hips.hearstapps.com/hmg-prod/images/professional-road-cyclist-royalty-free-image-477445662-1564146292.jpg?crop=0.88847xw:1xh;center,top&resize=1200:*',
        producto: "Bicicleta de carrera",
        fechaInicio: "8 de Noviembre de 2023",
        fechaFinalizacion: "10 de Noviembre de 2023",
        precioAlquilerTotal: 8000
    },
    {
        id: 3,
        image: 'https://images.sodimac.com/v3/assets/blt2f8082df109cfbfb/blte37665df10d1fdcf/64c2dbe596bd576cc4dcec2a/ni%C3%B1os-monatando-bicicleta-mb.jpg',
        producto: "Bicicleta de niño",
        fechaInicio: "8 de Noviembre de 2023",
        fechaFinalizacion: "10 de Noviembre de 2023",
        precioAlquilerTotal: 4500
    }
    ]

    return (
        <div className=" flex flex-col mt-3 gap-3 max-w-[1200px] mx-auto">
            <div className="flex gap-8 justify-between items-center rounded-full bg-tertiary font-semibold text-xs p-3 px-6">
                <p className="w-4/12 ">Producto</p>
                <p className="w-3/12">Fecha de inicio</p>
                <p className="w-3/12">Fecha de finalización</p>
                <p className="w-1/12">Precio Total</p>
            </div>
            <div className="flex flex-col gap-2 ">
                {bookingHistory.map((booking) => (
                    <div key={booking.id}>
                        <div className="cursor-pointer flex gap-8 justify-between items-center rounded-xl text-xs p-3 px-6 bg-white shadow-md border border-gray-200 relative hover:bg-gray-100">
                            <div className="flex gap-2 flex-1 items-center w-5/12">
                                <div className="w-10 h-10 rounded-xl overflow-hidden">
                                    {booking.image && (
                                        <img
                                            src={booking.image}
                                            alt={booking.producto}
                                            className="w-10 h-full object-cover"
                                        />
                                    )}
                                </div>
                                <p>{booking.producto}</p>
                            </div>
                            <p className="w-3/12">
                                {booking.fechaInicio}
                            </p>
                            <p className="w-3/12">
                                {booking.fechaFinalizacion}
                            </p>
                            <p className="w-1/12">
                                {"$ " + booking.precioAlquilerTotal}
                            </p>
                        </div>
                    </div>
                ))}

            </div>
        </div>

    );
};

