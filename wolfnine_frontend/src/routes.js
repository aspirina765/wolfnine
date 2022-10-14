import { Navigate, useRoutes } from 'react-router-dom';
// layouts
import DashboardLayout from './layouts/dashboard';
import LogoOnlyLayout from './layouts/LogoOnlyLayout';
//
import Blog from './pages/Blog';
import User from './pages/User';
import Login from './pages/Login';
import NotFound from './pages/Page404';
import Register from './pages/Register';
import Products from './pages/Products';
import DashboardApp from './pages/DashboardApp';
import ProtectedRoute from './modules/auth/contexts/protectedRoute';
import { ROUTES } from './constants/routerConfig';
import CrawlerConfig from './modules/crawlerConfig/CrawlerConfig';
import CreateCrawlerConfig from './modules/crawlerConfig/pages/CreateCrawlerConfig';

// ----------------------------------------------------------------------

export default function Router() {
  return useRoutes([
    {
      path: ROUTES.DASHBOARD_PARENT,
      element: (
        <ProtectedRoute>
          <DashboardLayout />
        </ProtectedRoute>
      ),
      children: [
        { path: ROUTES.DASHBOARD_APP_PATH, element: <DashboardApp /> },
        { path: ROUTES.CRAWLER_CONFIGS, element: <CrawlerConfig /> },
        { path: ROUTES.CREATE_CRAWLER_CONFIG, element: <CreateCrawlerConfig /> },
        { path: ROUTES.USER, element: <User /> },
        { path: ROUTES.PRODUCT, element: <Products /> },
        { path: 'blog', element: <Blog /> },
      ],
    },
    {
      path: ROUTES.LOGIN,
      element: <Login />,
    },
    {
      path: ROUTES.REGISTER,
      element: <Register />,
    },
    {
      path: '/',
      element: <LogoOnlyLayout />,
      children: [
        { path: '/', element: <Navigate to={ROUTES.DASHBOARD_APP_PATH} /> },
        { path: '404', element: <NotFound /> },
        { path: '*', element: <Navigate to="/404" /> },
      ],
    },
    {
      path: '*',
      element: <Navigate to="/404" replace />,
    },
  ]);
}
