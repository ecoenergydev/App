package com.dp.hex_t_bot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestTemplateBuilder;

import java.time.Duration;

@Configuration
public class TgClientConfig {
    private static final String TG_BOT_API_URL = "https://api.telegram.org/bot%s";

    @Bean
    public RestTemplate tgClient(
            @Value("${tg.bot.timeout}") int timeout,
            @Value("${tg.bot.token}") String token,
            RestTemplateBuilder restTemplateBuilder
    ) {
        var timeoutDuration = Duration.of(timeout, ChronoUnit.SECONDS);
        restTemplateBuilder
                .rootUri(TG_BOT_API_URL.formatted(token))
                .setReadTimeout(timeoutDuration)
                .setConnectionTimeout(timeoutDuration)
                .build();
    }
}
