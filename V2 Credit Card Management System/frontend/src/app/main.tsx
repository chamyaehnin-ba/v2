import React from "react";
import ReactDOM from "react-dom/client";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { RouterProvider, createBrowserRouter, Navigate } from "react-router-dom";
import { AppLayout } from "../components/layout/AppLayout";
import { QueuePage } from "../features/applications/QueuePage";
import { NewApplicationPage } from "../features/applications/NewApplicationPage";
import { ReportPage } from "../features/reports/ReportPage";
import "./styles.css";

const queryClient = new QueryClient();

const router = createBrowserRouter([
  {
    path: "/",
    element: <AppLayout />,
    children: [
      { index: true, element: <Navigate to="/applications/hub" replace /> },
      { path: "applications/new", element: <NewApplicationPage /> },
      { path: "applications/:queue", element: <QueuePage /> },
      { path: "reports/:type", element: <ReportPage /> },
      { path: "*", element: <Navigate to="/" replace /> }
    ]
  }
]);

ReactDOM.createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    <QueryClientProvider client={queryClient}>
      <RouterProvider router={router} />
    </QueryClientProvider>
  </React.StrictMode>
);
