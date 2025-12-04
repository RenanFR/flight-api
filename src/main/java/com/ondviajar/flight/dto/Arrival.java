package com.ondviajar.flight.dto;

public record Arrival(String airport, String iata, String scheduled, String actual) {
}