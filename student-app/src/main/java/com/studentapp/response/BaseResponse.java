package com.studentapp.response;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * Instantiates a new base response.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseResponse<T, ID> extends GenericResponse{
	
	/** The data. */
	T data;
	
	/** The id. */
	ID id;
}
