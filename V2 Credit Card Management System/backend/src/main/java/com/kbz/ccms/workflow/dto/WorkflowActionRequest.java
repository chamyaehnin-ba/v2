package com.kbz.ccms.workflow.dto;

import com.kbz.ccms.workflow.WorkflowAction;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record WorkflowActionRequest(
    @NotNull WorkflowAction action,
    String assignedUserId,
    String checkerUserId,
    BigDecimal approvedCreditLimit,
    @NotBlank String remark
) {}

