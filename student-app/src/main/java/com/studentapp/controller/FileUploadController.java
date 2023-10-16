package com.studentapp.controller;

import com.studentapp.dto.FileDeleteDto;
import com.studentapp.response.BaseResponse;
import com.studentapp.service.IFileUploadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/file")
public class FileUploadController {
	private final IFileUploadService fileUploadService;

	public FileUploadController(IFileUploadService fileUploadService) {
		this.fileUploadService = fileUploadService;
	}

	@PostMapping("/upload")
	public ResponseEntity<BaseResponse<Object>> uploadFile(
			@RequestParam MultipartFile file) {
		return ResponseEntity.ok(fileUploadService.uploadFile(file));
	}

	@DeleteMapping("/delete")
	public ResponseEntity<BaseResponse<Object>> deleteFile(
			@RequestBody FileDeleteDto filesDto) {
		return ResponseEntity.ok(fileUploadService.deleteFile(filesDto));
	}
}
