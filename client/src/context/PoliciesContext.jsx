import { createContext, useContext, useState, useEffect } from 'react';
import {
  getPolicies,
  getPoliciesId,
  postPolicies,
  deletePolicies,
} from '../api/policies';

export const PoliciesContext = createContext();
export const usePoliciesContext = () => {
  return useContext(PoliciesContext);
};

export const PoliciesProvider = ({ children }) => {
  const [policies, setPolicies] = useState([]);
  const [error, setError] = useState(null);
  const [loading, setLoadingPolicies] = useState(false);

  // FUNCION PARA BUSCAR LAS POLITICAS
  const fetchData = async () => {
    setLoadingPolicies(true);
    try {
      const data = await getPolicies();
      console.log('Datos obtenidos:', data);
      setPolicies(data);
    } catch (err) {
      setError(err);
    } finally {
      setLoadingPolicies(false);
    }
  };

  // FUNCION PARA HACER BUSCAR POR ID
  const fetchDataForId = async (id) => {
    setLoadingPolicies(true);
    try {
      const data = await getPoliciesId(id);
      console.log('Datos obtenidos:', data);
      setPolicies(data);
    } catch (err) {
      setError(err);
    } finally {
      setLoadingPolicies(false);
    }
  };

  // FUNCION PARA AGREGAR UNA POLITICA
  const addNewPolicies = async (titulo, descripcion) => {
    setLoadingPolicies(true);
    try {
      const newPolicies = await postPolicies(titulo, descripcion);
      fetchData();
      return newPolicies;
    } catch (err) {
      setError(err);
    } finally {
      setLoadingPolicies(false);
    }
  };

  // FUNCION PARA ELIMINAR A FAVORITOS
  const removeAPolicies = async (id) => {
    setLoadingPolicies(true);
    try {
      const deletedPolicies = await deletePolicies(id);

      fetchData(id);
      return deletedPolicies;
    } catch (err) {
      setError(err);
    } finally {
      setLoadingPolicies(false);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <PoliciesContext.Provider
      value={{
        // PROPIEDADES
        policies,
        error,
        loading,
        // METODOS
        setPolicies,
        addNewPolicies,
        removeAPolicies,
        setError,
        fetchData,
      }}
    >
      {children}
    </PoliciesContext.Provider>
  );
};
