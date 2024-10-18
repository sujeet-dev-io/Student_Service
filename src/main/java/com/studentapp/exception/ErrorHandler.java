package com.studentapp.exception;

import com.studentapp.enums.Status;
import com.studentapp.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<BaseResponse<Object>> handleAccessDeniedException(AccessDeniedException ex) {
        BaseResponse<Object> response = BaseResponse.builder()
                .status(Status.FAILURE)
                .error("Access Denied")
                .errorMsg(ex.getMessage())  // Use ex.getMessage() instead of ex.defaultMessage
                .build();
        return new ResponseEntity<>(response, null, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<BaseResponse<Object>> handleSpringSecurityAccessDeniedException() {
        BaseResponse<Object> response = BaseResponse.builder()
                .status(Status.FAILURE)
                .error("Access Denied")
                .errorMsg("You don't have permission to access this API")
                .build();

        return new ResponseEntity<>(response, null, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BaseResponse<Object>> handleBadRequestException(BadRequestException ex) {
        BaseResponse<Object> response = BaseResponse.builder()
                .status(Status.FAILURE)
                .error("Bad Request")
                .errorMsg(ex.defaultMessage)
                .build();

        return new ResponseEntity<>(response, null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Object>> handleOtherException(Exception ex) {
        BaseResponse<Object> response = BaseResponse.builder()
                .status(Status.FAILURE)
                .error("Bad Request")
                .errorMsg(ex.getMessage())
                .build();

        return new ResponseEntity<>(response, null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
