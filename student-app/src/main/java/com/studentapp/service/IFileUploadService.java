package com.studentapp.service;

import com.studentapp.dto.FileDeleteDto;
import com.studentapp.response.BaseResponse;
import org.springframework.web.multipart.MultipartFile;

public interface IFileUploadService {
    BaseResponse<Object> uploadFile(MultipartFile file);
    BaseResponse<Object> deleteFile(FileDeleteDto request);
}
