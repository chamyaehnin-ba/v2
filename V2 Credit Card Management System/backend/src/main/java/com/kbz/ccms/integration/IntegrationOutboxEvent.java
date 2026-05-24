package com.kbz.ccms.integration;

import jakarta.persistence.*;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "integration_outbox", indexes = {
    @Index(name = "idx_outbox_status_retry", columnList = "status,next_retry_at")
})
public class IntegrationOutboxEvent {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "application_id")
  private Long applicationId;

  @Enumerated(EnumType.STRING)
  @Column(name = "target_system", nullable = false, length = 32)
  private IntegrationTarget targetSystem;

  @Column(name = "event_type", nullable = false, length = 64)
  private String eventType;

  @Column(nullable = false, columnDefinition = "jsonb")
  private String payload;

  @Column(nullable = false, length = 32)
  private String status = "PENDING";

  @Column(name = "retry_count", nullable = false)
  private int retryCount = 0;

  @Column(name = "next_retry_at")
  private Instant nextRetryAt;

  @Column(name = "created_at", nullable = false)
  private Instant createdAt = Instant.now();

  @Column(name = "updated_at", nullable = false)
  private Instant updatedAt = Instant.now();
}

