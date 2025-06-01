package com.example.dem_service.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.dem_service.entities.Download;
import com.example.dem_service.entities.Provider;
import com.example.dem_service.repository.DownloadRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DownloadsService {

    private final DownloadRepository downloadRepository;
    private final ObjectMapper objectMapper;

    public DownloadsService(
        DownloadRepository downloadRepository,
        ObjectMapper objectMapper
    ) {
        this.downloadRepository = downloadRepository;
        this.objectMapper = objectMapper;
    }

    public void saveRawJsonAndRecord(Provider provider, List<JsonNode> countries) {
        Path downloadsDir = Paths.get("downloads");
        String safeProviderName = provider.getName().replaceAll("\\s+", "_");
        String filename = safeProviderName + ".json";
        Path filePath = downloadsDir.resolve(filename);

        try {
            if (!Files.exists(downloadsDir)) {
                Files.createDirectories(downloadsDir);
            }
        } catch (IOException e) {
            throw new RuntimeException("Falha ao criar pasta downloads: " + downloadsDir, e);
        }

        try {
            JsonNode arrayNode = objectMapper.createArrayNode().addAll(countries);
            objectMapper.writerWithDefaultPrettyPrinter()
                        .writeValue(filePath.toFile(), arrayNode);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store json in file: " + filePath, e);
        }

        Long provId = provider.getId();
        Optional<Download> existingOpt = downloadRepository.findByProvider_Id(provId);

        if (existingOpt.isPresent()) {
            Download existing = existingOpt.get();
            existing.setDate(LocalDateTime.now());
            existing.setFilepath(filePath.toString());
            downloadRepository.save(existing);
        } else {
            Download newDownload = new Download(
                provider,
                LocalDateTime.now(),
                filePath.toString()
            );
            downloadRepository.save(newDownload);
        }
    }
}
