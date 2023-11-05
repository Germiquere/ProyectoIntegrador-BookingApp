import React from "react";
import { TableHeaderUsers } from "../components/users/TableHeaderUsers";
import { TableUsers } from "../components/users/TableUsers";
import { useUsersContext } from "../../context/UsersContext";
import { EditRolModal } from "../components/users/EditRolModal";

export const UsersPage = () => {
    const { openEditUserModal } = useUsersContext();
    return (
        <div className=" lg:flex flex-col mt-3 gap-3">
            {openEditUserModal && <EditRolModal />}
            <TableHeaderUsers />
            <TableUsers />
        </div>
    );
};
