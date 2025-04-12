package org.example.analyticsservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryTransactionDataForChart{
    private String name;

    private Map<String, Double> data;
}
