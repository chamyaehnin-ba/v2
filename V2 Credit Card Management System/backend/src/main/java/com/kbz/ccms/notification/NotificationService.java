package com.kbz.ccms.notification;

import com.kbz.ccms.application.ApplicationStatus;
import com.kbz.ccms.application.CreditCardApplication;
import com.kbz.ccms.workflow.WorkflowAction;
import java.util.EnumSet;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
  private static final EnumSet<ApplicationStatus> SSBP_SYNC_STATUSES = EnumSet.of(
      ApplicationStatus.INCOMPLETE,
      ApplicationStatus.INSUFFICIENT,
      ApplicationStatus.REJECTED,
      ApplicationStatus.APPROVED,
      ApplicationStatus.READY_TO_ISSUE,
      ApplicationStatus.COMPLETED
  );

  private final NotificationLogRepository repository;

  public NotificationService(NotificationLogRepository repository) {
    this.repository = repository;
  }

  public void notifyAfterWorkflow(CreditCardApplication app, WorkflowAction action) {
    String sms = smsMessage(app.getStatus());
    if (sms != null) {
      persistOnce(app.getId(), action, NotificationType.SMS, sms);
    }
    if (SSBP_SYNC_STATUSES.contains(app.getStatus())) {
      persistOnce(app.getId(), action, NotificationType.SSBP_MY_ORDER, app.getStatus().name());
      persistOnce(app.getId(), action, NotificationType.SSBP_INBOX, sms == null ? app.getStatus().name() : sms);
    }
  }

  private void persistOnce(Long applicationId, WorkflowAction action, NotificationType type, String message) {
    if (repository.existsByApplicationIdAndWorkflowActionAndType(applicationId, action.name(), type)) {
      return;
    }
    NotificationLog log = new NotificationLog();
    log.setApplicationId(applicationId);
    log.setWorkflowAction(action.name());
    log.setType(type);
    log.setStatus(NotificationStatus.PENDING);
    log.setMessage(message);
    repository.save(log);
  }

  private String smsMessage(ApplicationStatus status) {
    return switch (status) {
      case INCOMPLETE -> "[KBZ Bank] Your filled up ID number and uploaded ID are not matched. Please upload your ID again.";
      case INSUFFICIENT -> "[KBZ Bank] Insufficient amount at your bank account, please deposit 100% of your limit within 2 working days.";
      case REJECTED -> "[KBZ Bank] Your application is rejected. You can apply a new application again.";
      case APPROVED -> "[KBZ Bank] Your application is approved.";
      case READY_TO_ISSUE -> "[KBZ Bank] Credit Card created successfully. Please go to your selected branch to pick up your card with original NRC.";
      case COMPLETED -> "[KBZ Bank] Credit Card was successfully delivered. Thank you for using our credit card.";
      default -> null;
    };
  }
}

