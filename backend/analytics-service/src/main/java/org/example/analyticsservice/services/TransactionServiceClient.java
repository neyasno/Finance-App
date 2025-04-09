package org.example.analyticsservice.services;

import feign.Headers;
import feign.Param;
import org.example.analyticsservice.dto.TransactionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@FeignClient(name = "transaction-service")
public interface TransactionServiceClient {

    @GetMapping("/all")
    ResponseEntity<List<TransactionDTO>> getAllTransactions(@RequestHeader("X-User-Id") Long userId);

    @GetMapping("/by-time")
    ResponseEntity<List<TransactionDTO>> getAllTransactionsBetween(@RequestHeader("X-User-Id") Long userId,
                                                                   @RequestParam(name = "from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                                                                   @RequestParam(name = "to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to);
}
