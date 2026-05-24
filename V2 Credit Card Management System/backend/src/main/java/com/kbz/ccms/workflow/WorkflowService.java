package com.kbz.ccms.workflow;

import com.kbz.ccms.application.*;
import com.kbz.ccms.audit.AuditService;
import com.kbz.ccms.auth.UserRole;
import com.kbz.ccms.common.BusinessException;
import com.kbz.ccms.notification.NotificationService;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class WorkflowService {
  private final ApplicationRepository applications;
  private final AuditService auditService;
  private final NotificationService notificationService;

  public WorkflowService(ApplicationRepository applications, AuditService auditService,
      NotificationService notificationService) {
    this.applications = applications;
    this.auditService = auditService;
    this.notificationService = notificationService;
  }

  @Transactional
  public CreditCardApplication transition(Long applicationId, WorkflowAction action, String actorUserId,
      UserRole actorRole, String assignedUserId, String checkerUserId, BigDecimal approvedCreditLimit, String remark) {
    CreditCardApplication app = applications.findById(applicationId)
        .filter(a -> !a.isDeleted())
        .orElseThrow(() -> new BusinessException("1004", "Application not found"));

    ApplicationStatus from = app.getStatus();
    assertAllowed(app, action, actorRole, actorUserId);

    switch (action) {
      case ASSIGN_TO_OPERATOR -> {
        require(actorRole == UserRole.SUPER, "HUB-001", "Only Super Users can assign HUB records.");
        require(StringUtils.hasText(assignedUserId), "HUB-004", "Operator is required.");
        app.setStatus(ApplicationStatus.INBOX);
        app.setStage(ApplicationStage.OPERATOR);
        app.setAssignedUserId(assignedUserId);
      }
      case MARK_INCOMPLETE -> {
        app.setStatus(ApplicationStatus.INCOMPLETE);
        app.setStage(roleStage(actorRole));
      }
      case MARK_INSUFFICIENT -> {
        app.setStatus(ApplicationStatus.INSUFFICIENT);
        app.setStage(roleStage(actorRole));
      }
      case REJECT_APPLICATION -> {
        app.setStatus(ApplicationStatus.REJECTED);
        app.setStage(roleStage(actorRole));
      }
      case SUBMIT_FOR_APPROVAL -> {
        require(actorRole == UserRole.OPERATOR, "MBX-010", "Only Operator can submit approval request.");
        require(StringUtils.hasText(checkerUserId), "MBX-010", "Checker is required.");
        app.setStatus(ApplicationStatus.PRE_APPROVED);
        app.setStage(ApplicationStage.SUPER);
        app.setCheckerUserId(checkerUserId);
        app.setAssignedUserId(checkerUserId);
      }
      case FINAL_APPROVE -> {
        require(actorRole == UserRole.SUPER, "MBX-009", "Only Super User can approve.");
        require(app.getStatus() == ApplicationStatus.PRE_APPROVED, "WF-001", "Only Pre-Approved applications can be approved.");
        app.setStatus(ApplicationStatus.APPROVED);
        app.setStage(ApplicationStage.BRANCH);
        app.setApprovedCreditLimit(approvedCreditLimit == null ? app.getRequestedCreditLimit() : approvedCreditLimit);
      }
      case MARK_READY_TO_ISSUE -> {
        require(actorRole == UserRole.BRANCH, "BR-003", "Only Branch can mark Ready To Issue.");
        app.setStatus(ApplicationStatus.READY_TO_ISSUE);
        app.setStage(ApplicationStage.BRANCH);
      }
      case COMPLETE_PICKUP -> {
        require(actorRole == UserRole.BRANCH, "BR-005", "Only Branch can complete pickup.");
        app.setStatus(ApplicationStatus.COMPLETED);
        app.setStage(ApplicationStage.BRANCH);
      }
      case RESUBMIT_TO_HUB -> {
        require(actorRole == UserRole.BRANCH, "BR-001", "Only Branch can resubmit customer applications.");
        require(from == ApplicationStatus.INCOMPLETE || from == ApplicationStatus.INSUFFICIENT, "WF-002",
            "Only Incomplete or Insufficient applications can be resubmitted.");
        app.setStatus(ApplicationStatus.HUB);
        app.setStage(ApplicationStage.SUPER);
        app.setAssignedUserId(null);
        app.setLoopCount(app.getLoopCount() + 1);
      }
    }

    CreditCardApplication saved = applications.save(app);
    auditService.record(saved.getId(), actorUserId, actorRole, action, from, saved.getStatus(), remark);
    notificationService.notifyAfterWorkflow(saved, action);
    return saved;
  }

  private void assertAllowed(CreditCardApplication app, WorkflowAction action, UserRole role, String actorUserId) {
    if (role == UserRole.OPERATOR && app.getAssignedUserId() != null && !app.getAssignedUserId().equals(actorUserId)
        && action != WorkflowAction.SUBMIT_FOR_APPROVAL) {
      throw new BusinessException("MBX-003", "Application access is restricted to assigned users only.");
    }
    if (app.getStatus() == ApplicationStatus.COMPLETED) {
      throw new BusinessException("BR-009", "Completed applications are read-only.");
    }
  }

  private ApplicationStage roleStage(UserRole role) {
    return switch (role) {
      case SUPER -> ApplicationStage.SUPER;
      case OPERATOR -> ApplicationStage.OPERATOR;
      case BRANCH -> ApplicationStage.BRANCH;
    };
  }

  private void require(boolean condition, String code, String message) {
    if (!condition) {
      throw new BusinessException(code, message);
    }
  }
}

