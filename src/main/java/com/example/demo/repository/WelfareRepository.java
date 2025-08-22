package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Welfare;

public interface WelfareRepository extends JpaRepository<Welfare, Long> {
	Optional<Welfare> findById(Long id);
}
