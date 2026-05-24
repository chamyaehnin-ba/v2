package com.kbz.ccms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CcmsApplication {
  public static void main(String[] args) {
    SpringApplication.run(CcmsApplication.class, args);
  }
}

