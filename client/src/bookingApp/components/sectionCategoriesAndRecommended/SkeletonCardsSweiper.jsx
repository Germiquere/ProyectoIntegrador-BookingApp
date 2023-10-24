import React from "react";

export const SkeletonCardsSweiper = () => {
    return (
        <div>
            <div className="h-[300px] md:h-[330px] rounded-2xl overflow-hidden bg-gray-100 ">
                {/* animacion */}
                <div className=" animate-pulse">
                    {/* imagen */}
                    <div className="h-40 bg-gray-300 "></div>
                    <div className="h-12  flex justify-center  items-center ">
                        {/* titulo h-7 - texto h-5 */}
                        {/* titulo */}
                        <div className="h-7 bg-gray-300 w-24  rounded-2xl "></div>
                    </div>
                </div>
            </div>
        </div>
    );
};
