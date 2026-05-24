package com.kbz.ccms.audit;

import com.kbz.ccms.application.ApplicationStatus;
import com.kbz.ccms.auth.UserRole;
import com.kbz.ccms.workflow.WorkflowAction;
import org.springframework.stereotype.Service;

@Service
public class AuditService {
  private final AuditEventRepository repository;

  public AuditService(AuditEventRepository repository) {
    this.repository = repository;
  }

  public void record(Long applicationId, String actorUserId, UserRole role, WorkflowAction action,
      ApplicationStatus fromStatus, ApplicationStatus toStatus, String remark) {
    AuditEvent event = new AuditEvent();
    event.setApplicationId(applicationId);
    event.setActorUserId(actorUserId);
    event.setActorRole(role.name());
    event.setAction(action.name());
    event.setFromStatus(fromStatus.name());
    event.setToStatus(toStatus.name());
    event.setRemark(remark);
    repository.save(event);
  }
}

