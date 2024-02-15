package com.plantaccion.alartapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AlertAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlertAppApplication.class, args);
	}


}
