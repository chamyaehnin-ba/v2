import { useMemo, useState } from "react";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { useParams } from "react-router-dom";
import { DataTable, type Column } from "../../components/data-table/DataTable";
import { queueStatusMap, type QueueKey } from "../../lib/navigation";
import { useSessionStore } from "../../stores/sessionStore";
import { getApplications, runWorkflow } from "./applicationService";
import { StatusBadge } from "./StatusBadge";
import type { ApplicationSummary } from "./types";

const actionsByStatus: Record<string, { label: string; action: string }[]> = {
  HUB: [{ label: "Assign", action: "ASSIGN_TO_OPERATOR" }],
  INBOX: [
    { label: "Incomplete", action: "MARK_INCOMPLETE" },
    { label: "Insufficient", action: "MARK_INSUFFICIENT" },
    { label: "Reject", action: "REJECT_APPLICATION" },
    { label: "Submit", action: "SUBMIT_FOR_APPROVAL" }
  ],
  PRE_APPROVED: [{ label: "Approve", action: "FINAL_APPROVE" }],
  APPROVED: [{ label: "Ready", action: "MARK_READY_TO_ISSUE" }],
  READY_TO_ISSUE: [{ label: "Complete", action: "COMPLETE_PICKUP" }],
  INCOMPLETE: [{ label: "Resubmit", action: "RESUBMIT_TO_HUB" }],
  INSUFFICIENT: [{ label: "Resubmit", action: "RESUBMIT_TO_HUB" }]
};

export function QueuePage() {
  const { queue = "hub" } = useParams();
  const status = queueStatusMap[queue as QueueKey] ?? "HUB";
  const user = useSessionStore((state) => state.user);
  const queryClient = useQueryClient();
  const [page] = useState(0);
  const { data, isLoading } = useQuery({
    queryKey: ["applications", status, page, user.role],
    queryFn: () => getApplications(status, page)
  });

  const mutation = useMutation({
    mutationFn: ({ id, action }: { id: number; action: string }) =>
      runWorkflow(id, {
        action,
        assignedUserId: action === "ASSIGN_TO_OPERATOR" ? "operator.user" : undefined,
        checkerUserId: action === "SUBMIT_FOR_APPROVAL" ? "super.user" : undefined,
        remark: `${action} by ${user.displayName}`
      }),
    onSuccess: () => queryClient.invalidateQueries({ queryKey: ["applications"] })
  });

  const columns = useMemo<Column<ApplicationSummary>[]>(() => [
    { key: "no", title: "No", render: (_, index) => index + 1 },
    { key: "documentNo", title: "Document No", render: (row) => row.documentNo },
    { key: "applicantName", title: "Applicant Name", render: (row) => row.applicantName },
    { key: "nrc", title: "NRC", render: (row) => row.nrc },
    { key: "limit", title: "Credit Limit", render: (row) => `${Number(row.requestedCreditLimit).toLocaleString()} MMK` },
    { key: "approved", title: "Approved Amount", render: (row) => row.approvedCreditLimit ? `${Number(row.approvedCreditLimit).toLocaleString()} MMK` : "-" },
    { key: "stage", title: "Stage", render: (row) => row.stage },
    { key: "status", title: "Status", render: (row) => <StatusBadge status={row.status} /> },
    { key: "loop", title: "Loop", render: (row) => row.loopCount },
    { key: "channel", title: "Channel", render: (row) => row.channel },
    {
      key: "actions",
      title: "Action",
      render: (row) => (
        <div className="flex gap-2">
          {(actionsByStatus[row.status] ?? []).map((item) => (
            <button
              key={item.action}
              className="rounded bg-bank px-3 py-1.5 text-xs font-semibold text-white disabled:opacity-50"
              disabled={mutation.isPending}
              onClick={() => mutation.mutate({ id: row.id, action: item.action })}
            >
              {item.label}
            </button>
          ))}
        </div>
      )
    }
  ], [mutation, user.displayName]);

  return (
    <div className="space-y-4">
      <div className="flex items-end justify-between">
        <div>
          <h2 className="text-2xl font-semibold">{status.replaceAll("_", " ")} Queue</h2>
          <p className="text-sm text-slate-500">Showing applications available for {user.role.toLowerCase()} workflow operations.</p>
        </div>
        <div className="flex gap-2">
          <input className="rounded border border-slate-300 px-3 py-2 text-sm" placeholder="Search Doc No / NRC" />
          <button className="rounded border border-slate-300 bg-white px-3 py-2 text-sm font-semibold">Export Excel</button>
        </div>
      </div>
      <DataTable columns={columns} rows={data?.content ?? []} loading={isLoading} />
      <p className="text-sm text-slate-500">
        Showing {(data?.number ?? 0) * (data?.size ?? 15) + 1}-{Math.min(((data?.number ?? 0) + 1) * (data?.size ?? 15), data?.totalElements ?? 0)} of {data?.totalElements ?? 0} records
      </p>
    </div>
  );
}

