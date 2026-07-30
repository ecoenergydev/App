package com.dp.hex_t_bot.services;


import com.dp.hex_t_bot.dto.UpdateResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class BotPollingService {

    private final RestClient tgClient;

    @Scheduled(fixedDelay = 2000) // Каждые 2 секунды
    public void poll() {
        try {
            UpdateResponse response = tgClient.get()
                    .uri("/getUpdates") // Исправлено: /getUpdates (во множественном числе)
                    .retrieve()
                    .body(UpdateResponse.class);

            log.info("Received updates: {}", response);

            if (response != null && response.isOk()) {
                // TODO: Обработать полученные обновления
                log.debug("Updates count: {}",
                        response.getResult() != null ? response.getResult().size() : 0);
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
