//package com.studentapp.helper;
//
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
//
//@Component
//public class FileUploadHelper {
//
//    public final String UPLOAD_DIR = new ClassPathResource("static/image").getFile().getAbsolutePath();
//
//    public FileUploadHelper() throws IOException {
//
//    }
//    public Boolean uploadFile(MultipartFile file) {
//        Boolean f = false;
//        try{
//            Files.copy(
//                    file.getInputStream(),
//                    Paths.get(UPLOAD_DIR+ File.separator + file.getOriginalFilename()),
//                    StandardCopyOption.REPLACE_EXISTING);
//            f = true;
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//        return f;
//    }
//}
