//package com.dp.hex_t_bot.services;
//
//
//import com.dp.hex_t_bot.dto.Update;
//import com.dp.hex_t_bot.utils.TgClient;
//import lombok.RequiredArgsConstructor;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import java.util.concurrent.atomic.AtomicInteger;
//
//@Service
//@RequiredArgsConstructor
//public class BotPollingService {
//    private static final AtomicInteger offset = new AtomicInteger();
//            // todo вынести из кода (может выдавать задвоение сообщения при перезапуске приложения)
//    private final TgClient tgClient;
//
//    @Scheduled(fixedDelay = 2000) //позволяет добавлять некоторые методы, каждые 2000мс вызов
//    public void poll() {
//        var updates = tgClient.getUpdates(offset.get());
//
//
//
//        for (Update update : updates) {
//
//            String text = update.getMessage().getText();
//
//            tgClient.sendMessage(
//                    "Response: " + update.getMessage().getText(),
//                    update.getMessage().getChat().getId()
//            );
//            offset.set(update.getUpdateId() + 1);
//        }
//    }
//}
//package com.dp.hex_t_bot.services;
//
//import com.dp.hex_t_bot.dto.Update;
//import com.dp.hex_t_bot.utils.TgClient;
//import lombok.RequiredArgsConstructor;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import java.util.concurrent.atomic.AtomicInteger;
//
//@Service
//@RequiredArgsConstructor
//public class BotPollingService {
//    private static final AtomicInteger offset = new AtomicInteger();
//    private final TgClient tgClient;
//
//    @Scheduled(fixedDelay = 2000)
//    public void poll() {
//        var updates = tgClient.getUpdates(offset.get());
//
//        for (Update update : updates) {
//            // Проверяем наличие сообщения
//            if (update.getMessage() == null) {
//                tgClient.sendMessage(
//                        "Ошибка: null",
//                        update.getMessage().getChat().getId()
//                );
//                offset.set(update.getUpdateId() + 1);
//                continue;
//            }
//
//            // Проверяем наличие текста
//            String text = update.getMessage().getText();
//            if (text == null || text.trim().isEmpty()) {
//                tgClient.sendMessage(
//                        "Ошибка: пустое сообщение",
//                        update.getMessage().getChat().getId()
//                );
//                offset.set(update.getUpdateId() + 1);
//                continue;
//            }
//
//            String[] parts = text.split(" ");
//
//            if (parts.length == 2) {
//                try {
//                    double o2 = Double.parseDouble(parts[0]);
//                    double noxPpm = Double.parseDouble(parts[1]);
//
//
//
//
//                    // Проверка корректности значений
//                    if (o2 <= 0) {
//                        tgClient.sendMessage(
//                                "Ошибка: O2 должно быть больше 0",
//                                update.getMessage().getChat().getId()
//                        );
//                        offset.set(update.getUpdateId() + 1);
//                        continue;
//                    }
//                    if (o2 >= 21) {
//                        tgClient.sendMessage(
//                                "Ошибка: O2 должно быть меньше 21",
//                                update.getMessage().getChat().getId()
//                        );
//                        offset.set(update.getUpdateId() + 1);
//                        continue;
//                    }
//
//                    if (noxPpm < 0) {
//                        tgClient.sendMessage(
//                                "Ошибка: NOx не может быть отрицательным",
//                                update.getMessage().getChat().getId()
//                        );
//                        offset.set(update.getUpdateId() + 1);
//                        continue;
//                    }
//
//                    // Формула приведения NOx к 1.4 (O2 = 1.4%)
//                    double noxCorrected = noxPpm * (17.45 / (21 - o2));
//
//                    tgClient.sendMessage(
//                            String.format(
//                                    "📊 Результат расчета:\n" +
//                                            "━━━━━━━━━━━━━━━━━━━\n" +
//                                            "O2 = %.2f%%\n" +
//                                            "NOx (исходный) = %.2f ppm\n" +
//                                            "━━━━━━━━━━━━━━━━━━━\n" +
//                                            "NOx (к 1.4) = %.2f ppm",
//                                    o2, noxPpm, noxCorrected
//                            ),
//                            update.getMessage().getChat().getId()
//                    );
//                } catch (NumberFormatException e) {
//                    tgClient.sendMessage(
//                            "❌ Ошибка: введите корректные числа\n" +
//                                    "Формат: O2 NOx\n" +
//                                    "Например: 6 250",
//                            update.getMessage().getChat().getId()
//                    );
//                }
//            } else {
//                tgClient.sendMessage(
//                        "📝 Введите два числа через пробел:\n" +
//                                "O2,%  NOx,ppm\n" +
//                                "━━━━━━━━━━━━━━━━━━━\n" +
//                                "Пример: 6 250",
//                        update.getMessage().getChat().getId()
//                );
//            }
//
//            offset.set(update.getUpdateId() + 1);
//        }
//    }
//}

