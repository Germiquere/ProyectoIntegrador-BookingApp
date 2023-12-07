export const SkeletonDescription = () => {
    return (
        <div className="flex gap-3 flex-col relative  md:justify-center mt-3 max-w-[1200px] mx-auto animate-pulse">
            {/* seccion del lado izquierdo */}
            {/* NOTA: con flex-1 le digo que ocupe todo el espacio libre */}
            <div className="flex-1 p-3 bg-gray-100 rounded-xl  ">
                <div className="h-7 bg-gray-300 w-24  rounded-2xl mb-3 "></div>
                <div className="h-5 bg-gray-300 w-24  rounded-2xl mb-3"></div>
                <div className="h-5 bg-gray-300   rounded-2xl  max-w-[1000px] mb-3"></div>
                <div className="h-96 bg-gray-300   rounded-2xl  max-w-[1000px] "></div>

                {/* <ImgGallery
                data={data}
                handleToggleImgGallery={handleToggleImgGallery}
            /> */}
            </div>
            {/* seccion del costado derecho */}
            <div className=" flex flex-col bg-gray-100 rounded-xl  p-3 gap-3  h-full lg:h-auto ">
                <div
                    className="flex gap-2 items-center justify-end
                cursor-pointer
                absolute top-4  right-3
               
            "
                >
                    <div className="h-5 bg-gray-300 w-10  rounded-2xl mb-3"></div>
                </div>
                <div className="flex flex-col gap-2 h-full justify-between">
                    <div className="flex flex-col gap-2">
                        <div className="h-5 bg-gray-300 w-24  rounded-2xl mb-3"></div>
                        <div className="h-5 bg-gray-300 w-24  rounded-2xl mb-3"></div>
                        <div className="h-5 bg-gray-300 w-full  rounded-2xl mb-3"></div>
                    </div>

                    <div className="h-5 bg-gray-300 w-full  rounded-2xl mb-3"></div>
                </div>
            </div>
        </div>
    );
};
