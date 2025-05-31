package com.example.dem_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dem_service.entities.Download;

public interface DownloadRepository extends JpaRepository<Download, Long> {}
