package org.example.notificationservice.constants;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkAttachment {
    public String header;
    public String link;

    @Override
    public String toString(){
        return header + ": " + link;
    }
}
