package com.kbz.ccms.auth;

import java.util.List;

public record LoginResponse(
    String accessToken,
    String employeeId,
    String displayName,
    UserRole role,
    String branchCode,
    List<String> permissions
) {}

