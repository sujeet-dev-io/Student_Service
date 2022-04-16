package com.studentapp.controller;

import com.studentapp.dto.Status;
import com.studentapp.response.BaseResponse;
import com.studentapp.service.IFileUploadService;

import java.nio.file.NoSuchFileException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/file")
public class FileUploadController {

	@Autowired
	private IFileUploadService fileUploadService;

	@PostMapping("/upload")
	public ResponseEntity<BaseResponse<String, Long>> uploadFile(
			@RequestParam MultipartFile file) throws NoSuchFileException {
		BaseResponse<String, Long> response = new BaseResponse<String, Long>();
		try {
			Boolean isFileUploaded = fileUploadService.uploadFile(file);
			if(isFileUploaded) {
				String data = ServletUriComponentsBuilder
						.fromCurrentContextPath()
						.path("/image/")
						.path(file.getOriginalFilename())
						.toUriString();
				response.setData(data);
				response.setStatus(Status.SUCCESS);
				response.setMessage("File uploaded successfully");
				return ResponseEntity.ok(response);
			}
		}  catch (Exception e) {
			response.setStatus(Status.FAILURE);
			response.setError("File could not be uploaded. Something went wrong !!");
			e.printStackTrace();
		}
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/delete/{fileName}")
	public ResponseEntity<BaseResponse<String, Long>> deleteFile(
			@PathVariable String fileName) {
		BaseResponse<String, Long> response = new BaseResponse<String, Long>();
		Boolean isFileUploaded = false;
		try {
			isFileUploaded = fileUploadService.deleteFile(fileName);
			if(isFileUploaded) {
				response.setData(Boolean.toString(isFileUploaded));
				response.setStatus(Status.SUCCESS);
				response.setMessage("File deleted successfully");
				return ResponseEntity.ok(response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setData(Boolean.toString(isFileUploaded));
		response.setStatus(Status.FAILURE);
		response.setError("File could not be deleted. Something went wrong !!");
		return ResponseEntity.ok(response);
	}
}
