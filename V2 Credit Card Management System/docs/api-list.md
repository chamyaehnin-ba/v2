# API List

## Auth

`POST /api/v1/auth/login`

Request:

```json
{ "employeeId": "super.user", "password": "secret" }
```

Response:

```json
{
  "success": true,
  "code": "0000",
  "message": "Success",
  "data": {
    "accessToken": "jwt",
    "employeeId": "super.user",
    "displayName": "Super User",
    "role": "SUPER",
    "branchCode": null,
    "permissions": ["HUB_ASSIGN", "FINAL_APPROVE"]
  }
}
```

## Applications

- `GET /api/v1/applications?status=HUB&page=0&size=15`
- `POST /api/v1/applications/branch`
- `POST /api/v1/applications/{id}/workflow`

Workflow request:

```json
{
  "action": "ASSIGN_TO_OPERATOR",
  "assignedUserId": "operator.user",
  "remark": "Assigned for review"
}
```

## Reports

- `GET /api/v1/reports/daily?fromDate=2026-05-25&toDate=2026-05-25&status=APPROVED`
- `GET /api/v1/reports/user-progress?fromDate=2026-05-25&toDate=2026-05-25&userRole=OPERATOR`

