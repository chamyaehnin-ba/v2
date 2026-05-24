export interface ApplicationSummary {
  id: number;
  documentNo: string;
  applicantName: string;
  nrc: string;
  requestedCreditLimit: number;
  approvedCreditLimit?: number;
  appliedAt: string;
  stage: "SUPER" | "OPERATOR" | "BRANCH" | "SYSTEM";
  status:
    | "HUB"
    | "INBOX"
    | "INCOMPLETE"
    | "INSUFFICIENT"
    | "REJECTED"
    | "PRE_APPROVED"
    | "APPROVED"
    | "READY_TO_ISSUE"
    | "COMPLETED";
  loopCount: number;
  channel: "SSBP" | "BRANCH" | "KBZPAY_CENTRE" | "SMART_HR";
  applicationType: string;
  pickupLocationCode: string;
}

