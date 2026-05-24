package com.kbz.ccms.integration;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IntegrationOutboxRepository extends JpaRepository<IntegrationOutboxEvent, Long> {}

