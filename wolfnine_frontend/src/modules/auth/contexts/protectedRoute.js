import { Navigate } from 'react-router-dom';
import { ROUTES } from '../../../constants/routerConfig';
import { useAuth } from './authProvider';

const ProtectedRoute = ({ children }) => {
  const { token } = useAuth();

  if (!token) {
    return <Navigate to={ROUTES.LOGIN} replace />;
  }

  return children;
};

export default ProtectedRoute;
