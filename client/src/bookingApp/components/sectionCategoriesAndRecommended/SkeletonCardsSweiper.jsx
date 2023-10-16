import React from "react";

export const SkeletonCardsSweiper = () => {
    return (
        <div>
            <div className="h-52  rounded-2xl overflow-hidden bg-gray-100">
                <div className=" animate-pulse">
                    <div className="h-40 bg-gray-200 "></div>
                    <div className="h-12  flex justify-center  items-center ">
                        <div className="h-7 bg-gray-200 w-24  rounded-2xl "></div>
                    </div>
                </div>
            </div>
        </div>
    );
};
