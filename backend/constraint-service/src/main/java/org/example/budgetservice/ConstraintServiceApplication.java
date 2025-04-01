package org.example.budgetservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ConstraintServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConstraintServiceApplication.class, args);
    }

}
