package org.example.notificationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConstraintDTO {
    private Double value;

    private LocalDateTime timeToExpire;

    private LocalDateTime timeCreated;

    private Double available;

    @Override
    public String toString() {
        return String.format("""
                Value: %f
                Expire date: %s
                Creation date: %s
                Available: %f
                """, value, timeToExpire.toString(), timeCreated.toString(), available);
    }
}
