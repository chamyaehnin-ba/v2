package com.kbz.ccms.reporting;

import com.kbz.ccms.common.ApiResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/reports")
public class ReportController {
  @GetMapping("/daily")
  public ApiResponse<Map<String, Object>> dailyReport(
      @RequestParam(required = false) LocalDate fromDate,
      @RequestParam(required = false) LocalDate toDate,
      @RequestParam(required = false) String status,
      @RequestParam(required = false) String docNo,
      @RequestParam(required = false) String nrc) {
    return ApiResponse.ok(Map.of(
        "fromDate", fromDate == null ? LocalDate.now() : fromDate,
        "toDate", toDate == null ? LocalDate.now() : toDate,
        "rows", List.of(),
        "exportSupported", true
    ));
  }

  @GetMapping("/user-progress")
  public ApiResponse<Map<String, Object>> userProgressReport(
      @RequestParam(required = false) LocalDate fromDate,
      @RequestParam(required = false) LocalDate toDate,
      @RequestParam(required = false) String userRole,
      @RequestParam(required = false) String userName) {
    return ApiResponse.ok(Map.of(
        "fromDate", fromDate == null ? LocalDate.now() : fromDate,
        "toDate", toDate == null ? LocalDate.now() : toDate,
        "rows", List.of(),
        "exportSupported", true
    ));
  }
}

