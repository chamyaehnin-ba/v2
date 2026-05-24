import { NavLink } from "react-router-dom";
import clsx from "clsx";
import { queueStatusMap, roleNavigation } from "../../lib/navigation";
import { useSessionStore } from "../../stores/sessionStore";

const sampleQueueCounts: Record<string, number> = {
  HUB: 3,
  INBOX: 3,
  PRE_APPROVED: 2,
  APPROVED: 2,
  REJECTED: 2,
  INSUFFICIENT: 2,
  INCOMPLETE: 2,
  READY_TO_ISSUE: 2,
  COMPLETED: 3
};

export function Sidebar() {
  const user = useSessionStore((state) => state.user);
  const sections = roleNavigation[user.role];

  return (
    <aside className="fixed inset-y-0 left-0 z-20 w-72 border-r border-slate-200 bg-white">
      <div className="flex h-16 items-center border-b border-slate-200 px-5">
        <div className="flex h-10 w-10 items-center justify-center rounded bg-bank text-sm font-bold text-white">CC</div>
        <div className="ml-3">
          <p className="text-sm font-bold">CCMS</p>
          <p className="text-xs text-slate-500">KBZ Banking Operations</p>
        </div>
      </div>
      <nav className="h-[calc(100vh-4rem)] overflow-y-auto px-3 py-4">
        {sections.map((section) => (
          <div key={section.label} className="mb-5">
            <p className="mb-2 px-3 text-xs font-bold uppercase tracking-wide text-slate-500">{section.label}</p>
            <div className="space-y-1">
              {section.items.map((item) => {
                const Icon = item.icon;
                const count = item.queue ? sampleQueueCounts[queueStatusMap[item.queue]] : undefined;
                return (
                  <NavLink
                    key={item.path}
                    to={item.path}
                    className={({ isActive }) =>
                      clsx(
                        "flex items-center gap-3 rounded px-3 py-2 text-sm font-medium",
                        isActive ? "bg-bank text-white" : "text-slate-700 hover:bg-slate-100"
                      )
                    }
                  >
                    <Icon className="h-4 w-4" />
                    <span className="min-w-0 flex-1">{item.label}</span>
                    {count === undefined ? null : (
                      <span className="rounded-full bg-slate-200 px-2 py-0.5 text-xs font-bold text-slate-700 group-[.active]:bg-white/20">
                        {count}
                      </span>
                    )}
                  </NavLink>
                );
              })}
            </div>
          </div>
        ))}
      </nav>
    </aside>
  );
}
