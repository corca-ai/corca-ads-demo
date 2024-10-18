package com.corca.ads.demo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Corca Ads Demo Server", version = "1.0",
		description = "Corca Ads를 사용하는 데모 서버를 위한 API"))
public class CorcaAdsDemoServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CorcaAdsDemoServerApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
