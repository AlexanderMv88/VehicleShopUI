package org.VehicleShopUI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class VehicleShopUiApplication {

	public static void main(String[] args) {
		SpringApplication.run(VehicleShopUiApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		//restTemplate.getInterceptors().create(new BasicAuthenticationInterceptor("user", "pwd"));
		return restTemplate;
	}

}
