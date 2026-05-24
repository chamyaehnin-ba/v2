# Centralized Credit Card Management System (CCMS)

Enterprise-grade web application scaffold for KBZ Bank credit card application processing, based on the supplied Functional Specification Document.

## Modules

- `backend` - Java Spring Boot API with workflow, RBAC, audit, reporting, notification, and integration boundaries.
- `frontend` - React + TypeScript + Tailwind UI with role-based left sidebar navigation.
- `database` - PostgreSQL DDL and seed reference data.
- `docs` - architecture, API, workflow, ERD, deployment, and security design.
- `deployment` - Docker Compose and environment templates.

## Core Roles

- Super User
- Operator User
- Branch User

## Core Queues

HUB, Inbox, Pre-Approved, Approved, Rejected, Insufficient, Incomplete, Ready To Issue, Completed, Submit.

