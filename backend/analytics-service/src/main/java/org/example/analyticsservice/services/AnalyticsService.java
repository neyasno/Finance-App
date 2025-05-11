package org.example.analyticsservice.services;

import feign.FeignException;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.analyticsservice.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final TransactionServiceClient transactionService;

    public List<GeneralTransactionDataForChart> getGeneralTransactionDataForLast24Hours(Long userId) {
        try {
            LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
            LocalDateTime start = now.minusHours(23);

            ResponseEntity<List<TransactionDTO>> response = transactionService.getAllTransactionsBetween(userId, start, now.plusHours(1));

            validateBody(response);

            List<TransactionDTO> transactions = response.getBody();
            if (transactions == null) {
                transactions = Collections.emptyList();
            }

            Map<LocalDateTime, double[]> hourlySums = transactions.stream()
                    .collect(Collectors.groupingBy(
                            tx -> tx.getTime().truncatedTo(ChronoUnit.HOURS),
                            Collectors.reducing(
                                    new double[2],
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

            List<GeneralTransactionDataForChart> result = new ArrayList<>();
            for (int i = 0; i < 24; i++) {
                LocalDateTime hour = start.plusHours(i);
                double[] values = hourlySums.getOrDefault(hour, new double[]{0.0, 0.0});

                result.add(new GeneralTransactionDataForChart(
                        String.format("%02d:00", hour.getHour()),
                        values[0],
                        values[1]
                ));
            }

            return result;

        } catch (FeignException exception) {
            log.error("TRANSACTION_SERVICE_ERROR: {}", exception.getMessage());
            throw new RuntimeException(exception);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<IncomeTransactionDataForChart> getIncomeTransactionDataForLast24Hours(Long userId) {
        List<GeneralTransactionDataForChart> data = getGeneralTransactionDataForLast24Hours(userId);

        return data.stream()
                .map(t -> new IncomeTransactionDataForChart(t.getName(), t.getIncome()))
                .toList();
    }

    public List<OutcomeTransactionDataForChart> getOutcomeTransactionDataForLast24Hours(Long userId) {
        List<GeneralTransactionDataForChart> data = getGeneralTransactionDataForLast24Hours(userId);

        return data.stream()
                .map(t -> new OutcomeTransactionDataForChart(t.getName(), t.getOutcome()))
                .toList();
    }

    public List<GeneralTransactionDataForChart> getGeneralTransactionDataForLastMonth(Long userId) {
        try {
            LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).plusSeconds(5);
            LocalDateTime thirtyDaysAgo = now.minusDays(30);

            ResponseEntity<List<TransactionDTO>> response = transactionService.getAllTransactionsBetween(userId, thirtyDaysAgo, now);

            validateBody(response);

            List<TransactionDTO> transactions = response.getBody();

            Map<LocalDate, double[]> dailySums = transactions.stream()
                    .collect(Collectors.groupingBy(
                            tx -> tx.getTime().toLocalDate(),
                            Collectors.reducing(
                                    new double[2],
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

            LocalDate startDate = thirtyDaysAgo.toLocalDate();
            LocalDate endDate = now.toLocalDate();

            for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
                dailySums.putIfAbsent(date, new double[]{0.0, 0.0});
            }

            List<GeneralTransactionDataForChart> result = dailySums.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
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
            LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).plusSeconds(5);
            LocalDateTime yearAgo = now.minusYears(1).plusDays(1);

            ResponseEntity<List<TransactionDTO>> response = transactionService.getAllTransactionsBetween(userId, yearAgo, now);

            validateBody(response);

            List<TransactionDTO> transactions = response.getBody();
            if (transactions == null) transactions = Collections.emptyList();

            Map<YearMonth, double[]> monthlySums = transactions.stream()
                    .collect(Collectors.groupingBy(
                            tx -> YearMonth.from(tx.getTime()),
                            Collectors.reducing(
                                    new double[2],
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

            List<YearMonth> allMonths = new ArrayList<>();
            YearMonth startMonth = YearMonth.from(yearAgo);
            YearMonth endMonth = YearMonth.from(now);
            for (YearMonth month = startMonth; !month.isAfter(endMonth); month = month.plusMonths(1)) {
                allMonths.add(month);
            }

            List<GeneralTransactionDataForChart> result = allMonths.stream()
                    .map(month -> {
                        double[] values = monthlySums.getOrDefault(month, new double[]{0.0, 0.0});
                        String monthName = month.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
                        return new GeneralTransactionDataForChart(monthName, values[0], values[1]);
                    })
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

    public List<GeneralTransactionDataForChart> getGeneralTransactionDataForAllTime(Long userId) {
        try {
            ResponseEntity<List<TransactionDTO>> response = transactionService.getAllTransactions(userId);

            validateBody(response);

            List<TransactionDTO> transactions = response.getBody();

            Map<Year, double[]> yearlySum = transactions.stream()
                    .collect(Collectors.groupingBy(
                            tx -> Year.from(tx.getTime()),
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


            List<GeneralTransactionDataForChart> result = yearlySum.entrySet().stream()
                    .map(entry -> new GeneralTransactionDataForChart(
                            entry.getKey().toString(),
                            entry.getValue()[0],
                            entry.getValue()[1]
                    ))
                    .toList();

            return result;
        } catch (FeignException exception) {
            log.error("TRANSACTION_SERVICE_ERROR: {}", exception.getMessage());
            throw new RuntimeException(exception);
        } catch (Exception e) {
            log.error("TRANSACTION_SERVICE_ERROR DURING COMPUTING: {}", e.getMessage());
            throw new RuntimeException(e);
        }

    }

    public List<IncomeTransactionDataForChart> getIncomeTransactionDataForAllTime(Long userId) {
        List<GeneralTransactionDataForChart> data = getGeneralTransactionDataForAllTime(userId);

        return data.stream()
                .map(t -> new IncomeTransactionDataForChart(t.getName(), t.getIncome()))
                .toList();
    }

    public List<OutcomeTransactionDataForChart> getOutcomeTransactionDataForAllTime(Long userId) {
        List<GeneralTransactionDataForChart> data = getGeneralTransactionDataForAllTime(userId);

        return data.stream()
                .map(t -> new OutcomeTransactionDataForChart(t.getName(), t.getOutcome()))
                .toList();
    }

    public List<CategoryTransactionDataForChart> getCategoriesTransactionDataForLast24Hours(Long userId) {
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
        LocalDateTime start = now.minusHours(23);

        ResponseEntity<List<TransactionDTO>> response = transactionService.getAllTransactionsBetween(userId, start, now.plusHours(1));

        validateBody(response);

        List<TransactionDTO> transactions = response.getBody();
        if (transactions == null) transactions = Collections.emptyList();

        Map<Long, String> categoryIdToTitle = getCategoryIdToTitleMapping(userId);

        // Группируем по часу -> по категории -> сумма
        Map<LocalDateTime, Map<String, Double>> hourlyCategorySums = new HashMap<>();

        for (TransactionDTO tx : transactions) {
            LocalDateTime hour = tx.getTime().truncatedTo(ChronoUnit.HOURS);
            String category = categoryIdToTitle.getOrDefault(tx.getCategoryId(), "No Category");

            double value = tx.getValue();
            if ("outcome".equalsIgnoreCase(tx.getType())) {
                value = -value;
            }

            hourlyCategorySums
                    .computeIfAbsent(hour, k -> new HashMap<>())
                    .merge(category, value, Double::sum);
        }

        // Формируем результат, заполняем пропущенные часы
        List<CategoryTransactionDataForChart> result = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            LocalDateTime hour = start.plusHours(i);
            String hourLabel = String.format("%02d", hour.getHour());

            Map<String, Double> data = hourlyCategorySums.getOrDefault(hour, new HashMap<>());
            result.add(new CategoryTransactionDataForChart(hourLabel, data));
        }

        return result;
    }

    public List<CategoryTransactionDataForChart> getCategoriesTransactionDataForLastMonth(Long userId) {
        try {
            LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).plusSeconds(5);
            LocalDateTime thirtyDaysAgo = now.minusDays(30);

            ResponseEntity<List<TransactionDTO>> response = transactionService.getAllTransactionsBetween(userId, thirtyDaysAgo, now);

            validateBody(response);

            List<TransactionDTO> transactions = response.getBody();
            if (transactions == null) transactions = Collections.emptyList();

            Map<Long, String> categoryIdToTitle = getCategoryIdToTitleMapping(userId);

            // Группируем по дате -> по категории -> сумма
            Map<LocalDate, Map<String, Double>> dailyCategorySums = new HashMap<>();

            for (TransactionDTO tx : transactions) {
                LocalDate day = tx.getTime().toLocalDate();
                String category = categoryIdToTitle.getOrDefault(tx.getCategoryId(), "No Category");

                double value = tx.getValue();

                if ("outcome".equalsIgnoreCase(tx.getType())) {
                    value = -value;
                }

                dailyCategorySums
                        .computeIfAbsent(day, k -> new HashMap<>())
                        .merge(category, value, Double::sum);
            }

            LocalDate startDate = thirtyDaysAgo.toLocalDate();
            LocalDate endDate = now.toLocalDate();

            for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
                dailyCategorySums.putIfAbsent(date, new HashMap<>());
            }

            List<CategoryTransactionDataForChart> result = dailyCategorySums.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .map(entry -> new CategoryTransactionDataForChart(
                            String.valueOf(entry.getKey().getDayOfMonth()),
                            entry.getValue()
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

    public List<CategoryTransactionDataForChart> getCategoriesTransactionDataForLastYear(Long userId) {
        try {
            LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).plusSeconds(5);
            LocalDateTime yearAgo = now.minusMonths(12).withDayOfMonth(1).truncatedTo(ChronoUnit.DAYS);

            ResponseEntity<List<TransactionDTO>> response = transactionService.getAllTransactionsBetween(userId, yearAgo, now);

            validateBody(response);

            List<TransactionDTO> transactions = response.getBody();
            if (transactions == null) transactions = Collections.emptyList();

            Map<Long, String> categoryIdToTitle = getCategoryIdToTitleMapping(userId);

            // Группируем по месяцу -> по категории -> сумма
            Map<YearMonth, Map<String, Double>> monthlyCategorySums = new HashMap<>();

            for (TransactionDTO tx : transactions) {
                YearMonth month = YearMonth.from(tx.getTime());
                String category = categoryIdToTitle.getOrDefault(tx.getCategoryId(), "No Category");

                double value = tx.getValue();

                if ("outcome".equalsIgnoreCase(tx.getType())) {
                    value = -value;
                }

                monthlyCategorySums
                        .computeIfAbsent(month, k -> new HashMap<>())
                        .merge(category, value, Double::sum);
            }

            YearMonth startMonth = YearMonth.from(yearAgo);
            YearMonth endMonth = YearMonth.from(now);

            List<CategoryTransactionDataForChart> result = new ArrayList<>();

            for (YearMonth month = startMonth; !month.isAfter(endMonth); month = month.plusMonths(1)) {
                Map<String, Double> data = monthlyCategorySums.getOrDefault(month, new HashMap<>());
                result.add(new CategoryTransactionDataForChart(month.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH), data));
            }

            return result;
        } catch (FeignException exception) {
            log.error("TRANSACTION_SERVICE_ERROR: {}", exception.getMessage());
            throw new RuntimeException(exception);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<CategoryTransactionDataForChart> getCategoriesTransactionDataForAllTime(Long userId) {
        try {
            ResponseEntity<List<TransactionDTO>> response = transactionService.getAllTransactions(userId);

            validateBody(response);

            List<TransactionDTO> transactions = response.getBody();
            if (transactions == null) transactions = Collections.emptyList();

            Map<Long, String> categoryIdToTitle = getCategoryIdToTitleMapping(userId);

            // Группируем по году -> по категории -> сумма
            Map<Integer, Map<String, Double>> yearlyCategorySums = new HashMap<>();

            for (TransactionDTO tx : transactions) {
                int year = tx.getTime().getYear();

                String category = categoryIdToTitle.getOrDefault(tx.getCategoryId(), "No Category");
                double value = tx.getValue();

                if ("outcome".equalsIgnoreCase(tx.getType())) {
                    value = -value;
                }

                yearlyCategorySums
                        .computeIfAbsent(year, y -> new HashMap<>())
                        .merge(category, value, Double::sum);
            }

            List<CategoryTransactionDataForChart> result = yearlyCategorySums.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .map(entry -> new CategoryTransactionDataForChart(
                            String.valueOf(entry.getKey()),
                            entry.getValue()
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

    private Map<Long, String> getCategoryIdToTitleMapping(Long userId) {
        ResponseEntity<List<CategoryDTO>> categoryResponse = transactionService.getAllCategories(userId);
        List<CategoryDTO> categories = categoryResponse.getBody();

        if (categories == null) categories = Collections.emptyList();

        Map<Long, String> categoryIdToTitle = new HashMap<>();

        for (CategoryDTO category : categories) {
            categoryIdToTitle.put(category.getId(), category.getTitle());
        }
        return categoryIdToTitle;
    }


    private static void validateBody(ResponseEntity<List<TransactionDTO>> response) throws RuntimeException {
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException(response.getStatusCode().toString());
        }

        if (!response.hasBody() || response.getBody() == null) {
            log.error("NO TRANSACTIONS FOUND IN RESPONSE FROM TRANSACTION SERVICE: {}", response.getStatusCode());
            throw new RuntimeException("No transactions found");
        }
    }

}