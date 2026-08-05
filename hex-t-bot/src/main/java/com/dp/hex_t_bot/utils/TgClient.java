package com.dp.hex_t_bot.utils;

import com.dp.hex_t_bot.dto.ResponseMessage;
import com.dp.hex_t_bot.dto.Update;
import com.dp.hex_t_bot.dto.UpdateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TgClient {
    private final RestClient tgClient;

    public List<Update> getUpdates(long offset) {
        UpdateResponse response = tgClient.get()
                .uri("/getUpdates?offset=" + offset)
                .retrieve()
                .body(UpdateResponse.class);

        return response.getResult();
    }

    public void sendMessage(String msg, Long chatId) {
        var response = new ResponseMessage(chatId, msg);
        tgClient.post()
                .uri("/sendMessage")
                .body(response)
                .retrieve()
                .toBodilessEntity();

    }
}
