package org.example.transactionservice.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import org.example.transactionservice.utils.CustomLocalDateTimeDeserializer;

import java.time.LocalDateTime;

@Data
public class TransactionByTimeRequest {
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime time;
}


