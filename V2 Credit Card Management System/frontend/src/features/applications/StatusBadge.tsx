import clsx from "clsx";
import type { ApplicationSummary } from "./types";

const colors: Record<ApplicationSummary["status"], string> = {
  HUB: "bg-blue-50 text-bank",
  INBOX: "bg-cyan-50 text-mint",
  INCOMPLETE: "bg-amber-50 text-warning",
  INSUFFICIENT: "bg-orange-50 text-warning",
  REJECTED: "bg-red-50 text-risk",
  PRE_APPROVED: "bg-indigo-50 text-indigo-700",
  APPROVED: "bg-green-50 text-success",
  READY_TO_ISSUE: "bg-teal-50 text-mint",
  COMPLETED: "bg-slate-100 text-slate-700"
};

export function StatusBadge({ status }: { status: ApplicationSummary["status"] }) {
  return <span className={clsx("status-badge", colors[status])}>{status.replaceAll("_", " ")}</span>;
}

