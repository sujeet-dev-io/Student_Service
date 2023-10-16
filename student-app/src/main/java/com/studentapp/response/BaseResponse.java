package com.studentapp.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.studentapp.enums.Status;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class BaseResponse<T>{
	String successMsg;

	@Builder.Default
	Status status = Status.SUCCESS;

	T data;
	String error;
	String errorMsg;
	String token;
}
