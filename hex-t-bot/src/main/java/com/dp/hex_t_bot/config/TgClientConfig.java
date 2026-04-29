package com.dp.hex_t_bot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class TgClientConfig {
    private static final String TG_BOT_API_URL = "https://api.telegram.org/bot%s";

    public RestTemplate tgClient(
            @Value("${tg.bot.timeout}") int timeout,
            @Value("${tg.bot.token}") String token,


    )
}
