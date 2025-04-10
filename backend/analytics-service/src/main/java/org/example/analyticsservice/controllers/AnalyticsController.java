package org.example.analyticsservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.analyticsservice.dto.GeneralTransactionDataForChart;
import org.example.analyticsservice.dto.IncomeTransactionDataForChart;
import org.example.analyticsservice.dto.OutcomeTransactionDataForChart;
import org.example.analyticsservice.services.AnalyticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @Operation(summary = "Get All Transactions Data of user for period of time",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Found transactions", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GeneralTransactionDataForChart.class))}),
                    @ApiResponse(responseCode = "400", description = "Invalid period", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal Error", content = @Content),

            }
    )
    @GetMapping("/general")
    public ResponseEntity<List<GeneralTransactionDataForChart>> getAllTransactions(
            @RequestParam(required = false, defaultValue = "day", name = "period") String periodValue,
            @RequestHeader("X-User-Id") Long userId
    ) {
        try {
            Period period = Period.valueOf(periodValue.toUpperCase());

            if (period == Period.DAY) {
                return ResponseEntity.ok(List.of(analyticsService.getGeneralTransactionDataForLast24Hours(userId)));
            } else if (period == Period.MONTH) {
                return ResponseEntity.ok(analyticsService.getGeneralTransactionDataForLastMonth(userId));
            } else if (period == Period.YEAR) {
                return ResponseEntity.ok(analyticsService.getGeneralTransactionDataForLastYear(userId));
            }
//            else if(period == Period.ALL){
//                return ResponseEntity.ok(analyticsService.getGeneralTransactionDataForAllTime(userId));
//            }

        } catch (IllegalArgumentException e) {
            log.error("STRING CAST TO PERIOD FAILED: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.badRequest().build(); // TODO: complete
    }

    @Operation(summary = "Get Income Transactions Data of user for period of time",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Found transactions", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GeneralTransactionDataForChart.class))}),
                    @ApiResponse(responseCode = "400", description = "Invalid period", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal Error", content = @Content),

            }
    )
    @GetMapping("/income")
    public ResponseEntity<List<IncomeTransactionDataForChart>> getIncomeTransactions(
            @RequestParam(required = false, defaultValue = "day", name = "period") String periodValue,
            @RequestHeader("X-User-Id") Long userId
    ) {
        try {
            Period period = Period.valueOf(periodValue.toUpperCase());

            if (period == Period.DAY) {
                return ResponseEntity.ok(List.of(analyticsService.getIncomeTransactionDataForLast24Hours(userId)));
            } else if (period == Period.MONTH) {
                return ResponseEntity.ok(analyticsService.getIncomeTransactionDataForLastMonth(userId));
            } else if (period == Period.YEAR) {
                return ResponseEntity.ok(analyticsService.getIncomeTransactionDataForLastYear(userId));
            }
//            else if(period == Period.ALL){
//                return ResponseEntity.ok(analyticsService.getGeneralTransactionDataForAllTime(userId));
//            }

        } catch (IllegalArgumentException e) {
            log.error("STRING CAST TO PERIOD FAILED: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.badRequest().build(); // TODO: complete
    }

    @Operation(summary = "Get Outcome Transactions Data of user for period of time",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Found transactions", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GeneralTransactionDataForChart.class))}),
                    @ApiResponse(responseCode = "400", description = "Invalid period", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal Error", content = @Content),

            }
    )
    @GetMapping("/outcome")
    public ResponseEntity<List<OutcomeTransactionDataForChart>> getOutcomeTransactions(
            @RequestParam(required = false, defaultValue = "day", name = "period") String periodValue,
            @RequestHeader("X-User-Id") Long userId
    ) {
        try {
            Period period = Period.valueOf(periodValue.toUpperCase());

            if (period == Period.DAY) {
                return ResponseEntity.ok(List.of(analyticsService.getOutcomeTransactionDataForLast24Hours(userId)));
            } else if (period == Period.MONTH) {
                return ResponseEntity.ok(analyticsService.getOutcomeTransactionDataForLastMonth(userId));
            } else if (period == Period.YEAR) {
                return ResponseEntity.ok(analyticsService.getOutcomeTransactionDataForLastYear(userId));
            }
//            else if(period == Period.ALL){
//                return ResponseEntity.ok(analyticsService.getGeneralTransactionDataForAllTime(userId));
//            }

        } catch (IllegalArgumentException e) {
            log.error("STRING CAST TO PERIOD FAILED: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.badRequest().build(); // TODO: complete
    }

}

