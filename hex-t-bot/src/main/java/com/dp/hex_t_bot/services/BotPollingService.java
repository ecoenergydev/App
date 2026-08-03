package com.dp.hex_t_bot.services;


import com.dp.hex_t_bot.dto.Message;
import com.dp.hex_t_bot.dto.ResponseMessage;
import com.dp.hex_t_bot.dto.Update;
import com.dp.hex_t_bot.dto.UpdateResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class BotPollingService {
    private static final AtomicInteger offset = new AtomicInteger();

    private final RestClient tgClient;

    @Scheduled(fixedDelay = 2000) // Каждые 2 секунды
    public void poll() {
        try {
            UpdateResponse updates = tgClient.get()
                    .uri("/getUpdates?offset=" + offset.get())
                    .retrieve()
                    .body(UpdateResponse.class);

            log.info("Received updates: {}", updates);

            if (updates != null && updates.isOk() && updates.getResult() != null) {
                for (Update update : updates.getResult()) {
                    var response = new ResponseMessage(
                            update.getMessage().getChat().getId(),
                            "Response: " + update.getMessage().getText()
                    );
                    //var res = tgClient.postForEntity("/sendMessage", response, Message.class);
                    ResponseEntity<Message> res = tgClient.post()
                            .uri("/sendMessage")
                            .body(response)
                            .retrieve()
                            .toEntity(Message.class);
                    if (res.getStatusCode() == HttpStatusCode.valueOf(200)) {
                        offset.set(update.getUpdateId() + 1);
                    }
                }
                // TODO: Обработать полученные обновления
                log.debug("Updates count: {}",
                        updates.getResult() != null ? updates.getResult().size() : 0);
            }

        } catch (Exception e) {
            log.error("Error polling updates: {}", e.getMessage(), e);
        }
    }
}

//import com.dp.hex_t_bot.dto.UpdateResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//@Service
//@RequiredArgsConstructor
//public class BotPollingService {
//
//    private final RestTemplate tgClient;
//    @Scheduled(fixedDelay = 2000) //позволяет добавлять некоторые методы, каждые 2000мс вызов
//    public void poll() {
//        ResponseEntity<UpdateResponse> updates = tgClient.getForEntity("/getUpdate", UpdateResponse.class);
//        System.out.println(updates);
//    }
//}
