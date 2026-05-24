INSERT INTO users (employee_id, display_name, role, branch_code)
VALUES
  ('super.user', 'Super User', 'SUPER', NULL),
  ('operator.user', 'Operator User', 'OPERATOR', NULL),
  ('branch.user', 'Branch User', 'BRANCH', '277')
ON CONFLICT (employee_id) DO NOTHING;

INSERT INTO credit_card_applications (
  document_no, applicant_name, nrc, mobile_number, email, date_of_birth,
  requested_credit_limit, status, stage, channel, pickup_location_code, pickup_location_name
)
VALUES
  ('277CC260525001', 'Mg Mg Aung', '12/LaKaNa(N)123456', '09123456789', 'mgmg@example.com', '1992-01-01', 5000000, 'HUB', 'SUPER', 'SSBP', '277', 'Branch 277'),
  ('C101CC260525002', 'Ma Hnin Yu', '10/MaLaMa(N)654321', '09987654321', 'hnin@example.com', '1990-02-10', 2000000, 'INBOX', 'OPERATOR', 'BRANCH', 'C101', 'KBZPay Centre C101');

