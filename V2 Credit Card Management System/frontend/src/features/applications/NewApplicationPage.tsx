import { zodResolver } from "@hookform/resolvers/zod";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import type React from "react";
import { useForm } from "react-hook-form";
import { z } from "zod";
import { createBranchApplication } from "./applicationService";

const schema = z.object({
  applicantName: z.string().min(1),
  nrc: z.string().min(5),
  mobileNumber: z.string().regex(/^09(\d{7}|\d{9})$/),
  email: z.string().email(),
  dateOfBirth: z.string().min(1),
  requestedCreditLimit: z.coerce.number().min(200000).max(5000000),
  channel: z.enum(["BRANCH", "KBZPAY_CENTRE"]),
  pickupLocationCode: z.string().min(1),
  pickupLocationName: z.string().min(1),
  cardBrand: z.enum(["VISA", "MPU_UPI", "MPU"]),
  cardType: z.enum(["PLATINUM", "CLASSIC", "GOLD"]),
  temporaryHoldingAccount: z.string().regex(/^\d{17}$/),
  autoDebitAccount: z.string().regex(/^\d{17}$/)
});

type FormValues = z.infer<typeof schema>;

export function NewApplicationPage() {
  const queryClient = useQueryClient();
  const { register, handleSubmit, formState: { errors }, reset } = useForm<FormValues>({
    resolver: zodResolver(schema),
    defaultValues: {
      channel: "BRANCH",
      pickupLocationCode: "277",
      pickupLocationName: "Branch 277",
      cardBrand: "VISA",
      cardType: "CLASSIC"
    }
  });
  const mutation = useMutation({
    mutationFn: createBranchApplication,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["applications"] });
      reset();
    }
  });

  return (
    <form className="space-y-6" onSubmit={handleSubmit((values) => mutation.mutate(values))}>
      <div>
        <h2 className="text-2xl font-semibold">Create New Application</h2>
        <p className="text-sm text-slate-500">Branch intake with CBS-ready customer, account, and card validation fields.</p>
      </div>
      <section className="rounded border border-slate-200 bg-white p-5 shadow-panel">
        <h3 className="mb-4 text-base font-semibold">Personal Details</h3>
        <div className="grid grid-cols-3 gap-4">
          <Field label="Applicant Name" error={errors.applicantName?.message}><input {...register("applicantName")} /></Field>
          <Field label="NRC / Passport No." error={errors.nrc?.message}><input {...register("nrc")} /></Field>
          <Field label="Mobile Number" error={errors.mobileNumber?.message}><input {...register("mobileNumber")} placeholder="09xxxxxxxxx" /></Field>
          <Field label="Email Address" error={errors.email?.message}><input {...register("email")} /></Field>
          <Field label="Date of Birth" error={errors.dateOfBirth?.message}><input type="date" {...register("dateOfBirth")} /></Field>
          <Field label="Application Channel" error={errors.channel?.message}>
            <select {...register("channel")}><option value="BRANCH">Branch</option><option value="KBZPAY_CENTRE">KBZPay Centre</option></select>
          </Field>
        </div>
      </section>
      <section className="rounded border border-slate-200 bg-white p-5 shadow-panel">
        <h3 className="mb-4 text-base font-semibold">Card Application Information</h3>
        <div className="grid grid-cols-3 gap-4">
          <Field label="Card Brand" error={errors.cardBrand?.message}>
            <select {...register("cardBrand")}><option value="VISA">VISA</option><option value="MPU_UPI">MPU-UPI</option><option value="MPU">MPU</option></select>
          </Field>
          <Field label="Card Type" error={errors.cardType?.message}>
            <select {...register("cardType")}><option value="PLATINUM">Platinum</option><option value="CLASSIC">Classic</option><option value="GOLD">Gold</option></select>
          </Field>
          <Field label="Credit Limit" error={errors.requestedCreditLimit?.message}><input type="number" {...register("requestedCreditLimit")} /></Field>
          <Field label="Temporary Holding Account" error={errors.temporaryHoldingAccount?.message}><input {...register("temporaryHoldingAccount")} /></Field>
          <Field label="Auto Debit Account" error={errors.autoDebitAccount?.message}><input {...register("autoDebitAccount")} /></Field>
          <Field label="Pickup Location Code" error={errors.pickupLocationCode?.message}><input {...register("pickupLocationCode")} /></Field>
          <Field label="Pickup Location Name" error={errors.pickupLocationName?.message}><input {...register("pickupLocationName")} /></Field>
        </div>
      </section>
      <div className="flex justify-end gap-3">
        <button type="button" className="rounded border border-slate-300 bg-white px-4 py-2 text-sm font-semibold" onClick={() => reset()}>Clear</button>
        <button type="submit" className="rounded bg-bank px-4 py-2 text-sm font-semibold text-white" disabled={mutation.isPending}>
          Submit To HUB
        </button>
      </div>
    </form>
  );
}

function Field({ label, error, children }: { label: string; error?: string; children: React.ReactElement }) {
  return (
    <label className="space-y-1 text-sm">
      <span className="font-semibold text-slate-700">{label}</span>
      <div className="[&_input]:w-full [&_input]:rounded [&_input]:border [&_input]:border-slate-300 [&_input]:px-3 [&_input]:py-2 [&_select]:w-full [&_select]:rounded [&_select]:border [&_select]:border-slate-300 [&_select]:px-3 [&_select]:py-2">
        {children}
      </div>
      {error ? <span className="text-xs text-risk">{error}</span> : null}
    </label>
  );
}
