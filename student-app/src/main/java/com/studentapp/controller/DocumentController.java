package com.studentapp.controller;

import com.studentapp.dto.DocumentDataResponseDTO;
import com.studentapp.dto.DocumentDeleteResponseDTO;
import com.studentapp.dto.DocumentUploadResponseDTO;
import com.studentapp.response.BaseResponse;
import com.studentapp.service.impl.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/document")
public class DocumentController {

    @Autowired
    private DocumentService docUploadService;

    @PutMapping("/upload")
    public BaseResponse<DocumentUploadResponseDTO, Integer> uploadToS3(
            @RequestParam("fileName") String fileName,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        BaseResponse<DocumentUploadResponseDTO, Integer> response = new BaseResponse<>();
        response.setData(docUploadService.upload(fileName, file));
        response.setMessage("Document uploaded successfully");
        return response;
    }

    @DeleteMapping
    public BaseResponse<DocumentDeleteResponseDTO, Integer> deleteFromS3(
            @RequestParam("fileName") String fileName) throws IOException {
        BaseResponse<DocumentDeleteResponseDTO, Integer> response = new BaseResponse<>();
        response.setData(docUploadService.delete(fileName));
        response.setMessage("Document deleted successfully");
        return response;
    }

    @GetMapping
    public BaseResponse<DocumentDataResponseDTO, Integer> fetchFromS3(
            @RequestParam("key") String documentKey) throws IOException {
        BaseResponse<DocumentDataResponseDTO, Integer> response = new BaseResponse<>();
        response.setData(docUploadService.fetchDoc(documentKey));
        response.setMessage("Document signed url fetched successfully");
        return response;
    }

}
