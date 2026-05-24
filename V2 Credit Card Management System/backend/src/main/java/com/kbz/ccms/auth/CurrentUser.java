package com.kbz.ccms.auth;

public record CurrentUser(
    String userId,
    String displayName,
    UserRole role,
    String branchCode
) {}

