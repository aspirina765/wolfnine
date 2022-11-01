import axios from 'axios';
import { generateApiPath } from '../../../utils/generate';
import authService from '../../auth/services/authService';
import { api } from '../../shared/services/api';
import { CreateCrawlerConfigType } from '../types/crawlerConfigType';

const API_ENDPOINTS = {
  GET_ALL: '/crawler/configs',
  CREATE: '/crawler/configs',
  FIND_BY_ID: '/crawler/configs/',
  UPDATE: '/crawler/configs/',
  DELETE: '/crawler/configs/',
};

type SearchParamType = {
  limit: number;
  page: number;
  sortBy: string;
};

class CrawlerConfigService {
  getAll = async (params: SearchParamType) => {
    return await api.get(generateApiPath(API_ENDPOINTS.GET_ALL), {
      params,
    });
  };

  findById = async (id: number) => {
    return await api.get(generateApiPath(API_ENDPOINTS.FIND_BY_ID + id));
  };

  create = async (data: CreateCrawlerConfigType) => {
    return await api.post(generateApiPath(API_ENDPOINTS.CREATE), data);
  };

  update = async (id: number, data: CreateCrawlerConfigType) => {
    return await api.post(generateApiPath(API_ENDPOINTS.UPDATE + id), data);
  };

  delete = async (id: number) => {
    return await api.get(generateApiPath(API_ENDPOINTS.DELETE + id + '/delete'));
  };
}

const crawlerConfigService = new CrawlerConfigService();
export default crawlerConfigService;
