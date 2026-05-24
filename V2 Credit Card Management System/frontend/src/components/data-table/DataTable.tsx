import type { ReactNode } from "react";

export interface Column<T> {
  key: string;
  title: string;
  render: (row: T, index: number) => ReactNode;
}

interface DataTableProps<T> {
  columns: Column<T>[];
  rows: T[];
  loading?: boolean;
  emptyText?: string;
}

export function DataTable<T>({ columns, rows, loading, emptyText = "No records found" }: DataTableProps<T>) {
  return (
    <div className="overflow-hidden rounded border border-slate-200 bg-white shadow-panel">
      <table className="min-w-full divide-y divide-slate-200">
        <thead className="bg-slate-50">
          <tr>
            {columns.map((column) => (
              <th key={column.key} className="px-4 py-3 text-left text-xs font-bold uppercase text-slate-500">
                {column.title}
              </th>
            ))}
          </tr>
        </thead>
        <tbody className="divide-y divide-slate-100">
          {loading ? (
            <tr>
              <td colSpan={columns.length} className="px-4 py-10 text-center text-sm text-slate-500">Loading records...</td>
            </tr>
          ) : rows.length === 0 ? (
            <tr>
              <td colSpan={columns.length} className="px-4 py-10 text-center text-sm text-slate-500">{emptyText}</td>
            </tr>
          ) : (
            rows.map((row, index) => (
              <tr key={index} className="hover:bg-slate-50">
                {columns.map((column) => (
                  <td key={column.key} className="whitespace-nowrap px-4 py-3 text-sm text-slate-700">
                    {column.render(row, index)}
                  </td>
                ))}
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
  );
}

