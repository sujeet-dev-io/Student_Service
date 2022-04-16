package com.studentapp.helper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.studentapp.constant.Message;
import com.studentapp.dto.FileDeleteDto;

@Component
public class FileUploadHelper {
	
	protected final Log logger = LogFactory.getLog(FileUploadHelper.class);

	// To upload the file in your local location
	
//    public final String UPLOAD_DIR = "D:\\client\\std\\development\\student-app\\student-app\\src\\main\\resources\\static\\image";
    
	// To upload the file in your global location
	
	 String UPLOAD_DIR = new ClassPathResource("").getFile().getAbsolutePath();

    public FileUploadHelper() throws IOException {

    }
    
    public Boolean uploadFile(MultipartFile file) throws IOException {
        Boolean isUploaded = false;
        
        logger.info("ClassPath ->" + new ClassPathResource("").getFile().getAbsolutePath());
        try{
            Files.copy(
                    file.getInputStream(),
                    Paths.get(UPLOAD_DIR+ File.separator + file.getOriginalFilename()),
                    StandardCopyOption.REPLACE_EXISTING);
            isUploaded = true;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return isUploaded;
    }
    
    public Boolean deleteFile(FileDeleteDto filesDto) throws IOException {

    	Boolean isUploaded = false; 
    	List<String> listOfFilesNamesToDelete = filesDto.getListOfFileNames();

    	for(String fileName : listOfFilesNamesToDelete) {
    		
    		Path path = java.nio.file.Paths.get(UPLOAD_DIR + File.separator + fileName);
    		try {
    			Files.deleteIfExists(path);
    			isUploaded = true;
    		} catch(NoSuchFileException ex) {
    			logger.info(Message.FILE_NOT_FOUND);
    			return false;
    		}
    		catch(Exception e) {
    			e.printStackTrace();
    		}
    	}
    	return isUploaded;
    }
}
