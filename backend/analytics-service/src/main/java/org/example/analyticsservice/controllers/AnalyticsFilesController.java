package org.example.analyticsservice.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.analyticsservice.services.AnalyticsFilesConverterService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AnalyticsFilesController {

    private final AnalyticsFilesConverterService analyticsService;

    @GetMapping("/csv")
    public ResponseEntity<InputStreamResource> getAnalyticsInCSVFormat(
            @RequestHeader("X-User-Id") Long userId
    ) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "text/csv");
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename("general.csv").build());

        String data = analyticsService.generateCSVStringForGeneralDataForAllTime(userId);

        try(ByteArrayInputStream dataStream = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8))) {
            InputStreamResource inputStreamResource = new InputStreamResource(dataStream);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(inputStreamResource);
        } catch (IOException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
