package org.example.analyticsservice.controllers;

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

    @GetMapping("/general")
    public ResponseEntity<List<GeneralTransactionDataForChart>> getAllTransactions(
            @RequestParam(required = false, defaultValue = "day", name = "period") String periodValue,
            @RequestHeader("X-User-Id") Long userId
    ) {
        try{
            Period period = Period.valueOf(periodValue.toUpperCase());

            if(period == Period.DAY){
                return ResponseEntity.ok(List.of(analyticsService.getGeneralTransactionDataForLast24Hours(userId)));
            }
            else if(period == Period.MONTH){
                return ResponseEntity.ok(analyticsService.getGeneralTransactionDataForLastMonth(userId));
            }

        } catch (IllegalArgumentException e) {
            log.error("STRING CAST TO PERIOD FAILED: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error( e.getMessage());
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.badRequest().build(); // TODO: complete
    }

//    @GetMapping("/income")
//    public ResponseEntity<List<IncomeTransactionDataForChart>> getIncomeTransactions(@RequestHeader("X-User-Id") Long userId) {
//    }
//
//    @GetMapping("/outcome")
//    public ResponseEntity<List<OutcomeTransactionDataForChart>> getOutcomeTransactions(@RequestHeader("X-User-Id") Long userId) {
//    }
}

