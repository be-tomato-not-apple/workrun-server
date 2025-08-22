package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.InterestTopic;

public interface InterestTopicRepository extends JpaRepository<InterestTopic, Integer> {
	String findById(Long id);
}
