import React from "react";

export const SkeletonTableProducts = () => {
    return (
        <div className="flex flex-col gap-2">
            <div className="flex gap-8 justify-between items-center rounded-xl text-xs p-3 bg-gray-100 shadow-md animate-pulse ">
                <div className="w-10">
                    <div className="h-7 bg-gray-300 w-5  rounded-2xl "></div>
                </div>
                <div className="flex gap-2 flex-1 items-center">
                    <div className="w-10 h-10 ">
                        <div className="h-full bg-gray-300 w-full rounded-2xl "></div>
                    </div>
                    <div className="h-7 bg-gray-300 w-14  rounded-2xl "></div>
                </div>
                <div className="h-7 bg-gray-300 w-14  rounded-2xl "></div>

                <div className="w-16 flex justify-center">
                    <button
                        className="flex  items-center justify-center middle none center rounded-full  h-7 w-7 font-sans text-xs font-bold uppercase  transition-all hover:bg-blackOpacity1 active:bg-tertiary disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none "
                        data-ripple-dark="true"
                    >
                        <div className="h-5 bg-gray-300 w-5  rounded-2xl "></div>
                    </button>
                </div>
            </div>
        </div>
    );
};
