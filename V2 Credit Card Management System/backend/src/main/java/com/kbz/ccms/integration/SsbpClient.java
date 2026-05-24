package com.kbz.ccms.integration;

import com.kbz.ccms.application.ApplicationStatus;
import org.springframework.stereotype.Component;

@Component
public class SsbpClient {
  public void syncStatus(String documentNo, ApplicationStatus status) {
    // Production implementation calls SSBP MyOrder and Inbox APIs.
  }
}

