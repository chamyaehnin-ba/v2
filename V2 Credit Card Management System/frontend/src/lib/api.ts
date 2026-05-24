import axios from "axios";
import { useSessionStore } from "../stores/sessionStore";

export const api = axios.create({
  baseURL: "/api/v1",
  timeout: 20000
});

api.interceptors.request.use((config) => {
  const { user } = useSessionStore.getState();
  config.headers["X-User-Id"] = user.employeeId;
  config.headers["X-User-Role"] = user.role;
  if (user.branchCode) config.headers["X-Branch-Code"] = user.branchCode;
  return config;
});

export interface ApiResponse<T> {
  success: boolean;
  code: string;
  message: string;
  data: T;
  timestamp: string;
}

export interface PageResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  number: number;
  size: number;
}

