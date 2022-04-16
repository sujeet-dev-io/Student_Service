package com.studentapp.service;

import org.springframework.web.multipart.MultipartFile;

import com.studentapp.dto.FileDeleteDto;

public interface IFileUploadService {
    Boolean uploadFile(MultipartFile file);

	Boolean deleteFile(FileDeleteDto request);
}
