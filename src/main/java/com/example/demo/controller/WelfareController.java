package com.example.demo.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.apiPayload.ApiResponse;

import com.example.demo.dto.FilterWelfareRequest;
import com.example.demo.dto.FilteredWelfareDTO;
import com.example.demo.dto.welfareDetailDTO;
import com.example.demo.service.WelfareService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/welfares")
public class WelfareController {

	private final WelfareService welfareService;

	@GetMapping("/{welfareId}")
	@Operation(summary = "id기반 정보조회 api")
	@Parameters({
		@Parameter(name = "welfareId", description = "복지정책 아이디 넘겨주세요")
	})
	public ResponseEntity<ApiResponse<welfareDetailDTO>> getWelfareDetail(
		@PathVariable Long welfareId
	){
		welfareDetailDTO result = welfareService.getDetailById(welfareId);
		return ResponseEntity.ok(ApiResponse.success(result));
	}

	@PostMapping("/filtering")
	@Operation(summary = "카테고리의 교집합에 해당하는 복지 정책을 반환합니다")
	public ResponseEntity<ApiResponse<FilteredWelfareDTO.listDTO>> getFilteredWelfare(
		@RequestBody FilterWelfareRequest dto
	){
		return null;
	}
}
