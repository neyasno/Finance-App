package org.example.budgetservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "transaction-service")
public interface TransactionServiceClient {
    //TODO: IMPLEMENT CONTROLLER MAPPING FOR TRANSACTION CONTROLLER TO GET TRANSACTIONS BY TYPE AN ACCORDING TO DATES OR DATE PERIODS.

    //TODO: ВЫНЕСИ ТУТ ЛОГИКУ ЮЗЕРА ТОЖЕ :)
    public final static String X_USER_ID = "X-User-Id";

    /*Headers(X_USER_ID + ":{userId}")
    public Double get bla-bla-bla :))) цитирую: "Я сам потом сделаю. Не надо" (с)Костя
    */
}
