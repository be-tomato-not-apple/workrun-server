package com.example.demo.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.FileService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/welfares")
public class WelfareController {

	private final FileService fileService;

	@PostMapping(value = "/xlsx", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Operation(summary = "중앙정책 파일 가공 api")
	public void tuningCentralPolicyFile(@RequestPart("file") MultipartFile file){
		fileService.ingestCentralFile(file);
	}
}
