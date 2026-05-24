import { useParams } from "react-router-dom";
import { FileSpreadsheet } from "lucide-react";

export function ReportPage() {
  const { type } = useParams();
  const title = type === "user-progress" ? "User Progress Report" : "Daily Report";

  return (
    <div className="space-y-4">
      <div className="flex items-end justify-between">
        <div>
          <h2 className="text-2xl font-semibold">{title}</h2>
          <p className="text-sm text-slate-500">Default date range is current device date with latest records first.</p>
        </div>
        <button className="inline-flex items-center gap-2 rounded bg-bank px-4 py-2 text-sm font-semibold text-white">
          <FileSpreadsheet className="h-4 w-4" />
          Export Excel
        </button>
      </div>
      <section className="rounded border border-slate-200 bg-white p-5 shadow-panel">
        <div className="grid grid-cols-5 gap-4">
          <input className="rounded border border-slate-300 px-3 py-2 text-sm" type="date" />
          <input className="rounded border border-slate-300 px-3 py-2 text-sm" type="date" />
          <input className="rounded border border-slate-300 px-3 py-2 text-sm" placeholder={type === "user-progress" ? "User Role" : "Status"} />
          <input className="rounded border border-slate-300 px-3 py-2 text-sm" placeholder={type === "user-progress" ? "User Name" : "Doc No / Name / NRC"} />
          <button className="rounded border border-slate-300 bg-white px-3 py-2 text-sm font-semibold">Search</button>
        </div>
      </section>
      <section className="rounded border border-slate-200 bg-white p-10 text-center text-sm text-slate-500 shadow-panel">
        Report results will appear here after search.
      </section>
    </div>
  );
}

