import { useState } from "react";
import { IoIosArrowDropleft, IoIosArrowDropright } from "react-icons/io";
import { useLocation, useNavigate } from "react-router";
import queryString from "query-string";
export const Pagination = ({ bikesDataPaginated }) => {
    const location = useLocation();
    const navigate = useNavigate();
    const [actualPage, setActualPage] = useState(1);

    const {
        search,
        page,
        startDate = "",
        endDate = "",
    } = queryString.parse(location.search);
    // TODO: CAMBIAR EL 3 POR EL LIMIT 12
    const totalPages = bikesDataPaginated
        ? Math.ceil(bikesDataPaginated?.paginationData?.total / 3)
        : 0;
    // FUNCION PARA IR A LA SIGUIENTE PAGINA
    const handleSum = () => {
        if (actualPage < totalPages) {
            navigate(
                `/items?search=${search}${
                    startDate ? `&startDate=${startDate}` : ""
                }${endDate ? `&endDate=${endDate}` : ""}&page=${
                    parseInt(page) + 1
                }`
            );
            setActualPage(actualPage + 1);
        }
    };

    const handleRest = () => {
        if (actualPage === 1) return;
        navigate(
            `/items?search=${search}${
                startDate ? `&startDate=${startDate}` : ""
            }${endDate ? `&endDate=${endDate}` : ""}&page=${parseInt(page) - 1}`
        );
        setActualPage(actualPage - 1);
    };
    return (
        <div className="w-full  rounded-full bg-tertiary font-semibold  p-3 h-11 flex justify-center">
            <div className="flex gap-2 items-center">
                <button onClick={handleRest}>
                    <IoIosArrowDropleft className="text-2xl cursor-pointer" />
                </button>
                <div className="flex gap-2 items-center justify-center">
                    <p className=" bg-primary text-center w-7 h-7 text-white leading-7 rounded-full">
                        {actualPage}
                    </p>

                    <p>de</p>
                    <p>{totalPages}</p>
                </div>
                <button onClick={handleSum}>
                    <IoIosArrowDropright className="text-2xl cursor-pointer" />
                </button>
            </div>
        </div>
    );
};
