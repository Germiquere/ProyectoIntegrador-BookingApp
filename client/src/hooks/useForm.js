import { useEffect, useState } from "react";

export const useForm = (initialForm = {}) => {
  const [formState, setFormState] = useState(initialForm);
  // useEffect(() => {
  //   setFormState(initialForm);
  // }, [initialForm]);

  const onInputChange = ({ target }) => {
    const { name, value } = target;
    setFormState({
      ...formState,
      [name]: value,
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
