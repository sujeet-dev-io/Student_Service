package com.studentapp.dto;

import java.util.Date;

import lombok.Data;

@Data
public class DocumentDataResponseDTO {
	private String signedUrl;
	private Date validUntil;
	
	public DocumentDataResponseDTO(String signedUrl, Date validUntil) {
		super();
		this.signedUrl = signedUrl;
		this.validUntil = validUntil;
	} 
	
}
