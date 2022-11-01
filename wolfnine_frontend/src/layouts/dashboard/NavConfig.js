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
    title: 'Shopee Shop Configs',
    path: ROUTES.SHOPEE_SHOP_CONFIG,
    icon: getIcon('carbon:cloud-satellite-config'),
  },
  {
    title: 'Push Product Api',
    path: ROUTES.PUSH_PRODUCT_API,
    icon: getIcon('carbon:cloud-satellite-config'),
  },
];

export default navConfig;
