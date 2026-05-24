package com.kbz.ccms.application;

import com.kbz.ccms.application.dto.ApplicationSummaryResponse;

public final class ApplicationMapper {
  private ApplicationMapper() {}

  public static ApplicationSummaryResponse toSummary(CreditCardApplication app) {
    return new ApplicationSummaryResponse(
        app.getId(),
        app.getDocumentNo(),
        app.getApplicantName(),
        app.getNrc(),
        app.getRequestedCreditLimit(),
        app.getApprovedCreditLimit(),
        app.getAppliedAt(),
        app.getStage(),
        app.getStatus(),
        app.getLoopCount(),
        app.getChannel(),
        app.getApplicationType(),
        app.getPickupLocationCode()
    );
  }
}

