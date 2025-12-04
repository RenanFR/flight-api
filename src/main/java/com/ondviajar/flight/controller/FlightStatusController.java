package com.ondviajar.flight.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.ondviajar.flight.dto.AviationStackResponse;
import com.ondviajar.flight.dto.FlightStatusDTO;
import com.ondviajar.flight.service.SecretService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/flight-status")
public class FlightStatusController {

	private final WebClient webClient;
	private final SecretService secretService;

	public FlightStatusController(SecretService secretService, WebClient.Builder builder) {
		this.secretService = secretService;
		this.webClient = builder.baseUrl("https://api.aviationstack.com/v1").build();
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<FlightStatusDTO> getFlightStatus(@RequestParam("flightIata") String flightIata) {
		String apiKey = secretService.getAviationStackKey();
		System.out.println(">>> API KEY = [" + apiKey + "]");

		return webClient.get().uri(uriBuilder -> {
			var uri = uriBuilder.path("/flights").queryParam("access_key", apiKey).queryParam("flight_iata", flightIata)
					.build();

			System.out.println(">>> URL FINAL = " + uri.toString());
			return uri;
		}).retrieve().bodyToMono(AviationStackResponse.class).map(response -> {
			var flight = response.data().get(0);
			return new FlightStatusDTO(flight.flight_date(), flight.flight_status(), flight.airline().name(),
					flight.airline().iata(), flight.flight().number(), flight.flight().iata(),
					flight.departure().airport(), flight.departure().iata(), flight.departure().scheduled(),
					flight.departure().actual(), flight.arrival().airport(), flight.arrival().iata(),
					flight.arrival().scheduled(), flight.arrival().actual());
		});
	}
}
