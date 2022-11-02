import { generateApiPath, generateRouteWithParam } from "../../../utils/generate";
import { api } from "../../shared/services/api"
import { FindAllShopeeConfigByAuthUserParam, GetShopBrandListParams, GetShopeeCategoryListParams, PushItemsToShopeeShopRequest, SaveShopeeShopConfig } from "../entities/ShopeeShopConfigEntity";

const API_ENDPOINS = {
    SHOP_AUTH_URL: '/shopeeShopConfigs/authUrl',
    SAVE_CONFIG: '/shopeeShopConfigs',
    FIND_ALL_BY_ME: '/shopeeShopConfigs',
    GET_CATEGORY_LIST: '/shopeeShopConfigs/:id/categories',
    GET_BRAND_LIST: '/shopeeShopConfigs/:id/brands',
    GET_LOGISTIC_CHANNEL_LIST: '/shopeeShopConfigs/:id/logisticChannels',
    PUSH_ITEMS_TO_SHOPEE_SHOP: '/products/pushToShopee'
}

const redirectUrl = 'http://localhost:3000/shopee/auth'

class ShopeeShopConfigService {

    generateShopAuthUrl = async () => {
        return await api.get(generateApiPath(API_ENDPOINS.SHOP_AUTH_URL), {
            params: {
                redirectUrl
            }
        });
    }

    saveConfig = async (data: SaveShopeeShopConfig) => {
        return await api.post(generateApiPath(API_ENDPOINS.SAVE_CONFIG), data);
    }

    findAllByAuthUser = async (params: FindAllShopeeConfigByAuthUserParam) => {
        return await api.get(generateApiPath(API_ENDPOINS.FIND_ALL_BY_ME));
    }

    getCategoryList = async (shopeeShopConfigId: number, params: GetShopeeCategoryListParams) => {
        return await api.get(generateApiPath(generateRouteWithParam(API_ENDPOINS.GET_CATEGORY_LIST, ':id', shopeeShopConfigId)), {
            params
        });
    }

    getShopBrandList = async (shopeeShopConfigId: number, params: GetShopBrandListParams) => {
        return await api.get(generateApiPath(generateRouteWithParam(API_ENDPOINS.GET_BRAND_LIST, ':id', shopeeShopConfigId)), {
            params
        });
    }

    getLogisticChannelList = async (shopeeShopConfigId: number) => {
        return await api.get(generateApiPath(generateRouteWithParam(API_ENDPOINS.GET_LOGISTIC_CHANNEL_LIST, ':id', shopeeShopConfigId)));
    }

    pushItemsToShopeeShop = async (data: PushItemsToShopeeShopRequest) => {
        return await api.post(generateApiPath(API_ENDPOINS.PUSH_ITEMS_TO_SHOPEE_SHOP), data);
    }
}

const shopeeShopConfigService = new ShopeeShopConfigService();
export default shopeeShopConfigService;