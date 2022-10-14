import axios from 'axios';
import { AccessTokenObject, LoginDataType, RegisterDataType, UserInfo } from '../types/authTypes';
import { generateApiPath } from '../../../utils/generate';
import jwtUtil from '../../../utils/jwtUtil';

export const API_ENDPOINTS = {
  REGISTER: '/users/register',
  LOGIN: '/users/login',
  GET_AUTH_USER_INFO: '/users/my-info',
};

const ACCESS_TOKEN_KEY = 'access_token';
const EXPIRED_AT_KEY = 'expired_at';
const USER_INFO_KEY = 'user_info';

class AuthService {
  register = async (data: RegisterDataType) => {
    return await axios.post(generateApiPath(API_ENDPOINTS.REGISTER), data);
  };

  login = async (data: LoginDataType) => {
    let result = {};
    await axios
      .post(generateApiPath(API_ENDPOINTS.LOGIN), data)
      .then((res) => {
        result = res;
        this.setAccessToken({
          accessToken: res.data.data.accessToken,
          refreshToken: res.data.data.refreshToken,
          expiredAt: res.data.data.expiredAt,
        });
      })
      .catch((err) => {
        return false;
      });
    return result;
  };

  getAuthUserInfo = async () => {
    let result = {};
    await axios
      .get(generateApiPath(API_ENDPOINTS.GET_AUTH_USER_INFO), {
        headers: {
          Authorization: 'Bearer ' + this.getAccessToken(),
        },
      })
      .then((res) => {
        result = res;
        this.setUserInfo(res.data.data);
      });
    return result;
  };

  getUserInfo = (): UserInfo => {
    return JSON.parse(localStorage.getItem(USER_INFO_KEY) || '');
  };

  setUserInfo = (data: UserInfo): void => {
    localStorage.setItem(USER_INFO_KEY, JSON.stringify(data));
  };

  getAccessToken = (): string => {
    return localStorage.getItem(ACCESS_TOKEN_KEY) || '';
  };

  getExpireTime = () => {
    return localStorage.getItem(EXPIRED_AT_KEY) || '';
  };

  setAccessToken = (data: AccessTokenObject) => {
    JSON.stringify(localStorage.setItem(ACCESS_TOKEN_KEY, data.accessToken));
    JSON.stringify(localStorage.setItem(EXPIRED_AT_KEY, data.expiredAt));
  };

  isLogged = () => {
    console.log(
      'auth',
      this.getAccessToken() !== null &&
        this.getAccessToken() !== '' &&
        this.getExpireTime() !== null &&
        this.getExpireTime() !== ''
    );

    return (
      this.getAccessToken() !== null &&
      this.getAccessToken() !== '' &&
      this.getExpireTime() !== null &&
      this.getExpireTime() !== ''
    );
  };
}

const authService = new AuthService();
export default authService;
