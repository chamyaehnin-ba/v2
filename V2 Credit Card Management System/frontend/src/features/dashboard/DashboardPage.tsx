import { CheckCircle2, ClipboardList, Inbox, Send, ShieldCheck, TriangleAlert, XCircle } from "lucide-react";

const metrics = [
  { label: "Hub", value: 0, icon: ShieldCheck, color: "text-bank" },
  { label: "Inbox", value: 0, icon: Inbox, color: "text-mint" },
  { label: "Pre-Approved", value: 0, icon: ClipboardList, color: "text-indigo-700" },
  { label: "Approved", value: 0, icon: CheckCircle2, color: "text-success" },
  { label: "Insufficient", value: 0, icon: TriangleAlert, color: "text-warning" },
  { label: "Rejected", value: 0, icon: XCircle, color: "text-risk" },
  { label: "Ready To Issue", value: 0, icon: Send, color: "text-mint" },
  { label: "Completed", value: 0, icon: CheckCircle2, color: "text-slate-700" }
];

export function DashboardPage() {
  return (
    <div className="space-y-6">
      <div>
        <h2 className="text-2xl font-semibold">Operations Dashboard</h2>
        <p className="text-sm text-slate-500">Central view of application queues, branch issuance, and workflow health.</p>
      </div>
      <div className="grid grid-cols-4 gap-4">
        {metrics.map((metric) => {
          const Icon = metric.icon;
          return (
            <div key={metric.label} className="rounded border border-slate-200 bg-white p-4 shadow-panel">
              <div className="flex items-center justify-between">
                <p className="text-sm font-semibold text-slate-600">{metric.label}</p>
                <Icon className={`h-5 w-5 ${metric.color}`} />
              </div>
              <p className="mt-4 text-3xl font-bold">{metric.value}</p>
            </div>
          );
        })}
      </div>
      <div className="grid grid-cols-2 gap-4">
        <section className="rounded border border-slate-200 bg-white p-5 shadow-panel">
          <h3 className="text-base font-semibold">Workflow SLA</h3>
          <div className="mt-4 h-3 rounded bg-slate-100">
            <div className="h-3 w-2/3 rounded bg-mint" />
          </div>
          <p className="mt-3 text-sm text-slate-500">Queue health is calculated from application age, assignment age, and loop count.</p>
        </section>
        <section className="rounded border border-slate-200 bg-white p-5 shadow-panel">
          <h3 className="text-base font-semibold">Integration Health</h3>
          <div className="mt-4 grid grid-cols-2 gap-3 text-sm">
            {["CBS API", "SSBP API", "SMS Gateway", "SVBOII"].map((item) => (
              <div key={item} className="flex items-center justify-between rounded border border-slate-200 px-3 py-2">
                <span>{item}</span>
                <span className="font-semibold text-success">Ready</span>
              </div>
            ))}
          </div>
        </section>
      </div>
    </div>
  );
}

