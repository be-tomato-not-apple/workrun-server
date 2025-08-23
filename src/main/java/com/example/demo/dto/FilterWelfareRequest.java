package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.AssertFalse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FilterWelfareRequest {
	private String keyword;
	private List<String> homeStatusList = new ArrayList<>();
	private List<String> interestTopicList = new ArrayList<>();

}
