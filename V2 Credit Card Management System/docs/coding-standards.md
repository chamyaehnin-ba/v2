# Coding Standards

- Keep workflow transitions in backend services only.
- Keep UI queue actions thin and API-driven.
- Use strict TypeScript, typed API responses, React Query for server state, and Zod for form validation.
- Use DTO validation on every write API.
- Use standard API envelope: `success`, `code`, `message`, `data`, `timestamp`.
- Every workflow action must write audit history.
- Every customer-visible status change must enqueue notification and SSBP sync.
- Soft delete domain records and preserve audit events.
- Default listing page size is 15 with latest first.
- Use environment variables for external URLs and secrets.
- Never hard-code production credentials.

