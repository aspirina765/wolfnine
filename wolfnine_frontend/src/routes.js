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
import AuthProvider from './modules/auth/contexts/authProvider';
import ShopeeAuth from './modules/shopeeShopConfig/pages/ShopeeAuth';
import { lazy } from 'react';

// ----------------------------------------------------------------------

const CrawlerConfig = lazy(() => import('./modules/crawlerConfig/CrawlerConfig'))
const CreateCrawlerConfig = lazy(() => import('./modules/crawlerConfig/pages/CreateCrawlerConfig'))
const EditCrawlerConfig = lazy(() => import('./modules/crawlerConfig/pages/EditCrawlerConfig'))
const CrawlerCategory = lazy(() => import('./modules/crawlerCategory/CrawlerCategory'))
const CreateCrawlerCategory = lazy(() => import('./modules/crawlerCategory/pages/CreateCrawlerCategory'))
const EditCrawlerCategory = lazy(() => import('./modules/crawlerCategory/pages/EditCrawlerCategory'))
const CrawlerProduct = lazy(() => import('./modules/product/CrawlerProduct'))
const EditCrawlerProduct = lazy(() => import('./modules/product/pages/EditCrawlerProduct'))
const ShopeeShopConfig = lazy(() => import('./modules/shopeeShopConfig/ShopeeShopConfig'))
const PushProductApi = lazy(() => import('./modules/pushProductApi/PushProductApi'))


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
        { path: ROUTES.SHOPEE_SHOP_CONFIG, element: <ShopeeShopConfig /> },
        { path: ROUTES.SHOPEE_AUTH, element: <ShopeeAuth /> },
        { path: ROUTES.PUSH_PRODUCT_API, element: <PushProductApi /> },
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
