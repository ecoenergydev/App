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
    public RestClient tgClient(
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




// Код, использующий устаревший RestTemplate вместо RestClient:

// package com.dp.hex_t_bot.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.client.RestTemplateBuilder;
//
//import java.time.Duration;
//import java.time.temporal.ChronoUnit;
//
//@Configuration
//public class TgClientConfig {
//    private static final String TG_BOT_API_URL = "https://api.telegram.org/bot%s/";
//        // вместо %s будет указан токен из конфигурации (переменная среды)
//    @Bean
//    public RestTemplate tgClient(
//            @Value("${tg.bot.timeout}") int timeout,
//            @Value("${tg.bot.token}") String token,
//            RestTemplateBuilder restTemplateBuilder
//    ) {
//        var timeoutDuration = Duration.of(timeout, ChronoUnit.SECONDS);
//        return restTemplateBuilder
//                .rootUri(TG_BOT_API_URL.formatted(token)) // токен из конфигурации
//                .setReadTimeout(timeoutDuration)
//                .setConnectionTimeout(timeoutDuration)
//                .build();
//    }
//}
