package com.example.demo.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class welfareDetailDTO {
	private Long id;
	private String center;
	private String serviceName;

	@Column(columnDefinition = "TEXT")
	private String content;

	private String url;

	@Column(columnDefinition = "TEXT")
	private String target;

	@Column(columnDefinition = "TEXT")
	private String applyMethod;

	@Column(columnDefinition = "TEXT")
	private String needDocument;

	List<String> homeStatusList = new ArrayList<>();
	List<String> topicList = new ArrayList<>();
}
