package org.example.analyticsservice.services;

import feign.FeignException;
import org.example.analyticsservice.dto.GeneralTransactionDataForChart;
import org.example.analyticsservice.dto.TransactionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnalyticsServiceTest {

    @InjectMocks
    private AnalyticsService analyticsService;

    @Mock
    private TransactionServiceClient transactionServiceClient;


    @Test
    void getGeneralTransactionDataForLast24Hours_shouldReturnDataForLast24Hours() {
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        LocalDateTime yesterday = now.minusDays(1);
        Long userId = 1L;
        List<TransactionDTO> transactions = List.of(
                TransactionDTO.builder().title("Transaction 1").categoryId(1L).type("INCOME").value(500.0).time(now.minusHours(6)).build(),
                TransactionDTO.builder().title("Transaction 2").categoryId(1L).type("OUTCOME").value(250.0).time(now.minusHours(5)).build(),
                TransactionDTO.builder().title("Transaction 3").categoryId(2L).type("INCOME").value(100.0).time(now.minusHours(4)).build(),
                TransactionDTO.builder().title("Transaction 4").categoryId(2L).type("INCOME").value(50.0).time(now.minusMinutes(5)).build(),
                TransactionDTO.builder().title("Transaction 5").categoryId(2L).type("OUTCOME").value(125.0).time(now.minusSeconds(5)).build()
        );
        GeneralTransactionDataForChart expectedData = new GeneralTransactionDataForChart(now.toLocalDate().toString(), 650.0, -375.0);

        when(transactionServiceClient.getAllTransactionsBetween(userId, yesterday, now)).thenReturn(ResponseEntity.ok(transactions));

        GeneralTransactionDataForChart actualData = analyticsService.getGeneralTransactionDataForLast24Hours(userId);

        assertEquals(expectedData, actualData);
    }

    @Test
    void getGeneralTransactionDataForLast24Hours_shouldThrowException() {
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        LocalDateTime yesterday = now.minusDays(1);
        Long userId = 1L;

        when(transactionServiceClient.getAllTransactionsBetween(userId, yesterday, now)).thenThrow(FeignException.class);

        assertThrows(RuntimeException.class, () -> analyticsService.getGeneralTransactionDataForLast24Hours(userId));
    }

}