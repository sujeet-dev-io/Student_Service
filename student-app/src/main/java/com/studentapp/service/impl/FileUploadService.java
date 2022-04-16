package com.studentapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.studentapp.dto.FileDeleteDto;
import com.studentapp.helper.FileUploadHelper;
import com.studentapp.service.IFileUploadService;

@Service
public class FileUploadService implements IFileUploadService {

	@Autowired
	private FileUploadHelper fileUploadHelper;

	@Override
	public Boolean uploadFile(MultipartFile file) {
		Boolean isFileUploaded = false;
		if(file.isEmpty()) 
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File can't ne blank");

		System.out.println("File Format -> " + file.getContentType());
		if(!file.getContentType().equals("image/jpeg") && 
				!file.getContentType().equals("image/png"))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Format not supported, please upload jpg file");

		try {
			isFileUploaded = fileUploadHelper.uploadFile(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isFileUploaded;
	}

	@Override
	public Boolean deleteFile(FileDeleteDto filesDto) {
		
		Boolean isDeleted = false;

		try {
			isDeleted = fileUploadHelper.deleteFile(filesDto);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return isDeleted;
	}
}
