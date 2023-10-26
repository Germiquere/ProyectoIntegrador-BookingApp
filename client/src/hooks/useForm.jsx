import { useEffect, useState } from "react";

export const useForm = (initialForm = {}) => {
    const [formState, setFormState] = useState(initialForm);
    // useEffect(() => {
    //   setFormState(initialForm);
    // }, [initialForm]);

    const onInputChange = (e, convertToNumber = false) => {
        const { name, value } = e.target;
        const finalValue = convertToNumber ? parseFloat(value) : value;

        setFormState({
            ...formState,
            [name]: finalValue,
        });
    };

    const onResetForm = () => {
        setFormState(initialForm);
    };
    const onFocus = (event) => {
        // Select the content of the input element
        event.target.select();
    };
    return {
        ...formState,
        formState,
        onInputChange,
        onResetForm,
        setFormState,
        onFocus,
    };
};
