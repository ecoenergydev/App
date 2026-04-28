package com.dp.hex_t_bot.services;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class BotPollingService {

    @Scheduled(fixedDelay = 2000) //позволяет добавлять некоторые методы, каждые 2000мс вызов
    public void poll() {
        System.out.println("poll run");
    }
}
