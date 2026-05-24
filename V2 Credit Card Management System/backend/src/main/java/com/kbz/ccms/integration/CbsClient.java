package com.kbz.ccms.integration;

import org.springframework.stereotype.Component;

@Component
public class CbsClient {
  public boolean isCustomerEligible(String nrc) {
    return true;
  }

  public boolean isAccountEligible(String accountNumber) {
    return accountNumber != null && accountNumber.matches("\\d{17}");
  }
}

