package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilteredWelfareDTO{


	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class listDTO{
		List<welfarePreview> filteredList = new ArrayList<>();

	}

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class welfarePreview{
		private Long id;
		private String serviceName;
		private String center;
		List<String> homeStatusList = new ArrayList<>();
		List<String> interestTopicList = new ArrayList<>();


	}
}