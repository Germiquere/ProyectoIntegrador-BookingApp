import { Tooltip } from "@mui/material";
import { MdDelete } from "react-icons/md";
import { usePoliciesContext } from "../../../context/PoliciesContext";

export const TablePolicies = () => {
    const { policies, removeAPolicies, loading } = usePoliciesContext();
    const handleDeletePolicy = async (policy) => {
        await removeAPolicies(policy.politicaId);
    };

    return (
        <div className="flex flex-col gap-2">
            {policies.map((policy) => (
                <div key={policy.politicaId}>
                    <div className="cursor-pointer flex gap-8 justify-between items-center rounded-xl text-xs p-2 bg-white shadow-md border border-gray-200 relative hover:bg-gray-100">
                        <div className="flex gap-2 flex-1 items-center">
                            <p>{policy.titulo}</p>
                        </div>
                        <Tooltip title="Borrar">
                            <button
                                className="flex text-gray-500 items-center justify-center middle none center rounded-full  h-10 w-10 font-sans text-xs font-bold uppercase hover:bg-blackOpacity1  transition-all  active:bg-tertiary disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none "
                                data-ripple-dark="true"
                                disabled={loading}
                                onClick={() => {
                                    handleDeletePolicy(policy);
                                }}
                            >
                                <MdDelete className="text-xl" />
                            </button>
                        </Tooltip>
                    </div>
                </div>
            ))}
        </div>
    );
};
