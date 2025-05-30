package org.example.analyticsservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneralTransactionDataForChart {

    private String name;
    private Double income;
    private Double outcome;
}

