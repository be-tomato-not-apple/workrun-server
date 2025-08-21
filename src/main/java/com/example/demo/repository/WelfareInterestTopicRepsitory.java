package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.mapping.WelfareInterestTopic;

public interface WelfareInterestTopicRepsitory extends JpaRepository<WelfareInterestTopic, Long> {
}
