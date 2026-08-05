package com.dp.hex_t_bot.services;


import com.dp.hex_t_bot.dto.Update;
import com.dp.hex_t_bot.utils.TgClient;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class BotPollingService {
    private static final AtomicInteger offset = new AtomicInteger();
            // todo вынести из кода (может выдавать задвоение сообщения при перезапуске приложения)
    private final TgClient tgClient;

    @Scheduled(fixedDelay = 2000) //позволяет добавлять некоторые методы, каждые 2000мс вызов
    public void poll() {
        var updates = tgClient.getUpdates(offset.get());
        for (Update update : updates) {
            tgClient.sendMessage(
                    "Response: " + update.getMessage().getText(),
                    update.getMessage().getChat().getId()
            );
            offset.set(update.getUpdateId() + 1);
        }
    }
}
