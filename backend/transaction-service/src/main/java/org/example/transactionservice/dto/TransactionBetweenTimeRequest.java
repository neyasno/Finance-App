package org.example.transactionservice.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import org.example.transactionservice.utils.CustomLocalDateTimeDeserializer;

import java.time.LocalDateTime;

@Data
public class TransactionBetweenTimeRequest{
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime from;
    
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime to;
}
