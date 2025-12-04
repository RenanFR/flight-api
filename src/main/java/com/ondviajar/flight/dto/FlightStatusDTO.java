package com.ondviajar.flight.dto;

public record FlightStatusDTO(String flightDate, String status, String airlineName, String airlineIata,
		String flightNumber, String flightIata, String departureAirport, String departureIata,
		String departureScheduled, String departureActual, String arrivalAirport, String arrivalIata,
		String arrivalScheduled, String arrivalActual) {
}
