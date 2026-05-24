package com.kbz.ccms.audit;

import jakarta.persistence.*;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "audit_events", indexes = {
    @Index(name = "idx_audit_application", columnList = "application_id"),
    @Index(name = "idx_audit_actor", columnList = "actor_user_id")
})
public class AuditEvent {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "application_id", nullable = false)
  private Long applicationId;

  @Column(name = "actor_user_id", nullable = false, length = 64)
  private String actorUserId;

  @Column(name = "actor_role", nullable = false, length = 40)
  private String actorRole;

  @Column(nullable = false, length = 64)
  private String action;

  @Column(name = "from_status", nullable = false, length = 32)
  private String fromStatus;

  @Column(name = "to_status", nullable = false, length = 32)
  private String toStatus;

  @Column(nullable = false, length = 2000)
  private String remark;

  @Column(name = "created_at", nullable = false)
  private Instant createdAt = Instant.now();
}
