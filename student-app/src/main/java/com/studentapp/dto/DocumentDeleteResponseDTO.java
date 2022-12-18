package com.studentapp.dto;

import com.studentapp.enums.DocUploadStatus;
import lombok.Data;

@Data
public class DocumentDeleteResponseDTO {
	
	private DocUploadStatus status;
	
	public DocumentDeleteResponseDTO(DocUploadStatus status) {
		super();
		this.status = status;
	}

}
