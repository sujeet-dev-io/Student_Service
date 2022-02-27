//package com.studentapp.controller;
//
//import com.studentapp.dto.Status;
//import com.studentapp.response.BaseResponse;
//import com.studentapp.service.IFileUploadService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//
//@RestController
//@RequestMapping("/api")
//public class FileUploadController {
//
//    @Autowired
//    private IFileUploadService fileUploadService;
//
//    @PostMapping("/upload-file")
//    public ResponseEntity<BaseResponse<String, Long>> uploadFile(@RequestParam MultipartFile file) {
//        BaseResponse<String, Long> response = new BaseResponse<String, Long>();
//        try {
//            Boolean f = fileUploadService.uploadFile(file);
//            if(f) {
//                String data = ServletUriComponentsBuilder.
//                        fromCurrentContextPath().path("/image/").path(file.getOriginalFilename()).toUriString();
//                response.setData(data);
//                response.setStatus(Status.SUCCESS);
//                response.setMessage("File uploaded successfully");
//                return ResponseEntity.ok(response);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        response.setError("Something went wrong");
//        return ResponseEntity.ok(response);
//    }
//}
