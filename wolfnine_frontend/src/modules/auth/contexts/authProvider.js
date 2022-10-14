import axios from 'axios';
import * as React from 'react';
import { useState, useMemo, useEffect } from 'react';
import authService from '../services/authService';

export const AuthContext = React.createContext();

const AuthProvider = ({ children }) => {
  const [token, setToken] = useState(authService.getAccessToken());
  const [user, setUser] = useState();

  useEffect(() => {
    getAuthUser();
  }, [token]);

  const getAuthUser = async () => {
    await authService.getAuthUserInfo().then((res) => {
      setUser(res.data.data);
    });
  };

  const value = useMemo(
    () => ({
      token,
      user,
    }),
    [token, user]
  );
  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};

export default AuthProvider;

export const useAuth = () => {
  return React.useContext(AuthContext);
};
