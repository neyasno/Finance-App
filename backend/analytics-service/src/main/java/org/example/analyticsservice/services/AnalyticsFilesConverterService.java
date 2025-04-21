package org.example.analyticsservice.services;

import lombok.RequiredArgsConstructor;
import org.example.analyticsservice.dto.GeneralTransactionDataForChart;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyticsFilesConverterService {

    private final AnalyticsService analyticsService;

    public String generateCSVStringForGeneralDataForAllTime(Long userId) {
        List<GeneralTransactionDataForChart> data = analyticsService.getGeneralTransactionDataForAllTime(userId);

        StringBuilder csv = new StringBuilder();
        csv.append("%s,%s,%s\n".formatted("year", "income", "outcome"));

        for (GeneralTransactionDataForChart dataItem : data) {
            csv.append("%s,%s,%s\n".formatted(dataItem.getName(), dataItem.getIncome(), dataItem.getOutcome()));
        }

        return csv.toString();
    }
}
