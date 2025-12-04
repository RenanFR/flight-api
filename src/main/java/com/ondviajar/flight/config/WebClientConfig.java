package com.ondviajar.flight.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.JdkClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

	@Bean
	public WebClient.Builder webClientBuilder() {
		return WebClient.builder().clientConnector(new JdkClientHttpConnector());
	}
}
