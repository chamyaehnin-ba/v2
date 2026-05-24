package com.kbz.ccms.application;

import com.kbz.ccms.application.dto.CreateBranchApplicationRequest;
import com.kbz.ccms.auth.CurrentUser;
import com.kbz.ccms.auth.UserRole;
import com.kbz.ccms.common.BusinessException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApplicationService {
  private final ApplicationRepository repository;

  public ApplicationService(ApplicationRepository repository) {
    this.repository = repository;
  }

  public Page<CreditCardApplication> list(ApplicationStatus status, CurrentUser user, Pageable pageable) {
    Pageable effective = pageable.getPageSize() > 0 ? pageable : PageRequest.of(0, 15, Sort.by(Sort.Direction.DESC, "appliedAt"));
    if (user.role() == UserRole.BRANCH) {
      return repository.findAll((root, query, cb) -> cb.and(
          cb.equal(root.get("status"), status),
          cb.equal(root.get("pickupLocationCode"), user.branchCode()),
          cb.isFalse(root.get("deleted"))
      ), effective);
    }
    if (status == ApplicationStatus.INBOX && user.role() == UserRole.OPERATOR) {
      return repository.findByStatusAndAssignedUserIdAndDeletedFalse(status, user.userId(), effective);
    }
    return repository.findByStatusAndDeletedFalse(status, effective);
  }

  @Transactional
  public CreditCardApplication createBranchApplication(CreateBranchApplicationRequest request, CurrentUser user) {
    if (user.role() != UserRole.BRANCH) {
      throw new BusinessException("BR-001", "Only Branch users can create branch applications.");
    }
    validateCardLimit(request.cardType(), request.requestedCreditLimit());
    validateAge(request.dateOfBirth());

    CreditCardApplication app = new CreditCardApplication();
    app.setDocumentNo(generateDocumentNumber(request.pickupLocationCode()));
    app.setApplicantName(request.applicantName());
    app.setNrc(request.nrc());
    app.setMobileNumber(request.mobileNumber());
    app.setEmail(request.email());
    app.setDateOfBirth(request.dateOfBirth());
    app.setRequestedCreditLimit(request.requestedCreditLimit());
    app.setStatus(ApplicationStatus.HUB);
    app.setStage(ApplicationStage.SUPER);
    app.setChannel(request.channel());
    app.setPickupLocationCode(request.pickupLocationCode());
    app.setPickupLocationName(request.pickupLocationName());
    return repository.save(app);
  }

  private void validateCardLimit(String cardType, BigDecimal limit) {
    BigDecimal max = new BigDecimal("5000000");
    if (limit.compareTo(max) > 0) {
      throw new BusinessException("MSG021", "The Requested Credit Limit exceeds the maximum allowed limit of 5,000,000 MMK.");
    }
    if ("PLATINUM".equalsIgnoreCase(cardType) && limit.compareTo(new BigDecimal("2000000")) < 0) {
      throw new BusinessException("CARD-002", "Minimum credit limit for Platinum Card shall be 2,000,000 MMK.");
    }
    if ("CLASSIC".equalsIgnoreCase(cardType) && limit.compareTo(new BigDecimal("200000")) < 0) {
      throw new BusinessException("CARD-003", "Minimum credit limit for Classic Card shall be 200,000 MMK.");
    }
  }

  private void validateAge(LocalDate dob) {
    int age = LocalDate.now().getYear() - dob.getYear();
    if (age < 18 || age > 65) {
      throw new BusinessException("MSG010", "Applicant age must be between 18 and 65 years old.");
    }
  }

  private String generateDocumentNumber(String locationCode) {
    String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
    long count = repository.count() + 1;
    return locationCode + "CC" + date + String.format("%03d", count);
  }
}

