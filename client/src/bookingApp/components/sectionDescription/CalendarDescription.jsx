import { useContext, useEffect, useRef, useState } from "react";
import { BsCalendar } from "react-icons/bs";
import "react-date-range/dist/styles.css"; // main style file
import "react-date-range/dist/theme/default.css"; // theme css file
import { DateRange } from "react-date-range";
import { es } from "date-fns/locale";
import "../sectionCalendarAndSearch/Calendar/rangeCalendar.css";
import { compareAsc, format, startOfDay, parseISO, addDays } from "date-fns";
import { CalendarAndSearchContext } from "../../../context/CalendarSearchContext";
import { Loader } from "../../../ui/Loader";
export const CalendarDescription = ({ bikeId }) => {
    const {
        onInputChange,
        formState,
        setFormState,
        datesData,
        loadingDates,
        errorDates,
        setErrorDates,
        fetchDatesByBikeId,
    } = useContext(CalendarAndSearchContext);
    const [state, setState] = useState([
        {
            startDate: new Date(),
            endDate: new Date(),
            key: "selection",
        },
    ]);
    const [hasSelected, setHasSelected] = useState(false);
    const [open, setOpen] = useState(false);
    const calendarRef = useRef(null);
    const [isMobile, setIsMobile] = useState(
        window.matchMedia("(max-width: 1024px)").matches
    );

    const getDatesBetween = (startDate, endDate) => {
        const dates = [];
        let currentDate = parseISO(startDate);

        // Mientras la fecha actual sea menor o igual a la fecha de fin
        while (currentDate <= parseISO(endDate)) {
            // Agrega la fecha actual al array de fechas
            dates.push(currentDate);

            // Incrementa la fecha actual en un día
            currentDate = addDays(currentDate, 1);
        }

        return dates;
    };
    // crea un array vacio
    const allDates = [];
    // por cada reserva las trasnforma en fechas y las pushea al array allDates
    datesData.forEach((date) => {
        allDates.push(...getDatesBetween(date.fechaInicio, date.fechaFin));
    });
    // funcion para que al recargar se vuelva a hacer el fetch
    const handleLoadAgain = async () => {
        const dates = await fetchDatesByBikeId(bikeId);
        if (dates) {
            setErrorDates(false);
        }
    };
    // funcion para que al abrir se borre el contenido del input
    const handleOpen = () => {
        setOpen(true);
        const newState = [...state];

        newState[0] = {
            ...newState[0],
            startDate: new Date(),
            endDate: new Date(),
        };
        setState(newState);
    }; // funcion para cerrar el calendar  una vez  seleccionada la fecha
    const handleSelect = (item) => {
        setState([item.selection]);

        if (
            compareAsc(item.selection.startDate, item.selection.endDate) === -1
        ) {
            setOpen(false);
        }
        if (!hasSelected) {
            setHasSelected(true); // Marcar que la selección se ha realizado una vez
        } else {
            // Obtén las fechas solo en la segunda vez

            const start = format(item.selection.startDate, "d-M-yyyy", {
                locale: es,
            });
            const end = format(item.selection.endDate, "d-M-yyyy", {
                locale: es,
            });
            setFormState({
                ...formState,
                startDate: start,
                endDate: end,
            });
            setHasSelected(false);
        }
    };
    // funcion para cerrar el calendar haciendo click outside

    useEffect(() => {
        function handleClickOutside(event) {
            if (
                // la primera validacion es para ver que el componente este montado la segunda es para ver que no se haga  click en la referencia
                calendarRef.current &&
                !calendarRef.current.contains(event.target)
            ) {
                setOpen(false);
                setFormState({
                    ...formState,
                    startDate: "",
                    endDate: "",
                });
                setHasSelected(false);
            }
        }
        if (open) {
            document.addEventListener("mousedown", handleClickOutside);
        } else {
            document.removeEventListener("mousedown", handleClickOutside);
        }

        return () => {
            document.removeEventListener("mousedown", handleClickOutside);
        };
    }, [open]);
    useEffect(() => {
        const mediaQuery = window.matchMedia("(max-width: 1024px)");
        const handleResize = () => {
            setIsMobile(mediaQuery.matches);
        };

        // Agrega un listener para la ventana de cambio de tamaño
        window.addEventListener("resize", handleResize);

        // Limpia el listener al desmontar el componente
        return () => {
            window.removeEventListener("resize", handleResize);
        };
    }, []);
    return (
        <div className="flex-col ">
            <div className="flex flex-col sm:flex-row gap-3 sm:gap-0 relative">
                <div className=" h-11 w-full   ">
                    <input
                        className={`border-[1px] lg:border-r-[1px]  border-gray-100 rounded-l-full  peer h-full w-full flex-1  p-3 font-sans text-sm font-normal text-blue-gray-700 outline outline-0 transition-all  focus:outline-0  disabled:bg-blue-gray-50 ${
                            errorDates ? "cursor-default" : "cursor-pointer"
                        }`}
                        placeholder="Desde"
                        readOnly
                        type="text"
                        onClick={handleOpen}
                        name="startDate"
                        value={formState.startDate}
                        disabled={errorDates || loadingDates}
                    />

                    {/* <BsCalendar
                        className="absolute top-1/2 right-3 transform -translate-y-1/2 cursor-pointer text-gray-400"
                        onClick={handleOpen}
                    /> */}
                </div>
                <div className=" h-11 w-full   ">
                    <input
                        className={`border-[1px] border-l-[0px] border-gray-100 rounded-r-full peer h-full w-full flex-1  p-3 font-sans text-sm font-normal text-blue-gray-700 outline outline-0 transition-all  focus:outline-0  disabled:bg-blue-gray-50  ${
                            errorDates ? "cursor-default" : "cursor-pointer"
                        }`}
                        placeholder="Hasta"
                        readOnly
                        type="text"
                        onClick={handleOpen}
                        name="endDate"
                        value={formState.endDate}
                        disabled={errorDates || loadingDates}
                    />
                </div>

                {open && !errorDates && (
                    <div ref={calendarRef}>
                        <DateRange
                            editableDateInputs={true}
                            onChange={handleSelect}
                            moveRangeOnFirstSelection={false}
                            ranges={state}
                            months={isMobile ? 1 : 2}
                            showDateDisplay={false}
                            minDate={new Date()}
                            rangeColors={["#0274AE"]}
                            locale={es}
                            direction="horizontal"
                            className="absolute z-50 lg:right-0 lg:left-auto transform lg:transform-none -translate-x-1/2 left-1/2  max-w-[250px] sm:max-w-none bottom-0 "
                            disabledDates={allDates}
                        />
                    </div>
                )}
            </div>
            <div className="flex gap-2">
                <p
                    className={`pt-1 text-xs text-red-500 ${
                        errorDates ? "opacity-100" : "opacity-0"
                    }`}
                >
                    Algo salió mal.
                </p>
                <div className="flex gap-2">
                    <p
                        className={`pt-1 text-xs text-red-500 underline underline-offset-1 cursor-pointer  ${
                            errorDates ? "opacity-100" : "opacity-0"
                        }`}
                        onClick={handleLoadAgain}
                    >
                        Volver a cargar fechas
                    </p>
                    {loadingDates && <Loader className={`text-red-500`} />}
                </div>
            </div>
        </div>
    );
};
