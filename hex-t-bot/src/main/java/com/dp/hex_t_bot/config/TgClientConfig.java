package com.dp.hex_t_bot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Configuration
public class TgClientConfig {
    private static final String TG_BOT_API_URL = "https://api.telegram.org/bot%s/";

    @Bean
    public RestClient tgRestClient(
            @Value("${tg.bot.timeout}") int timeout,
            @Value("${tg.bot.token}") String token
    ) {
        // 1. Создаем и настраиваем фабрику запросов
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofSeconds(timeout));
        factory.setReadTimeout(Duration.ofSeconds(timeout));

        // 2. Передаем её в билдер напрямую (без лямбды)
        return RestClient.builder()
                .baseUrl(TG_BOT_API_URL.formatted(token))
                .requestFactory(factory)
                .build();
    }
}
