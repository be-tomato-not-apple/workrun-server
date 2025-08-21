package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class FileServiceImpl implements FileService{

	@Override
	@Transactional
	public void ingestCentralFile(MultipartFile multipartFile){

	}
}
