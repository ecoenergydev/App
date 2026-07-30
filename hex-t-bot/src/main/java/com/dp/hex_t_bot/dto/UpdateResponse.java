package com.dp.hex_t_bot.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UpdateResponse {
    private boolean ok;
    private List<Update> result;
}
