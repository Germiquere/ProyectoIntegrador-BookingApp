import { useUsersContext } from "../../../context/UsersContext";
import { SkeletonTableUsers } from "./SkeletonTableUsers";

export const TableUsers = () => {
    const { usersData, loadingUsers, setOpenEditUserModal } = useUsersContext();
    return (
        <div className="flex flex-col gap-2">
            {loadingUsers
                ? [1, 2, 3, 4, 5, 6, 7, 8, 9, 10].map((index) => (
                      <SkeletonTableUsers key={index} />
                  ))
                : usersData.map((user) => (
                      <div key={user.usuarioId}>
                          <div
                              //   onClick={() => {
                              //       setOpenEditProductModal(true);
                              //       setFormState({
                              //           bicicletaId: bike.bicicletaId,
                              //           nombre: bike.nombre,
                              //           descripcion: bike.descripcion,
                              //           precioAlquilerPorDia:
                              //               bike.precioAlquilerPorDia,
                              //           categoria: {
                              //               categoriaId:
                              //                   bike.categoria.categoriaId,
                              //           },
                              //           imagenes: bike.imagenes,
                              //       });
                              //   }}
                              onClick={() => {
                                  setOpenEditUserModal(true);
                              }}
                              className="cursor-pointer flex gap-8 justify-between items-center rounded-xl text-xs p-3 bg-white shadow-md border border-gray-200 relative hover:bg-gray-100"
                          >
                              <p className="w-10">{user.usuarioId}</p>
                              <div className="flex gap-2 h-10 flex-1 items-center">
                                  <p>{user.nombre + " " + user.apellido}</p>
                              </div>
                              <div className="flex  flex-1 items-center">
                                  <p>{user.email}</p>
                              </div>
                              <p className="w-24 overflow-hidden">{user.rol}</p>
                          </div>
                      </div>
                  ))}
        </div>
    );
};
