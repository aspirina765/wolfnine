import axios, { Axios, AxiosError, AxiosResponse } from 'axios';
import { Navigate, useNavigate } from 'react-router-dom';
import { ROUTES } from '../../../constants/routerConfig';
import { ACCESS_TOKEN_KEY } from '../../auth/services/authService';

export const api = axios.create({
  baseURL: process.env.REACT_APP_BASE_API_URL,
  timeout: 1000,
  headers: {
    Authorization: `Bearer ${localStorage.getItem('access_token')}`,
  },
});

const handleGlobalError = (error: AxiosError) => {
  const statusCode = error.response?.status;
  // if (statusCode == 403) {
  //   window.location.replace(ROUTES.LOGIN);
  // }
};

api.interceptors.response.use(
  (response: AxiosResponse) => response,
  (error: AxiosError) => {
    handleGlobalError(error);
  }
);
