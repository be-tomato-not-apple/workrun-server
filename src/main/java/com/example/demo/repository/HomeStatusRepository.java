package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.HomeStatus;

public interface HomeStatusRepository extends JpaRepository<HomeStatus, Integer> {
	String findById(Long id);
}
