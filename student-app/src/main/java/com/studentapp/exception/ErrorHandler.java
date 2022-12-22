package com.studentapp.exception;

import com.studentapp.enums.Status;
import com.studentapp.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@Slf4j
@ControllerAdvice
public class ErrorHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<BaseResponse<Integer, Integer>> handleAccessDeniedException(AccessDeniedException ex) {
        BaseResponse response = new BaseResponse();
        response.setStatus(Status.FAILURE);
        response.setMessage(ex.defaultMessage);
        response.setError("Access Denied");
        response.setData(null);

        return new ResponseEntity(response, null, HttpStatus.FORBIDDEN);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BaseResponse<Integer, Integer>> handleBadRequestException(BadRequestException ex) {
        BaseResponse response = new BaseResponse();
        response.setStatus(Status.FAILURE);
        response.setMessage(ex.defaultMessage);
        response.setError("Bad Request");
        response.setData(null);

        return new ResponseEntity(response, null, HttpStatus.BAD_REQUEST);
    }


}
