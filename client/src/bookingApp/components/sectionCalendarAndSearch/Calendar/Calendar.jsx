import { useContext, useEffect, useRef, useState } from "react";
import { BsCalendar } from "react-icons/bs";
import "react-date-range/dist/styles.css"; // main style file
import "react-date-range/dist/theme/default.css"; // theme css file
import { DateRange } from "react-date-range";
import { es } from "date-fns/locale";
import "./rangeCalendar.css";
import { compareAsc, format, startOfDay } from "date-fns";
import { CalendarAndSearchContext } from "../../../../context/CalendarSearchContext";
export const Calendar = () => {
    const { onInputChange, formState, setFormState } = useContext(
        CalendarAndSearchContext
    );
    const [hasSelected, setHasSelected] = useState(false);
    const [state, setState] = useState([
        {
            startDate: new Date(),
            endDate: new Date(),
            key: "selection",
        },
    ]);

    const [open, setOpen] = useState(false);
    const calendarRef = useRef(null);

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
                // de esta forma consigo recetear la funcion hasselected cuando clickeo afuera y tambien los valores del start y end para evitar errores con los datos
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
    return (
        <div className="flex-col ">
            <div className="flex flex-col sm:flex-row gap-3 sm:gap-0">
                <div className="relative h-11 w-full  sm:min-w-[150px] ">
                    {/* input stardate */}
                    <input
                        className="sm:border-r-[1px]  sm:border-gray-100 rounded-full sm:rounded-none peer h-full w-full flex-1  p-3 font-sans text-sm font-normal text-blue-gray-700 outline outline-0 transition-all  focus:outline-0  disabled:bg-blue-gray-50 cursor-pointer"
                        placeholder="Desde"
                        type="text"
                        onClick={handleOpen}
                        readOnly
                        name="startDate"
                        value={formState.startDate}
                    />

                    {/* <BsCalendar
                        className="absolute top-1/2 right-3 transform -translate-y-1/2 cursor-pointer text-gray-400"
                        onClick={handleOpen}
                    /> */}
                    {open && (
                        <div ref={calendarRef}>
                            <DateRange
                                editableDateInputs={true}
                                onChange={handleSelect}
                                moveRangeOnFirstSelection={false}
                                ranges={state}
                                months={1}
                                showDateDisplay={false}
                                minDate={new Date()}
                                rangeColors={["#0274AE"]}
                                locale={es}
                                direction="horizontal"
                                className="absolute z-50 left-1/2 transform -translate-x-1/2 max-w-[250px] sm:max-w-none bottom-0 sm:bottom-auto"
                            />
                        </div>
                    )}
                </div>
                <div className=" h-11 w-full  sm:min-w-[150px] ">
                    <input
                        // input enddate
                        className="rounded-full sm:rounded-none peer h-full w-full flex-1  p-3 font-sans text-sm font-normal text-blue-gray-700 outline outline-0 transition-all  focus:outline-0  disabled:bg-blue-gray-50 cursor-pointer"
                        placeholder="Hasta"
                        readOnly
                        type="text"
                        onClick={handleOpen}
                        name="endDate"
                        value={formState.endDate}
                    />
                </div>
            </div>
        </div>
    );
};
