package com.example.dem_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dem_service.services.FetchService;

@RestController
public class CountryAPIController {
  private final FetchService fetchService;
  
  public CountryAPIController(FetchService fetchService) {
    this.fetchService = fetchService;
  }

  @GetMapping("/test/fetch")
  public Object fetch() {
    return fetchService.fetchFromAllProviders();
  }
}
