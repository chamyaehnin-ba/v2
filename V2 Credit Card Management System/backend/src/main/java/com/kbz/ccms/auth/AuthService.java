package com.kbz.ccms.auth;

import com.kbz.ccms.common.BusinessException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
  public LoginResponse login(LoginRequest request) {
    if (request.employeeId().isBlank() || request.password().isBlank()) {
      throw new BusinessException("MSG001", "Invalid Employee ID or Password.");
    }
    UserRole role = inferRole(request.employeeId());
    return new LoginResponse(
        "dev-jwt-token-" + request.employeeId(),
        request.employeeId(),
        request.employeeId().toUpperCase(),
        role,
        role == UserRole.BRANCH ? "277" : null,
        permissions(role)
    );
  }

  public CurrentUser fromHeaders(String userId, String role, String branchCode) {
    UserRole resolved = role == null ? UserRole.SUPER : UserRole.valueOf(role.toUpperCase());
    return new CurrentUser(userId == null ? "super.user" : userId, "CCMS User", resolved, branchCode);
  }

  private UserRole inferRole(String employeeId) {
    String id = employeeId.toLowerCase();
    if (id.contains("operator")) return UserRole.OPERATOR;
    if (id.contains("branch")) return UserRole.BRANCH;
    return UserRole.SUPER;
  }

  private List<String> permissions(UserRole role) {
    return switch (role) {
      case SUPER -> List.of("HUB_ASSIGN", "FINAL_APPROVE", "VIEW_ALL_REPORTS");
      case OPERATOR -> List.of("PROCESS_INBOX", "SUBMIT_APPROVAL", "VIEW_ALL_REPORTS");
      case BRANCH -> List.of("CREATE_BRANCH_APPLICATION", "RESUBMIT", "CARD_PICKUP", "VIEW_BRANCH_REPORTS");
    };
  }
}

