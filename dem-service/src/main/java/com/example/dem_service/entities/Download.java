package com.example.dem_service.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Download {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "p_id", nullable = false)
    private Provider p_id;

    private LocalDateTime date;

    private String filepath;
    public Download() {}

    public Download(Provider p_id, LocalDateTime date, String filepath) {
        this.p_id = p_id;
        this.date = date;
        this.filepath = filepath;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Provider getP_id() {
        return p_id;
    }
    public void setP_id(Provider p_id) {
        this.p_id = p_id;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    public String getFilepath() {
        return filepath;
    }
    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
}
