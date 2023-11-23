import { useState } from 'react';
import { useForm } from '../../../hooks/useForm';
import { Tooltip } from '@mui/material';
import { PiArchiveBoxLight } from 'react-icons/pi';
import { usePoliciesContext } from '../../../context/PoliciesContext';

const formData = {
  nombre: '',
  descripcion: '',
};

export const ManagePolicies = () => {
  const { addNewPolicies, error, setError } = usePoliciesContext();
  const { onInputChange, onResetForm, nombre, descripcion } = useForm(formData);
  const [erros, setErros] = useState({
    nombre: false,
    descripcion: false,
  });

  // FUNCION PARA MANEJAR EL CAMBIO DE INPUT Y SUS ERRORES
  const handleInputChange = (e, toNumber = false) => {
    const { name, value } = e.target;
    onInputChange(e, toNumber);
    setErros({
      ...erros,
      [name]: value.trim() === '',
    });
    setError('');
  };
  // FUNCION PARA VALIDACIONES AL PRECIONAR GUARDAR
  const handleValidations = () => {
    let hasError = false;

    if (nombre.trim() === '') {
      setErros((prevErrors) => ({
        ...prevErrors,
        nombre: true,
      }));
      hasError = true;
    }
    if (descripcion.trim() === '') {
      setErros((prevErrors) => ({
        ...prevErrors,
        descripcion: true,
      }));
      hasError = true;
    }

    return hasError;
  };

  const handleSave = async () => {
    if (!handleValidations()) {
      try {
        // Obtener los valores de nombre y descripcion desde el estado
        const titulo = nombre;
        const desc = descripcion;
        // Llamar a la función para agregar una nueva política
        const newPolicies = await addNewPolicies(titulo, desc);
        console.log('Nuevas políticas:', newPolicies);
        // Limpiar los campos después de crear la política
        onResetForm();
      } catch (error) {
        // Manejar el error si es necesario
        console.error('Error al agregar políticas:', error);
      }
    }
  };

  return (
    <div className='flex flex-col gap-5'>
      <div className='w-full'>
        <h2 className='text-lg font-semibold pb-3 border-b-[1px] border-gray-300'>
          Detalles de las políticas
        </h2>
      </div>
      <div className='flex gap-5 '>
        <div className='flex flex-col gap-2 flex-1'>
          <div className='w-full'>
            <label className='text-base font-semibold mb-2'>Política *</label>
            <div className='relative h-11 w-full min-w-[200px] shadow-md rounded-xl border-[1px] border-gray-100 overflow-hidden'>
              <input
                className='peer h-full w-full p-2 font-sans text-sm font-normal text-blue-gray-700 outline outline-0 transition-all focus:outline-0 disabled:bg-blue-gray-50'
                placeholder='Titulo'
                type='text'
                value={nombre}
                name='nombre'
                onChange={handleInputChange}
              />
            </div>
            <p
              className={`pt-1 text-xs text-red-500 ${
                erros.nombre ? 'block' : 'hidden'
              }`}
            >
              Campo obligatorio
            </p>
            <p
              className={`pt-1 text-xs text-red-500 ${
                error?.status === 409 ? 'block' : 'hidden'
              }`}
            >
              Ya existe una categoria con ese nombre
            </p>
          </div>
          <div>
            <label className='text-base font-semibold mb-2'>
              Descripción *
            </label>
            <textarea
              className='p-2 w-full outline outline-0 shadow-md border-[1px] rounded-xl overflow-hidden border-gray-100'
              style={{ resize: 'none' }}
              placeholder='Descripción de la politica'
              rows={4}
              value={descripcion}
              name='descripcion'
              onChange={handleInputChange}
            ></textarea>
            <p
              className={`pt-1 text-xs text-red-500 ${
                erros.descripcion ? 'block' : 'hidden'
              }`}
            >
              Campo obligatorio
            </p>
          </div>
        </div>
        {/* Div de la derecha */}
        <div className='flex flex-1 gap-20 items-center'>
          <Tooltip title='Guardar Politica'>
            <button
              className='flex text-white items-center justify-center middle none center rounded-full h-10 w-10 font-sans text-xs font-bold uppercase bg-primary transition-all active:bg-tertiary'
              data-ripple-dark='true'
              onClick={handleSave}
            >
              <PiArchiveBoxLight className='text-2xl' />
            </button>
          </Tooltip>
        </div>
      </div>
    </div>
  );
};
