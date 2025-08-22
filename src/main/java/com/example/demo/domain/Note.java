package com.example.demo.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String message;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    protected Note() {
    } // JPA 기본 생성자

    public Note(String message) {
        this.message = message;
        this.createdAt = LocalDateTime.now();
    }

    // Getter (필요시 Setter)
    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}