package com.dp.hex_t_bot.services;

import com.dp.hex_t_bot.dto.Update;
import com.dp.hex_t_bot.utils.TgClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class BotPollingService {
    private static final AtomicInteger offset = new AtomicInteger();
    private final TgClient tgClient;

    @Scheduled(fixedDelay = 2000)
    public void poll() {
        try {
            var updates = tgClient.getUpdates(offset.get());

            for (Update update : updates) {
                // ПРОВЕРКА НА NULL - ОБЯЗАТЕЛЬНО!
                if (update.getMessage() == null) {
                    log.warn("Update {} has no message, skipping", update.getUpdateId());
                    offset.set(update.getUpdateId() + 1);
                    continue;
                }

                String text = update.getMessage().getText();
                if (text == null || text.trim().isEmpty()) {
                    log.warn("Update {} has empty message, skipping", update.getUpdateId());
                    offset.set(update.getUpdateId() + 1);
                    continue;
                }

                Long chatId = update.getMessage().getChat().getId();

                String[] parts = text.trim().split(" ");

                if (parts.length == 2) {
                    try {
                        double o2 = Double.parseDouble(parts[0]);
                        double noxPpm = Double.parseDouble(parts[1]);

                        if (o2 <= 0) {
                            tgClient.sendMessage(
                                    "Ошибка: O2 должно быть больше 0",
                                    chatId
                            );
                            offset.set(update.getUpdateId() + 1);
                            continue;
                        }

                        if (noxPpm < 0) {
                            tgClient.sendMessage(
                                    "Ошибка: NOx не может быть отрицательным",
                                    chatId
                            );
                            offset.set(update.getUpdateId() + 1);
                            continue;
                        }

                        double noxCorrected = noxPpm * (17.45 / (21 - o2));

                        tgClient.sendMessage(
                                String.format(
                                        "📊 Результат расчета:\n" +
                                                "━━━━━━━━━━━━━━━━━━━\n" +
                                                "O2 = %.2f%%\n" +
                                                "NOx (исходный) = %.2f ppm\n" +
                                                "━━━━━━━━━━━━━━━━━━━\n" +
                                                "NOx (к 1.4) = %.2f ppm",
                                        o2, noxPpm, noxCorrected
                                ),
                                chatId
                        );
                    } catch (NumberFormatException e) {
                        tgClient.sendMessage(
                                "❌ Ошибка: введите корректные числа\n" +
                                        "Формат: O2 NOx\n" +
                                        "Например: 6 250",
                                chatId
                        );
                    }
                } else {
                    tgClient.sendMessage(
                            "📝 Введите два числа через пробел:\n" +
                                    "O2,%  NOx,ppm\n" +
                                    "━━━━━━━━━━━━━━━━━━━\n" +
                                    "Пример: 6 250",
                            chatId
                    );
                }

                offset.set(update.getUpdateId() + 1);
            }
        } catch (Exception e) {
            log.error("Error in poll cycle: {}", e.getMessage(), e);
        }
    }
}