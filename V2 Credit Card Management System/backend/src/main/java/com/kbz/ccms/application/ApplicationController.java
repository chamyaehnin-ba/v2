package com.kbz.ccms.application;

import com.kbz.ccms.application.dto.*;
import com.kbz.ccms.auth.*;
import com.kbz.ccms.common.ApiResponse;
import com.kbz.ccms.workflow.WorkflowService;
import com.kbz.ccms.workflow.dto.WorkflowActionRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/applications")
public class ApplicationController {
  private final ApplicationService applicationService;
  private final WorkflowService workflowService;
  private final AuthService authService;

  public ApplicationController(ApplicationService applicationService, WorkflowService workflowService, AuthService authService) {
    this.applicationService = applicationService;
    this.workflowService = workflowService;
    this.authService = authService;
  }

  @GetMapping
  public ApiResponse<Page<ApplicationSummaryResponse>> list(
      @RequestParam ApplicationStatus status,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "15") int size,
      @RequestHeader(value = "X-User-Id", required = false) String userId,
      @RequestHeader(value = "X-User-Role", required = false) String role,
      @RequestHeader(value = "X-Branch-Code", required = false) String branchCode) {
    CurrentUser user = authService.fromHeaders(userId, role, branchCode);
    Page<CreditCardApplication> apps = applicationService.list(status, user,
        PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "appliedAt")));
    return ApiResponse.ok(apps.map(ApplicationMapper::toSummary));
  }

  @PostMapping("/branch")
  public ApiResponse<ApplicationSummaryResponse> createBranchApplication(
      @Valid @RequestBody CreateBranchApplicationRequest request,
      @RequestHeader(value = "X-User-Id", required = false) String userId,
      @RequestHeader(value = "X-User-Role", required = false) String role,
      @RequestHeader(value = "X-Branch-Code", required = false) String branchCode) {
    CurrentUser user = authService.fromHeaders(userId, role, branchCode);
    return ApiResponse.ok(ApplicationMapper.toSummary(applicationService.createBranchApplication(request, user)));
  }

  @PostMapping("/{id}/workflow")
  public ApiResponse<ApplicationSummaryResponse> workflow(
      @PathVariable Long id,
      @Valid @RequestBody WorkflowActionRequest request,
      @RequestHeader(value = "X-User-Id", required = false) String userId,
      @RequestHeader(value = "X-User-Role", required = false) String role,
      @RequestHeader(value = "X-Branch-Code", required = false) String branchCode) {
    CurrentUser user = authService.fromHeaders(userId, role, branchCode);
    return ApiResponse.ok(ApplicationMapper.toSummary(workflowService.transition(
        id, request.action(), user.userId(), user.role(), request.assignedUserId(),
        request.checkerUserId(), request.approvedCreditLimit(), request.remark())));
  }
}

