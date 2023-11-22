import { useEffect, useState } from "react";
import { IoIosArrowDropleft, IoIosArrowDropright } from "react-icons/io";
import { useLocation, useNavigate } from "react-router";
import queryString from "query-string";
import { useBikesContext } from "../context/BikesContext";
export const Pagination = ({
    bikesDataPaginated,
    className,
    scrollToProductsAndFilter,
}) => {
    const { bikesData, fetchPaginatedData, loadingPagination } =
        useBikesContext();
    const location = useLocation();
    const navigate = useNavigate();
    const [actualPage, setActualPage] = useState(1);
    console.log(actualPage);
    const {
        search,
        offset = 0,
        startDate = "",
        endDate = "",
    } = queryString.parse(location.search);
    // TODO: CAMBIAR EL 3 POR EL LIMIT 12

    const totalPages = bikesDataPaginated
        ? isNaN(bikesDataPaginated?.paginationData?.total)
            ? 0
            : Math.ceil(bikesDataPaginated?.paginationData?.total / 12)
        : 0;
    const calculateCurrentPage = () => {
        if (offset === 0) {
            return 1;
        } else {
            return Math.floor(offset / 12) + 1;
        }
    };

    // FUNCION PARA IR A LA SIGUIENTE PAGINA
    const handleSum = () => {
        const newOffset = parseInt(offset) + 12;
        if (actualPage < totalPages) {
            navigate(
                `/items?search=${search}${
                    startDate ? `&startDate=${startDate}` : ""
                }${endDate ? `&endDate=${endDate}` : ""}&offset=${newOffset}`
            );
            setActualPage((prevPage) => prevPage + 1);
            scrollToProductsAndFilter();
        }
    };

    const handleRest = async () => {
        const newOffset = parseInt(offset) - 12;
        if (actualPage === 1) return;
        navigate(
            `/items?search=${search}${
                startDate ? `&startDate=${startDate}` : ""
            }${endDate ? `&endDate=${endDate}` : ""}&offset=${newOffset}`
        );
        setActualPage(actualPage - 1);
        scrollToProductsAndFilter();
    };
    useEffect(() => {
        setActualPage(calculateCurrentPage());
    }, [offset]);

    return (
        <div
            className={`w-full  rounded-full bg-tertiary font-semibold  p-3 h-11 flex justify-center ${className} `}
        >
            <div className="flex gap-2 items-center">
                {parseInt(offset) !== 0 && (
                    <button onClick={handleRest}>
                        <IoIosArrowDropleft className="text-2xl cursor-pointer" />
                    </button>
                )}

                <div className="flex gap-2 items-center justify-center">
                    <p className=" bg-primary text-center w-7 h-7 text-white leading-7 rounded-full">
                        {actualPage}
                    </p>

                    <p>de</p>
                    <p>{totalPages}</p>
                </div>
                {actualPage !== totalPages && (
                    <button onClick={handleSum}>
                        <IoIosArrowDropright className="text-2xl cursor-pointer" />
                    </button>
                )}
            </div>
        </div>
    );
};
