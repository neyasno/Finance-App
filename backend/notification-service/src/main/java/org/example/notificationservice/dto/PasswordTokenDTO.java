package org.example.notificationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.notificationservice.constants.Constants;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordTokenDTO {
    String token;
}
