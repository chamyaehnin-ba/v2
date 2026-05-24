package com.kbz.ccms.application.dto;

import com.kbz.ccms.application.ApplicationChannel;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateBranchApplicationRequest(
    @NotBlank String applicantName,
    @NotBlank String nrc,
    @Pattern(regexp = "09\\d{7}|09\\d{9}") String mobileNumber,
    @Email String email,
    @NotNull LocalDate dateOfBirth,
    @NotNull @DecimalMin("200000") @DecimalMax("5000000") BigDecimal requestedCreditLimit,
    @NotNull ApplicationChannel channel,
    @NotBlank String pickupLocationCode,
    @NotBlank String pickupLocationName,
    @NotBlank String cardBrand,
    @NotBlank String cardType,
    @NotBlank String temporaryHoldingAccount,
    @NotBlank String autoDebitAccount
) {}

