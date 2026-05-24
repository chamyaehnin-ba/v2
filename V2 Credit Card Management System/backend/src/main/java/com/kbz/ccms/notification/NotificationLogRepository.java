package com.kbz.ccms.notification;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationLogRepository extends JpaRepository<NotificationLog, Long> {
  boolean existsByApplicationIdAndWorkflowActionAndType(Long applicationId, String workflowAction, NotificationType type);
}

