package com.dp.hex_t_bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HexTBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(HexTBotApplication.class, args);
	}

}
