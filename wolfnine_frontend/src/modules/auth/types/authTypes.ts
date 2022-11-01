export type LoginDataType = {
  username: string;
  password: string;
};

export type RegisterDataType = {
  fullName: string;
  email: string;
  phone: string;
  username: string;
  password: string;
  confirmPassword: string;
};

export type AccessTokenObject = {
  accessToken: string,
  refreshToken: string,
  expiredAt: string
}

export type UserInfo = {
  id: number,
  name: string,
  email: string,
  phone: string,
  status: string,
  userRoles: object
}
