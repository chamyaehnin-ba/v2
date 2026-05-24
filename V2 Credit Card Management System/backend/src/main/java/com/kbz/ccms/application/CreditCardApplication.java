package com.kbz.ccms.application;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "credit_card_applications", indexes = {
    @Index(name = "idx_cc_app_status", columnList = "status"),
    @Index(name = "idx_cc_app_doc_no", columnList = "document_no", unique = true),
    @Index(name = "idx_cc_app_assigned", columnList = "assigned_user_id"),
    @Index(name = "idx_cc_app_branch", columnList = "pickup_location_code")
})
public class CreditCardApplication {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "document_no", nullable = false, unique = true, length = 32)
  private String documentNo;

  @Column(name = "applicant_name", nullable = false, length = 160)
  private String applicantName;

  @Column(nullable = false, length = 64)
  private String nrc;

  @Column(name = "mobile_number", nullable = false, length = 20)
  private String mobileNumber;

  @Column(length = 160)
  private String email;

  @Column(name = "date_of_birth")
  private LocalDate dateOfBirth;

  @Column(name = "requested_credit_limit", nullable = false, precision = 18, scale = 2)
  private BigDecimal requestedCreditLimit;

  @Column(name = "approved_credit_limit", precision = 18, scale = 2)
  private BigDecimal approvedCreditLimit;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 32)
  private ApplicationStatus status = ApplicationStatus.HUB;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 32)
  private ApplicationStage stage = ApplicationStage.SUPER;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 32)
  private ApplicationChannel channel;

  @Column(name = "application_type", nullable = false, length = 32)
  private String applicationType = "NEW";

  @Column(name = "loop_count", nullable = false)
  private int loopCount = 0;

  @Column(name = "assigned_user_id", length = 64)
  private String assignedUserId;

  @Column(name = "checker_user_id", length = 64)
  private String checkerUserId;

  @Column(name = "pickup_location_code", nullable = false, length = 32)
  private String pickupLocationCode;

  @Column(name = "pickup_location_name", nullable = false, length = 120)
  private String pickupLocationName;

  @Column(name = "applied_at", nullable = false)
  private Instant appliedAt = Instant.now();

  @Column(name = "created_at", nullable = false)
  private Instant createdAt = Instant.now();

  @Column(name = "updated_at", nullable = false)
  private Instant updatedAt = Instant.now();

  @Column(nullable = false)
  private boolean deleted = false;

  @PreUpdate
  void preUpdate() {
    updatedAt = Instant.now();
  }
}
