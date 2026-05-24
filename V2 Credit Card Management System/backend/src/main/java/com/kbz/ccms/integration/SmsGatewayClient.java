package com.kbz.ccms.integration;

import org.springframework.stereotype.Component;

@Component
public class SmsGatewayClient {
  public void send(String mobileNumber, String message) {
    // Production implementation calls the enterprise SMS gateway.
  }
}

