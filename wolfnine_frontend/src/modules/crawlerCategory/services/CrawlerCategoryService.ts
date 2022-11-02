import axios from 'axios';
import { generateApiPath } from '../../../utils/generate';
import authService from '../../auth/services/authService';
import { api } from '../../shared/services/api';
import { CreateCrawlerCategoryType } from '../types/crawlCategoryType';

const API_ENDPOINTS = {
  GET_ALL: '/crawler/categories',
  CREATE: '/crawler/categories',
  UPDATE: '/crawler/categories/',
  FIND_BY_ID: '/crawler/categories/',
  DELETE: '/crawler/categories/',
};

type SearchParamType = {
  limit: number;
  page: number;
  sortBy: string;
};

class CrawlerCategoryService {
  getAll = async (params: SearchParamType) => {
    return await api.get(generateApiPath(API_ENDPOINTS.GET_ALL), {
      params,
    });
  };

  create = async (data: CreateCrawlerCategoryType) => {
    return await api.post(generateApiPath(API_ENDPOINTS.CREATE), data);
  };

  findById = async (id: number) => {
    return await api.get(generateApiPath(API_ENDPOINTS.FIND_BY_ID + id));
  };

  update = async (id: number, data: CreateCrawlerCategoryType) => {
    return await api.post(generateApiPath(API_ENDPOINTS.UPDATE + id), data);
  };

  delete = async (id: number) => {
    return await api.get(generateApiPath(API_ENDPOINTS.DELETE + id + '/delete'));
  };
}

const crawlerCategoryService = new CrawlerCategoryService();
export default crawlerCategoryService;
