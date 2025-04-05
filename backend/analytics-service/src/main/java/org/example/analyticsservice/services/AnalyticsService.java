package org.example.analyticsservice.services;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.analyticsservice.dto.TransactionDTO;
import org.example.analyticsservice.dto.TransactionDataForChart;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final TransactionServiceClient transactionService;

    public List<TransactionDataForChart> getTransactionDataForChart(LocalDateTime from, LocalDateTime to, Long userId) {
        try {
            ResponseEntity<List<TransactionDTO>> response = transactionService.getTransactionsByTime(from, to, userId);

            if(!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException(response.getStatusCode().toString());
            }

            List<TransactionDTO> transactions = response.getBody();

            var data = transactions.stream()
                    .map(transaction ->
                            new TransactionDataForChart(
                                    transaction.getTitle(),
                                    transaction.getValue() * (transaction.getType().equalsIgnoreCase("income")? 1 : -1)))
                    .toList();

            return data;

        }catch (FeignException exception){
            log.error("TRANSACTION_SERVICE_ERROR: {}", exception.getMessage());
            throw new RuntimeException(exception);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
