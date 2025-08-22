package com.example.demo.domain;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.domain.mapping.WelfareHomeStatus;
import com.example.demo.domain.mapping.WelfareInterestTopic;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Welfare {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String center;
	private String serviceName;

	@Column(columnDefinition = "TEXT")
	private String content;

	@Column(columnDefinition = "TEXT")
	private String url;

	@Column(columnDefinition = "TEXT")
	private String target;

	@Column(columnDefinition = "TEXT")
	private String applyMethod;

	@Column(columnDefinition = "TEXT")
	private String needDocument;

	@OneToMany(mappedBy = "welfare", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<WelfareHomeStatus> homeStatusList = new ArrayList<>();

	@OneToMany(mappedBy = "welfare", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<WelfareInterestTopic> interestList = new ArrayList<>();


}
