package com.prior.marketplace.exception;

import com.prior.marketplace.model.ResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResponseModel<String>> handleException(CustomException ex) {
        ResponseModel<String> responseModel = new ResponseModel<>();
        responseModel.setStatus(500);
        responseModel.setDescription("error " + ex.getMessage());
        return new ResponseEntity<>(responseModel,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
