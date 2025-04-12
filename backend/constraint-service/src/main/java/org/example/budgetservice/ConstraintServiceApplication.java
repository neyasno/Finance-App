package org.example.budgetservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableFeignClients
@SpringBootApplication
@EnableTransactionManagement
@EnableDiscoveryClient
public class ConstraintServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConstraintServiceApplication.class, args);
    }

}
