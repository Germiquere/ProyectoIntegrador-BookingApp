export const CityBikes = () => {
    return (
        <div className="flex flex-col">
            <div>
                <input type="text" placeholder="Buscar" />
            </div>
            <div className="flex justify-between bg-red-400 rounded-full">
                <p className="p-5">id</p>
                <p className="">producto</p>
                <p>precio</p>
            </div>
            <div className="flex justify-between bg-indigo-100 rounded-xl">
                <p className="p-5">id</p>
                <p className="">producto</p>
                <p>precio</p>
            </div>
            <div></div>
        </div>
    );
};
