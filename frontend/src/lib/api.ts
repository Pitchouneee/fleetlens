import axios, { AxiosHeaders } from "axios";
import { getToken, clearToken } from "./auth";

const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL as string | undefined,
});

api.interceptors.request.use((config) => {
  const token = getToken();
  if (token) {
    config.headers =
      config.headers instanceof AxiosHeaders
        ? config.headers
        : new AxiosHeaders(config.headers);

    config.headers.set("Authorization", `Bearer ${token}`);
  }
  return config;
});

api.interceptors.response.use(
  (res) => res,
  (error) => {
    if (error?.response?.status === 401) {
      clearToken();
      if (window.location.pathname !== "/login") {
        window.location.href = "/login";
      }
    }
    return Promise.reject(error);
  }
);

export default api;
