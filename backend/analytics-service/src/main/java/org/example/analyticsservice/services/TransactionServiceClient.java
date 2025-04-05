package org.example.analyticsservice.services;

import feign.Headers;
import feign.Param;
import org.example.analyticsservice.dto.TransactionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@FeignClient(name = "transaction-service")
public interface TransactionServiceClient {

    @GetMapping("/by-time")
    @Headers("X-User-Id: {userId}")
    ResponseEntity<List<TransactionDTO>> getTransactionsByTime(
            @RequestParam(required = false) @DateTimeFormat LocalDateTime from,
            @RequestParam(required = false) @DateTimeFormat LocalDateTime to,
            @Param("userId") Long userId);
}
