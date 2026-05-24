package com.kbz.ccms.application.dto;

import com.kbz.ccms.application.*;
import java.math.BigDecimal;
import java.time.Instant;

public record ApplicationSummaryResponse(
    Long id,
    String documentNo,
    String applicantName,
    String nrc,
    BigDecimal requestedCreditLimit,
    BigDecimal approvedCreditLimit,
    Instant appliedAt,
    ApplicationStage stage,
    ApplicationStatus status,
    int loopCount,
    ApplicationChannel channel,
    String applicationType,
    String pickupLocationCode
) {}

