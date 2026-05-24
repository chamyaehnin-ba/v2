# Security Best Practices

- Authenticate against Active Directory / LDAP.
- Issue short-lived JWT access tokens and rotate signing secrets.
- Enforce RBAC in backend service methods, not only in the menu.
- Filter operator and branch records server-side.
- Store uploaded documents outside the web root with malware scanning and content-type validation.
- Validate file type and file size before persistence.
- Audit login failures, workflow transitions, notification attempts, and integration failures.
- Protect all production traffic with TLS.
- Use parameterized persistence through JPA repositories.
- Keep integration credentials in secret stores, not repository files.

