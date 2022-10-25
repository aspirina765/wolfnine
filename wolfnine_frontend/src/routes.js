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
import CrawlerCategory from './modules/crawlerCategory/CrawlerCategory';
import CreateCrawlerCategory from './modules/crawlerCategory/pages/CreateCrawlerCategory';
import CrawlerProduct from './modules/product/CrawlerProduct';
import EditCrawlerConfig from './modules/crawlerConfig/pages/EditCrawlerConfig';
import EditCrawlerCategory from './modules/crawlerCategory/pages/EditCrawlerCategory';
import AuthProvider from './modules/auth/contexts/authProvider';
import EditCrawlerProduct from './modules/product/pages/EditCrawlerProduct';

// ----------------------------------------------------------------------

export default function Router() {
  return useRoutes([
    {
      path: ROUTES.DASHBOARD_PARENT,
      element: (
        <AuthProvider>
          <ProtectedRoute>
            <DashboardLayout />
          </ProtectedRoute>
        </AuthProvider>
      ),
      children: [
        { path: ROUTES.DASHBOARD_APP_PATH, element: <DashboardApp /> },
        { path: ROUTES.CRAWLER_CONFIGS, element: <CrawlerConfig /> },
        { path: ROUTES.CREATE_CRAWLER_CONFIG, element: <CreateCrawlerConfig /> },
        { path: ROUTES.EDIT_CRAWLER_CONFIG, element: <EditCrawlerConfig /> },
        { path: ROUTES.CRAWLER_CATEGORY, element: <CrawlerCategory /> },
        { path: ROUTES.CREATE_CRAWLER_CATEGORY, element: <CreateCrawlerCategory /> },
        { path: ROUTES.EDIT_CRAWLER_CATEGORY, element: <EditCrawlerCategory /> },
        { path: ROUTES.CRAWLER_PRODUCT, element: <CrawlerProduct /> },
        { path: ROUTES.EDIT_CRAWLER_PRODUCT, element: <EditCrawlerProduct /> },
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
