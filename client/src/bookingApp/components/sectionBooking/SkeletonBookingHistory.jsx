export const SkeletonBookingHistory = () => {
    return (
        <div className="mt-3">
            <div className="relative flex gap-3 md:min-w-[300px] bg-gray-200 border rounded-md shadow-md p-4 items-center animate-pulse ">
                <div className="min-w-[64px] min-h-[64px] rounded-md border bg-gray-100 overflow-hidden w-16 h-16"></div>

                <div className="flex flex-col gap-2 h-16 p-1">
                    <h3 className=" bg-gray-100 w-24 h-5 rounded-2xl"></h3>

                    <p className=" bg-gray-100 w-24 h-5 rounded-2xl"></p>
                </div>
            </div>
        </div>

        // <div
        //     className={` mt-8 mb-8 rounded-xl bg-gray-200 animate-pulse
        // `}
        // >

        //     <div className="bg-gray-200 w-full h-48 rounded-xl"></div>
        //     <p className="pl-4 pt-4 bg-gray-100 "></p>
        //     <p className="p-4 bg-gray-100 rounded-b-xl"></p>
        // </div>
    );
};
