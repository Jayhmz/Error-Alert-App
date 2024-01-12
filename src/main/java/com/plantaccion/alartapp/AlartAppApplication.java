package com.plantaccion.alartapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@ComponentScan("com.plantaccion.alartapp")
@PropertySource("classpath:application-local.properties")
@EnableScheduling
public class AlartAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlartAppApplication.class, args);
	}


}
