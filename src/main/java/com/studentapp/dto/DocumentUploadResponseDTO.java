package com.studentapp.dto;

import com.studentapp.enums.DocUploadStatus;
import lombok.Data;

@Data
public class DocumentUploadResponseDTO {

    private String key;
    private DocUploadStatus status;

    public DocumentUploadResponseDTO(String key, DocUploadStatus status) {
        super();
        this.key = key;
        this.status = status;
    }

}
