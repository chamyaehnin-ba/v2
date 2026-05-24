package com.kbz.ccms.application;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ApplicationRepository extends JpaRepository<CreditCardApplication, Long>, JpaSpecificationExecutor<CreditCardApplication> {
  Page<CreditCardApplication> findByStatusAndDeletedFalse(ApplicationStatus status, Pageable pageable);

  Page<CreditCardApplication> findByStatusAndAssignedUserIdAndDeletedFalse(ApplicationStatus status, String userId, Pageable pageable);

  long countByAssignedUserIdAndDeletedFalse(String userId);
}

