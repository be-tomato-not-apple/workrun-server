package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.mapping.WelfareHomeStatus;

public interface WelfareHomeStatusRepository extends JpaRepository<WelfareHomeStatus, Long> {
}
