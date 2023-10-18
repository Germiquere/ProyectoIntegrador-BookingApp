import { useEffect, useRef, useState } from "react";
import { BsCalendar } from "react-icons/bs";
import "react-date-range/dist/styles.css"; // main style file
import "react-date-range/dist/theme/default.css"; // theme css file
import { DateRange } from "react-date-range";
import { es } from "date-fns/locale";
import "./rangeCalendar.css";
import { compareAsc, format, startOfDay } from "date-fns";
export const Calendar = () => {
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
            <div className="flex">
                <div className="relative h-11 w-full  sm:min-w-[200px]">
                    <input
                        className="rounded-full sm:rounded-none peer h-full w-full flex-1 border-l-[1px] border-gray-100 p-3 font-sans text-sm font-normal text-blue-gray-700 outline outline-0 transition-all  focus:outline-0  disabled:bg-blue-gray-50 cursor-pointer"
                        placeholder="Elija sus fechas"
                        readOnly
                        type="text"
                        onClick={handleOpen}
                        value={
                            // lo paso  a string par ver si los valores son los mismos. Si son igules aparece el placeholder
                            state[0].startDate.toDateString() ===
                            state[0].endDate.toDateString()
                                ? ""
                                : `${format(state[0].startDate, "d-M-yyyy", {
                                      locale: es,
                                  })} a ${format(state[0].endDate, "d-M-yyyy", {
                                      locale: es,
                                  })}`
                        }
                    />
                    <BsCalendar
                        className="absolute top-1/2 right-3 transform -translate-y-1/2 cursor-pointer text-gray-400"
                        onClick={handleOpen}
                    />
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
            </div>
        </div>
    );
};
