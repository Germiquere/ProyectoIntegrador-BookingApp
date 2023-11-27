import React from "react";

export const SkeletonSearchProducts = () => {
    return (
        <div className="flex">
            <div className=" min-w-[180px]">
                <div className="h-7 bg-gray-300 w-24  rounded-2xl mb-3 "></div>
                <div className="h-5 bg-gray-300 w-24  rounded-2xl mb-3"></div>
                <div className="h-5 bg-gray-300 w-24  rounded-2xl mb-3"></div>
            </div>
            <div className="grid grid-cols-1  gap-4 ssm:grid-cols-2  md:grid-cols-3 lg:grid-cols-4 flex-1">
                {[1, 2, 3, 4].map((item, index) => (
                    <div
                        className={` mt-8 mb-8 rounded-xl bg-gray-200 
    `}
                        key={index}
                    >
                        <div className="bg-gray-200 w-full h-48 rounded-xl"></div>
                        <p className="pl-4 pt-4 bg-gray-100 "></p>
                        <p className="p-4 bg-gray-100 rounded-b-xl"></p>
                    </div>
                ))}
            </div>
        </div>
    );
};
