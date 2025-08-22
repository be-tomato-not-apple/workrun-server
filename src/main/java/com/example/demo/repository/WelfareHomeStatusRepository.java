package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.mapping.WelfareHomeStatus;

public interface WelfareHomeStatusRepository extends JpaRepository<WelfareHomeStatus, Long> {
	List<WelfareHomeStatus> findByWelfareId(Long id);
}
