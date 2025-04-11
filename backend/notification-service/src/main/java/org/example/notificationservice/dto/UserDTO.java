package org.example.notificationservice.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String email;

    public String getName() {
        int validationIndex = email.indexOf('@');

        if(validationIndex != -1 ) {
            return email.substring(0, validationIndex);
        }

        return "User";
    }
}
