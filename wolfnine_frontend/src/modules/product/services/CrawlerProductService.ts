import axios from 'axios';
import { number } from 'prop-types';
import { generateApiPath, generateRouteWithParam } from '../../../utils/generate';
import authService from '../../auth/services/authService';
import { api } from '../../shared/services/api';

const API_ENDPOINTS = {
  GET_ALL: '/products',
  CREATE: '/products',
  FIND_BY_ID: '/products/',
  DELETE_BY_IDS: '/products/delete',
  DELETE_BY_ID: '/products/:id/delete',
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

class CrawlerProductService {
  getAll = async (params: SearchParamType) => {
    return await api.get(generateApiPath(API_ENDPOINTS.GET_ALL), {
      params,
    });
  };

  findById = async (id: number) => {
    return await api.get(generateApiPath(API_ENDPOINTS.FIND_BY_ID + id));
  };

  deleteByIds = async (ids: Array<number>) => {
    return await api.post(generateApiPath(API_ENDPOINTS.DELETE_BY_IDS), ids);
  };

  deleteById = async (id: number) => {
    return await api.get(generateApiPath(generateRouteWithParam(API_ENDPOINTS.DELETE_BY_ID, ':id', id)));
  };
}

const crawlerProductService = new CrawlerProductService();
export default crawlerProductService;
