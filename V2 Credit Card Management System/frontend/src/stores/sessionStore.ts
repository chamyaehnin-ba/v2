import { create } from "zustand";

export type UserRole = "SUPER" | "OPERATOR" | "BRANCH";

export interface SessionUser {
  employeeId: string;
  displayName: string;
  role: UserRole;
  branchCode?: string;
}

interface SessionState {
  user: SessionUser;
  setRole: (role: UserRole) => void;
}

export const useSessionStore = create<SessionState>((set) => ({
  user: {
    employeeId: "super.user",
    displayName: "Super User",
    role: "SUPER"
  },
  setRole: (role) =>
    set({
      user:
        role === "BRANCH"
          ? { employeeId: "branch.user", displayName: "Branch User", role, branchCode: "277" }
          : role === "OPERATOR"
            ? { employeeId: "operator.user", displayName: "Operator User", role }
            : { employeeId: "super.user", displayName: "Super User", role }
    })
}));

