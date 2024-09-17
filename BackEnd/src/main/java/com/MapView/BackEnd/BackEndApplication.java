package com.MapView.BackEnd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class BackEndApplication {
	//Link Swagger => http://localhost:8082/swagger-ui/index.html#/

	public static void main(String[] args) {
		SpringApplication.run(BackEndApplication.class, args);
	}

}
