package com.example.mdm_service.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.mdm_service.dtos.CountryDTO;

@FeignClient(name = "DemServiceClient", url = "${dem.service.url}")
public interface DemServiceClient {

    @GetMapping("/fetch")
    List<CountryDTO> receiveDataFromDEM();
}
