package com.example.demo.domain.mapping;
import com.example.demo.domain.InterestTopic;
import com.example.demo.domain.Welfare;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WelfareInterestTopic {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "welfare_id")
	private Welfare welfare;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name= "interest_topic_id")
	private InterestTopic interestTopic;
}
