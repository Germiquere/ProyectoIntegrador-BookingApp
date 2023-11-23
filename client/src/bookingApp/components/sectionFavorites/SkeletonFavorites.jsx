export const SkeletonFavorites = () => {
    return (
        <div className="md:mt-3 pt-3 md:pt-0">
            <div className="grid grid-cols-1  gap-4  ssm:grid-cols-2  sm:grid-cols-3  md:grid-cols-4  lg:grid-cols-5 w-full">
                {[1, 2, 3, 4, 5, 6, 7, 8, 9, 10].map((item) => (
                    <div
                        className={` mt-8 mb-8 rounded-xl bg-gray-200 
    `}
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
