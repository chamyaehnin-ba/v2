import { CheckCircle2, ClipboardList, FilePlus2, FileText, Inbox, Send, ShieldCheck, TriangleAlert, XCircle } from "lucide-react";
import type { UserRole } from "../stores/sessionStore";

export type QueueKey =
  | "hub"
  | "inbox"
  | "submit"
  | "approved"
  | "rejected"
  | "insufficient"
  | "incomplete"
  | "ready-to-issue"
  | "complete";

export interface NavItem {
  label: string;
  path: string;
  queue?: QueueKey;
  icon: typeof Inbox;
}

export interface NavSection {
  label: string;
  items: NavItem[];
}

const commonReports: NavSection = {
  label: "Report",
  items: [
    { label: "Daily Report", path: "/reports/daily", icon: FileText },
    { label: "User Progress Report", path: "/reports/user-progress", icon: ClipboardList }
  ]
};

export const roleNavigation: Record<UserRole, NavSection[]> = {
  SUPER: [
    {
      label: "Application Management",
      items: [
        { label: "Hub", path: "/applications/hub", queue: "hub", icon: ShieldCheck },
        { label: "Inbox", path: "/applications/inbox", queue: "inbox", icon: Inbox },
        { label: "Approved", path: "/applications/approved", queue: "approved", icon: CheckCircle2 },
        { label: "Rejected", path: "/applications/rejected", queue: "rejected", icon: XCircle },
        { label: "Insufficient", path: "/applications/insufficient", queue: "insufficient", icon: TriangleAlert },
        { label: "Incomplete", path: "/applications/incomplete", queue: "incomplete", icon: ClipboardList },
        { label: "Ready To Issue", path: "/applications/ready-to-issue", queue: "ready-to-issue", icon: Send },
        { label: "Complete", path: "/applications/complete", queue: "complete", icon: CheckCircle2 }
      ]
    },
    commonReports
  ],
  OPERATOR: [
    {
      label: "Application Management",
      items: [
        { label: "Inbox", path: "/applications/inbox", queue: "inbox", icon: Inbox },
        { label: "Submit", path: "/applications/submit", queue: "submit", icon: Send },
        { label: "Rejected", path: "/applications/rejected", queue: "rejected", icon: XCircle },
        { label: "Insufficient", path: "/applications/insufficient", queue: "insufficient", icon: TriangleAlert },
        { label: "Incomplete", path: "/applications/incomplete", queue: "incomplete", icon: ClipboardList },
        { label: "Ready To Issue", path: "/applications/ready-to-issue", queue: "ready-to-issue", icon: Send },
        { label: "Complete", path: "/applications/complete", queue: "complete", icon: CheckCircle2 }
      ]
    },
    commonReports
  ],
  BRANCH: [
    {
      label: "Application Management",
      items: [
        { label: "Create New Application", path: "/applications/new", icon: FilePlus2 },
        { label: "Submit", path: "/applications/submit", queue: "submit", icon: Send },
        { label: "Rejected", path: "/applications/rejected", queue: "rejected", icon: XCircle },
        { label: "Insufficient", path: "/applications/insufficient", queue: "insufficient", icon: TriangleAlert },
        { label: "Incomplete", path: "/applications/incomplete", queue: "incomplete", icon: ClipboardList },
        { label: "Ready To Issue", path: "/applications/ready-to-issue", queue: "ready-to-issue", icon: Send },
        { label: "Complete", path: "/applications/complete", queue: "complete", icon: CheckCircle2 }
      ]
    },
    commonReports
  ]
};

export const queueStatusMap: Record<QueueKey, string> = {
  hub: "HUB",
  inbox: "INBOX",
  submit: "PRE_APPROVED",
  approved: "APPROVED",
  rejected: "REJECTED",
  insufficient: "INSUFFICIENT",
  incomplete: "INCOMPLETE",
  "ready-to-issue": "READY_TO_ISSUE",
  complete: "COMPLETED"
};

