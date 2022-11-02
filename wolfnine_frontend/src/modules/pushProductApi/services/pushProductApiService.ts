import { generateApiPath } from "../../../utils/generate";
import { api } from "../../shared/services/api";
import { PushProductToApi, SavePushProductApiConfig } from "../enums/pushProductApiEnum";

const API_ENDPOINS = {
    FIND_ALL: '/pushProductApis',
    SAVE: '/pushProductApis',
    PUSH_PRODUCT_TO_API: '/products/pushToApi'
}

class PushProductApiService {

    findAll = async () => {
        return await api.get(generateApiPath(API_ENDPOINS.FIND_ALL));
    }

    save = async (data: SavePushProductApiConfig) => {
        return await  api.post(generateApiPath(API_ENDPOINS.SAVE), data);
    }

    pushProductToApi = async (data: PushProductToApi) => {
        return await api.post(generateApiPath(API_ENDPOINS.PUSH_PRODUCT_TO_API), data);
    }
}

const pushProductApiService = new PushProductApiService();
export default pushProductApiService;