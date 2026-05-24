package com.kbz.ccms.notification;

import jakarta.persistence.*;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "notification_logs", indexes = {
    @Index(name = "idx_notification_application", columnList = "application_id"),
    @Index(name = "idx_notification_unique_action", columnList = "application_id,workflow_action,type", unique = true)
})
public class NotificationLog {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "application_id", nullable = false)
  private Long applicationId;

  @Column(name = "workflow_action", nullable = false, length = 64)
  private String workflowAction;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 32)
  private NotificationType type;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 32)
  private NotificationStatus status = NotificationStatus.PENDING;

  @Column(nullable = false, length = 2000)
  private String message;

  @Column(name = "error_message", length = 500)
  private String errorMessage;

  @Column(name = "created_at", nullable = false)
  private Instant createdAt = Instant.now();
}
