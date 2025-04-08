package org.example.analyticsservice.services;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.analyticsservice.dto.IncomeTransactionDataForChart;
import org.example.analyticsservice.dto.OutcomeTransactionDataForChart;
import org.example.analyticsservice.dto.TransactionDTO;
import org.example.analyticsservice.dto.GeneralTransactionDataForChart;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final TransactionServiceClient transactionService;

    public GeneralTransactionDataForChart getGeneralTransactionDataForLast24Hours(Long userId) {
        try {
            LocalDateTime today = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
            LocalDateTime yesterday = today.minusHours(24);

            ResponseEntity<List<TransactionDTO>> response = transactionService.getAllTransactionsBetween(userId, yesterday, today);

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException(response.getStatusCode().toString());
            }

            if (!response.hasBody() || response.getBody() == null) {
                log.error("NO TRANSACTIONS FOUND IN RESPONSE FROM TRANSACTION SERVICE: {}", response.getStatusCode());
                throw new RuntimeException("No transactions found");
            }

            List<TransactionDTO> transactions = response.getBody();

            String title = today.toLocalDate().toString();
            Double income = 0.0;
            Double outcome = 0.0;

            for (TransactionDTO transaction : transactions) {
                if (transaction.getType().equalsIgnoreCase("income")) {
                    income += transaction.getValue();
                } else {
                    outcome -= transaction.getValue();
                }
            }

            return new GeneralTransactionDataForChart(title, income, outcome);

        } catch (FeignException exception) {
            log.error("TRANSACTION_SERVICE_ERROR: {}", exception.getMessage());
            throw new RuntimeException(exception);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public IncomeTransactionDataForChart getIncomeTransactionDataForLast24Hours(Long userId){
        try{
            LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
            LocalDateTime yesterday = now.minusDays(1);

            ResponseEntity<List<TransactionDTO>> response = transactionService.getAllTransactionsBetween(userId, yesterday, now);

            if(!response.hasBody() || response.getBody() == null){
                log.error("NO TRANSACTIONS FOUND IN RESPONSE FROM TRANSACTION SERVICE: {}", response.getStatusCode());
                throw new RuntimeException("No transactions found");
            }

            List<TransactionDTO> transactionDTOS = response.getBody();

            String title = now.toLocalDate().toString();
            Double income = 0.0;

            for(var transaction : transactionDTOS){
                if(!transaction.getType().equalsIgnoreCase("income")){
                    continue;
                }

                income += transaction.getValue();
            }

            return new IncomeTransactionDataForChart(title, income);




        }catch(FeignException e){
            log.error("TRANSACTION_SERVICE_ERROR: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public OutcomeTransactionDataForChart getOutcomeTransactionDataForLast24Hours(Long userId){
        try{
            LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
            LocalDateTime yesterday = now.minusDays(1);

            ResponseEntity<List<TransactionDTO>> response = transactionService.getAllTransactionsBetween(userId, yesterday, now);

            if(!response.hasBody() || response.getBody() == null){
                log.error("NO TRANSACTIONS FOUND IN RESPONSE FROM TRANSACTION SERVICE: {}", response.getStatusCode());
                throw new RuntimeException("No transactions found");
            }

            List<TransactionDTO> transactionDTOS = response.getBody();

            String title = now.toLocalDate().toString();
            Double outcome = 0.0;

            for(var transaction : transactionDTOS){
                if(!transaction.getType().equalsIgnoreCase("outcome")){
                    continue;
                }

                outcome -= transaction.getValue();
            }

            return new OutcomeTransactionDataForChart(title, outcome);




        }catch(FeignException e){
            log.error("TRANSACTION_SERVICE_ERROR: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}