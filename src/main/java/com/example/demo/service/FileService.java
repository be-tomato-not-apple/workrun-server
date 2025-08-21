package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

public interface FileService {
	void ingestCentralFile(MultipartFile file);
}
