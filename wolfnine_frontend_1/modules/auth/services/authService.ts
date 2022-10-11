import { BASE_URL } from "../../../constants/server";
import { RegisterData } from "../../../pages/auth/register";
import axios from "axios";
import { generateApiPath } from "../../../helpers/helper";
import { LoginDataType } from "../../../pages/auth/login";

export const API_ENDPOINTS = {
  REGISTER: "/users/register",
  LOGIN: "/users/login"
};

class AuthService {
  register = async (data: RegisterData) => {
    return await axios.post(generateApiPath(API_ENDPOINTS.REGISTER), data);
  };

  login = async (data: LoginDataType) => {
    return await axios.post(generateApiPath(API_ENDPOINTS.LOGIN), data);
  }
}

const authService = new AuthService();
export default authService;
