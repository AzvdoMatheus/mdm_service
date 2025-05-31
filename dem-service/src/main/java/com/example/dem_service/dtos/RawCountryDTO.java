package com.example.dem_service.dtos;

import java.util.List;
import java.util.Map;

public record RawCountryDTO(
  Name name,
  String cca3,
  List<String> capital,
  long population,
  double area,
  Map<String, Currency> currencies
) {
  public static record Name(String common) {}
  public static record Currency(String name, String symbol) {}
}
