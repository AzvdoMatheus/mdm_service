package com.example.dem_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dem_service.entities.Provider;

public interface ProviderRepository extends JpaRepository<Provider, Long> {}
