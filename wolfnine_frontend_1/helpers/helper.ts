import { BASE_URL } from "../constants/server";

export const generateApiPath = (apiEndpoint: string) => {
  return BASE_URL + apiEndpoint;
};
