package com.studentapp.service;

import org.springframework.web.multipart.MultipartFile;

public interface IFileUploadService {
    Boolean uploadFile(MultipartFile file);
}
