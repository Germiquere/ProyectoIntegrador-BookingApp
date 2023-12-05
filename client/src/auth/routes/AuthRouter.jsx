import { Navigate, Route, Routes } from 'react-router-dom';
import { LoginPage } from '../pages/LoginPage';
import { RegisterPage } from '../pages/RegisterPage';
import { AuthLayout } from '../layout/AuthLayout';
import { useUsersContext } from '../../context/UsersContext';
import { VerificationRegisterPage } from '../pages/VerificationRegisterPage';

export const AuthRouter = () => {
  const { isRegistered } = useUsersContext();

  return (
    <Routes>
      <Route element={<AuthLayout />}>
        <Route index path='login' element={<LoginPage />} />
        <Route path='register' element={<RegisterPage />} />
        {isRegistered && (
          <Route
            path='register/verification'
            element={<VerificationRegisterPage />}
          />
        )}

        <Route path='/*' element={<Navigate to='login' />} />
      </Route>
    </Routes>
  );
};
