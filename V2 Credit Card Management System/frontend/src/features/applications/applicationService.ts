import { api, type ApiResponse, type PageResponse } from "../../lib/api";
import type { ApplicationSummary } from "./types";

export async function getApplications(status: string, page = 0, size = 15) {
  const response = await api.get<ApiResponse<PageResponse<ApplicationSummary>>>("/applications", {
    params: { status, page, size }
  });
  return response.data.data;
}

export async function runWorkflow(id: number, payload: Record<string, unknown>) {
  const response = await api.post<ApiResponse<ApplicationSummary>>(`/applications/${id}/workflow`, payload);
  return response.data.data;
}

export async function createBranchApplication(payload: Record<string, unknown>) {
  const response = await api.post<ApiResponse<ApplicationSummary>>("/applications/branch", payload);
  return response.data.data;
}

