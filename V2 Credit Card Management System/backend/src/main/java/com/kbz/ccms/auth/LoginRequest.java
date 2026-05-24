package com.kbz.ccms.auth;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    @NotBlank String employeeId,
    @NotBlank String password
) {}

