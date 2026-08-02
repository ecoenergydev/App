package com.dp.hex_t_bot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ResponseMessage {
    @JsonProperty("chat_id")
    private Long chatId;

    @JsonProperty("text")
    private String text;
}
