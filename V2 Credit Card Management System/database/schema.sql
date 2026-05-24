CREATE TYPE user_role AS ENUM ('SUPER', 'OPERATOR', 'BRANCH');
CREATE TYPE application_status AS ENUM ('HUB', 'INBOX', 'INCOMPLETE', 'INSUFFICIENT', 'REJECTED', 'PRE_APPROVED', 'APPROVED', 'READY_TO_ISSUE', 'COMPLETED');
CREATE TYPE application_stage AS ENUM ('SUPER', 'OPERATOR', 'BRANCH', 'SYSTEM');
CREATE TYPE application_channel AS ENUM ('SSBP', 'BRANCH', 'KBZPAY_CENTRE', 'SMART_HR');
CREATE TYPE notification_type AS ENUM ('SMS', 'SSBP_MY_ORDER', 'SSBP_INBOX');
CREATE TYPE notification_status AS ENUM ('PENDING', 'SENT', 'FAILED');

CREATE TABLE users (
  id BIGSERIAL PRIMARY KEY,
  employee_id VARCHAR(64) NOT NULL UNIQUE,
  display_name VARCHAR(160) NOT NULL,
  role user_role NOT NULL,
  branch_code VARCHAR(32),
  active BOOLEAN NOT NULL DEFAULT TRUE,
  created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  deleted BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE credit_card_applications (
  id BIGSERIAL PRIMARY KEY,
  document_no VARCHAR(32) NOT NULL UNIQUE,
  applicant_name VARCHAR(160) NOT NULL,
  nrc VARCHAR(64) NOT NULL,
  mobile_number VARCHAR(20) NOT NULL,
  email VARCHAR(160),
  date_of_birth DATE,
  requested_credit_limit NUMERIC(18,2) NOT NULL,
  approved_credit_limit NUMERIC(18,2),
  status VARCHAR(32) NOT NULL,
  stage VARCHAR(32) NOT NULL,
  channel VARCHAR(32) NOT NULL,
  application_type VARCHAR(32) NOT NULL DEFAULT 'NEW',
  loop_count INTEGER NOT NULL DEFAULT 0,
  assigned_user_id VARCHAR(64),
  checker_user_id VARCHAR(64),
  pickup_location_code VARCHAR(32) NOT NULL,
  pickup_location_name VARCHAR(120) NOT NULL,
  applied_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  deleted BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE application_documents (
  id BIGSERIAL PRIMARY KEY,
  application_id BIGINT NOT NULL REFERENCES credit_card_applications(id),
  document_type VARCHAR(64) NOT NULL,
  file_name VARCHAR(255) NOT NULL,
  content_type VARCHAR(120) NOT NULL,
  file_size BIGINT NOT NULL,
  storage_key VARCHAR(500) NOT NULL,
  created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  deleted BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE audit_events (
  id BIGSERIAL PRIMARY KEY,
  application_id BIGINT NOT NULL REFERENCES credit_card_applications(id),
  actor_user_id VARCHAR(64) NOT NULL,
  actor_role VARCHAR(40) NOT NULL,
  action VARCHAR(64) NOT NULL,
  from_status VARCHAR(32) NOT NULL,
  to_status VARCHAR(32) NOT NULL,
  remark VARCHAR(2000) NOT NULL,
  created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE notification_logs (
  id BIGSERIAL PRIMARY KEY,
  application_id BIGINT NOT NULL REFERENCES credit_card_applications(id),
  workflow_action VARCHAR(64) NOT NULL,
  type VARCHAR(32) NOT NULL,
  status VARCHAR(32) NOT NULL DEFAULT 'PENDING',
  message VARCHAR(2000) NOT NULL,
  error_message VARCHAR(500),
  created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  UNIQUE(application_id, workflow_action, type)
);

CREATE TABLE integration_outbox (
  id BIGSERIAL PRIMARY KEY,
  application_id BIGINT REFERENCES credit_card_applications(id),
  target_system VARCHAR(32) NOT NULL,
  event_type VARCHAR(64) NOT NULL,
  payload JSONB NOT NULL,
  status VARCHAR(32) NOT NULL DEFAULT 'PENDING',
  retry_count INTEGER NOT NULL DEFAULT 0,
  next_retry_at TIMESTAMPTZ,
  created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX idx_cc_app_status ON credit_card_applications(status);
CREATE INDEX idx_cc_app_assigned ON credit_card_applications(assigned_user_id);
CREATE INDEX idx_cc_app_branch ON credit_card_applications(pickup_location_code);
CREATE INDEX idx_cc_app_applied_at ON credit_card_applications(applied_at DESC);
CREATE INDEX idx_audit_application ON audit_events(application_id);
CREATE INDEX idx_notification_application ON notification_logs(application_id);
CREATE INDEX idx_outbox_status_retry ON integration_outbox(status, next_retry_at);

