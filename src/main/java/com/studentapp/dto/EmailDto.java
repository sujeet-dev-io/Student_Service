package com.studentapp.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EmailDto {

    private String to;
    private String[] toArray;
    private String from;
    private String subject;
    private String body;
    private String[] cc;
}

