// component
import Iconify from '../../components/Iconify';
import { ROUTES } from '../../constants/routerConfig';

// ----------------------------------------------------------------------

const getIcon = (name) => <Iconify icon={name} width={22} height={22} />;

const navConfig = [
  {
    title: 'dashboard',
    path: ROUTES.DASHBOARD_APP_PATH,
    icon: getIcon('eva:pie-chart-2-fill'),
  },
  {
    title: 'Crawler Configs',
    path: ROUTES.CRAWLER_CONFIGS,
    icon: getIcon('carbon:cloud-satellite-config'),
  },
  {
    title: 'Crawler Categories',
    path: ROUTES.CRAWLER_CATEGORY,
    icon: getIcon('carbon:cloud-satellite-config'),
  },
  {
    title: 'Crawler Products',
    path: ROUTES.CRAWLER_PRODUCT,
    icon: getIcon('carbon:cloud-satellite-config'),
  },
  {
    title: 'user',
    path: ROUTES.USER,
    icon: getIcon('eva:people-fill'),
  },
  {
    title: 'product',
    path: ROUTES.PRODUCT,
    icon: getIcon('eva:shopping-bag-fill'),
  },
  {
    title: 'blog',
    path: '/dashboard/blog',
    icon: getIcon('eva:file-text-fill'),
  },
  {
    title: 'login',
    path: '/login',
    icon: getIcon('eva:lock-fill'),
  },
  {
    title: 'register',
    path: '/register',
    icon: getIcon('eva:person-add-fill'),
  },
  {
    title: 'Not found',
    path: '/404',
    icon: getIcon('eva:alert-triangle-fill'),
  },
];

export default navConfig;
