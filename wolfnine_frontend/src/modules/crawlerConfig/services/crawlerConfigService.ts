import axios from 'axios';
import { generateApiPath } from '../../../utils/generate';
import authService from '../../auth/services/authService';
import { CreateCrawlerConfigType } from '../types/crawlerConfigType';

const API_ENDPOINTS = {
  GET_ALL: '/crawler/configs',
  CREATE: '/crawler/configs',
};

type SearchParamType = {
  limit: number;
  page: number;
  sortBy: string;
};

const headerConfigs = {
  headers: {
    Authorization: `Bearer ${authService.getAccessToken()}`,
  },
};

class CrawlerConfigService {
  getAll = async (params: SearchParamType) => {
    return await axios.get(generateApiPath(API_ENDPOINTS.GET_ALL), {
      headers: {
        Authorization: `Bearer ${authService.getAccessToken()}`,
      },
      params,
    });
  };

  create = async (data: CreateCrawlerConfigType) => {
    return await axios.post(generateApiPath(API_ENDPOINTS.CREATE), data, headerConfigs);
  };
}

const crawlerConfigService = new CrawlerConfigService();
export default crawlerConfigService;
