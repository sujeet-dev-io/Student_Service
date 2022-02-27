//package com.studentapp.service.impl;
//
//import com.studentapp.helper.FileUploadHelper;
//import com.studentapp.service.IFileUploadService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.server.ResponseStatusException;
//
//@Service
//public class FileUploadService implements IFileUploadService {
//
//    @Autowired
//    private FileUploadHelper fileUploadHelper;
//
//    @Override
//    public Boolean uploadFile(MultipartFile file) {
//        Boolean f = false;
//        if(file.isEmpty())
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File can't ne blank");
//        if(!file.getContentType().equals("image/jpeg"))
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
//                    "Format not supported, please upload jpg file");
//
//        try {
//            f = fileUploadHelper.uploadFile(file);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return f;
//    }
//}
