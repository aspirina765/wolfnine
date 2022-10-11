import NextAuth from "next-auth";
import CredentialsProvider from "next-auth/providers/credentials";
import authService from "../../../modules/auth/services/authService";
import { LoginDataType } from "../../auth/login";
import type { NextApiRequest, NextApiResponse } from "next";
import { AUTH_PROVIDER } from "../../../constants/auth";

export default async function auth(req: NextApiRequest, res: NextApiResponse) {
  // Do whatever you want here, before the request is passed down to `NextAuth`
  return await NextAuth(req, res, authOptions);
}

export const authOptions = {
  // Configure one or more authentication providers
  providers: [
    CredentialsProvider({
      name: AUTH_PROVIDER,
      authorize: async (credentials: any) => {
        const data: LoginDataType = {
          username: credentials.username,
          password: credentials.password,
        };
        let result = null;
        try {
          // Authenticate user with credentials
          await authService
            .login(data)
            .then((res) => {
              result = res.data;
            })
            .catch((err) => {
              throw new Error(err);
            });
          return result;
        } catch (e: any) {
          throw new Error(e);
        }
      },
      credentials: {
        username: { label: "Username", type: "text", placeholder: "wolfnine" },
        password: { label: "Password", type: "password" },
      },
    }),
  ],
  callbacks: {
    jwt: async ({ token, user }: any) => {
      console.log("🚀 ~ file: [...nextauth].ts ~ line 42 ~ jwt: ~ user", user);
      if (user) {
        // This will only be executed at login. Each next invocation will skip this part.
        token.accessToken = user.data.accessToken;
        token.accessTokenExpiry = user.data.expiredAt;
        token.refreshToken = user.data.refreshToken;
      }
      return Promise.resolve(token);
    },
    session: async ({ session, token }: any) => {
      // Here we pass accessToken to the client to be used in authentication with your API
      session.accessToken = token.accessToken;
      session.accessTokenExpiry = token.expiredAt;
      session.error = token.error;

      return Promise.resolve(session);
    },
    async signIn({ user, account, profile, email, credentials }: any) {
      return true;
    },
    async redirect({ url, baseUrl }: any) {
      return baseUrl;
    },
  },
  pages: {
    signIn: "/auth/login",
    signOut: "/auth/signout",
    error: "/auth/login",
  },
};
