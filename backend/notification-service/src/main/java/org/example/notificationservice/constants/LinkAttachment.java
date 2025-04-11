package org.example.notificationservice.constants;

import lombok.Data;

@Data
public class LinkAttachment {
    public String header;
    public String link;

    @Override
    public String toString(){
        return header + ": " + link;
    }
}
