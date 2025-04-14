package org.example.transactionservice.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;


@FeignClient(name = "constraint-service")
public interface ConstraintServiceClient {

    @GetMapping("/by-category/{categoryId}/check-limits/")
    ResponseEntity<Void> checkConstraintLimitsForCategory(@PathVariable Long categoryId,
                                                          @RequestHeader("X-User-Id") Long userId
    );
}
