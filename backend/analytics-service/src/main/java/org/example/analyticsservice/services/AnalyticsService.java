package org.example.analyticsservice.services;

import feign.FeignException;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.analyticsservice.dto.IncomeTransactionDataForChart;
import org.example.analyticsservice.dto.OutcomeTransactionDataForChart;
import org.example.analyticsservice.dto.TransactionDTO;
import org.example.analyticsservice.dto.GeneralTransactionDataForChart;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.util.function.Tuple3;
import reactor.util.function.Tuples;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

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

    public IncomeTransactionDataForChart getIncomeTransactionDataForLast24Hours(Long userId) {
        GeneralTransactionDataForChart generalData = getGeneralTransactionDataForLast24Hours(userId);

        return new IncomeTransactionDataForChart(generalData.getName(), generalData.getIncome());
    }

    public OutcomeTransactionDataForChart getOutcomeTransactionDataForLast24Hours(Long userId) {
        GeneralTransactionDataForChart generalData = getGeneralTransactionDataForLast24Hours(userId);

        return new OutcomeTransactionDataForChart(generalData.getName(), generalData.getOutcome());
    }

    public List<GeneralTransactionDataForChart> getGeneralTransactionDataForLastMonth(Long userId) {
        try {
            LocalDateTime today = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
            LocalDateTime yesterday = today.minusMonths(1);

            ResponseEntity<List<TransactionDTO>> response = transactionService.getAllTransactionsBetween(userId, yesterday, today);

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException(response.getStatusCode().toString());
            }

            if (!response.hasBody() || response.getBody() == null) {
                log.error("NO TRANSACTIONS FOUND IN RESPONSE FROM TRANSACTION SERVICE: {}", response.getStatusCode());
                throw new RuntimeException("No transactions found");
            }

            List<TransactionDTO> transactions = response.getBody();

            Map<LocalDate, double[]> dailySums = transactions.stream()
                    .collect(Collectors.groupingBy(
                            tx -> tx.getTime().toLocalDate(),
                            Collectors.reducing(
                                    new double[2], // [0] — income, [1] — outcome
                                    tx -> {
                                        double[] result = new double[2];
                                        if ("income".equalsIgnoreCase(tx.getType())) {
                                            result[0] = tx.getValue();
                                        } else if ("outcome".equalsIgnoreCase(tx.getType())) {
                                            result[1] = -tx.getValue();
                                        }
                                        return result;
                                    },
                                    (a, b) -> new double[]{a[0] + b[0], a[1] + b[1]}
                            )
                    ));

            List<GeneralTransactionDataForChart> result = dailySums.entrySet().stream()
                    .map(entry -> new GeneralTransactionDataForChart(
                            String.valueOf(entry.getKey().getDayOfMonth()),
                            entry.getValue()[0],
                            entry.getValue()[1]
                    ))
                    .toList();

            return result;
        } catch (FeignException exception) {
            log.error("TRANSACTION_SERVICE_ERROR: {}", exception.getMessage());
            throw new RuntimeException(exception);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<IncomeTransactionDataForChart> getIncomeTransactionDataForLastMonth(Long userId) {
        List<GeneralTransactionDataForChart> data = getGeneralTransactionDataForLastMonth(userId);

        return data.stream()
                .map(t -> new IncomeTransactionDataForChart(t.getName(), t.getIncome()))
                .toList();
    }

    public List<OutcomeTransactionDataForChart> getOutcomeTransactionDataForLastMonth(Long userId) {
        List<GeneralTransactionDataForChart> data = getGeneralTransactionDataForLastMonth(userId);

        return data.stream()
                .map(t -> new OutcomeTransactionDataForChart(t.getName(), t.getOutcome()))
                .toList();
    }

    public List<GeneralTransactionDataForChart> getGeneralTransactionDataForLastYear(Long userId) {
        try {
            LocalDateTime today = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
            LocalDateTime yesterday = today.minusMonths(1);

            ResponseEntity<List<TransactionDTO>> response = transactionService.getAllTransactionsBetween(userId, yesterday, today);

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException(response.getStatusCode().toString());
            }

            if (!response.hasBody() || response.getBody() == null) {
                log.error("NO TRANSACTIONS FOUND IN RESPONSE FROM TRANSACTION SERVICE: {}", response.getStatusCode());
                throw new RuntimeException("No transactions found");
            }

            List<TransactionDTO> transactions = response.getBody();

            Map<Month, double[]> monthlySum = transactions.stream()
                    .collect(Collectors.groupingBy(
                            tx -> tx.getTime().toLocalDate().getMonth(),
                            Collectors.reducing(
                                    new double[2], // [0] — income, [1] — outcome
                                    tx -> {
                                        double[] result = new double[2];
                                        if ("income".equalsIgnoreCase(tx.getType())) {
                                            result[0] = tx.getValue();
                                        } else if ("outcome".equalsIgnoreCase(tx.getType())) {
                                            result[1] = -tx.getValue();
                                        }
                                        return result;
                                    },
                                    (a, b) -> new double[]{a[0] + b[0], a[1] + b[1]}
                            )
                    ));

            List<GeneralTransactionDataForChart> result = monthlySum.entrySet().stream()
                    .map(entry -> new GeneralTransactionDataForChart(
                            entry.getKey().getDisplayName(TextStyle.SHORT, Locale.of("ru", "KZ")),
                            entry.getValue()[0],
                            entry.getValue()[1]
                    ))
                    .toList();

            return result;
        } catch (FeignException exception) {
            log.error("TRANSACTION_SERVICE_ERROR: {}", exception.getMessage());
            throw new RuntimeException(exception);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public List<IncomeTransactionDataForChart> getIncomeTransactionDataForLastYear(Long userId) {
        List<GeneralTransactionDataForChart> data = getGeneralTransactionDataForLastYear(userId);

        return data.stream()
                .map(t -> new IncomeTransactionDataForChart(t.getName(), t.getIncome()))
                .toList();
    }

    public List<OutcomeTransactionDataForChart> getOutcomeTransactionDataForLastYear(Long userId) {
        List<GeneralTransactionDataForChart> data = getGeneralTransactionDataForLastYear(userId);

        return data.stream()
                .map(t -> new OutcomeTransactionDataForChart(t.getName(), t.getOutcome()))
                .toList();
    }
}