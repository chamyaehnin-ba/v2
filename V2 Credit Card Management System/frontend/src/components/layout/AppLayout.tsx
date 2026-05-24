import { Outlet } from "react-router-dom";
import { Sidebar } from "./Sidebar";
import { useSessionStore, type UserRole } from "../../stores/sessionStore";

export function AppLayout() {
  const { user, setRole } = useSessionStore();

  return (
    <div className="min-h-screen bg-slate-100 text-ink">
      <Sidebar />
      <main className="min-h-screen pl-72">
        <header className="sticky top-0 z-10 flex h-16 items-center justify-between border-b border-slate-200 bg-white px-6 shadow-panel">
          <div>
            <p className="text-xs font-semibold uppercase tracking-wide text-bank">Centralized Credit Card Management System</p>
            <h1 className="text-lg font-semibold">Credit Card Operations Portal</h1>
          </div>
          <div className="flex items-center gap-3">
            <select
              className="rounded border border-slate-300 bg-white px-3 py-2 text-sm"
              value={user.role}
              onChange={(event) => setRole(event.target.value as UserRole)}
              aria-label="Switch role"
            >
              <option value="SUPER">Super User</option>
              <option value="OPERATOR">Operator User</option>
              <option value="BRANCH">Branch User</option>
            </select>
            <div className="text-right">
              <p className="text-sm font-semibold">{user.displayName}</p>
              <p className="text-xs text-slate-500">{user.role}{user.branchCode ? ` - Branch ${user.branchCode}` : ""}</p>
            </div>
          </div>
        </header>
        <section className="p-6">
          <Outlet />
        </section>
      </main>
    </div>
  );
}

