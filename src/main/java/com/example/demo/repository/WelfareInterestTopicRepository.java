package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.mapping.WelfareInterestTopic;

public interface WelfareInterestTopicRepository extends JpaRepository<WelfareInterestTopic, Long> {
	List<WelfareInterestTopic> findByWelfareId(Long id);
}
