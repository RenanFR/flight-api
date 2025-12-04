package com.ondviajar.flight.dto;

public record FlightData(String flight_date, String flight_status, Airline airline, Flight flight, Departure departure,
		Arrival arrival) {
}
