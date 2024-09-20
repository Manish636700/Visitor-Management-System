package com.example.L10minordemo.config;


import com.example.L10minordemo.Exception.BadRequestException;
import com.example.L10minordemo.Exception.NotFoundException;
import com.example.L10minordemo.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse>handleNotFound(final NotFoundException notFoundException)
    {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(notFoundException.getMessage());
        errorResponse.setException(notFoundException.toString());
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse>handleBadRequest(final BadRequestException badRequestException)
    {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(badRequestException.getMessage());
        errorResponse.setException(badRequestException.toString());
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse>handleRuntimeException(final RuntimeException runtimeException)
    {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(runtimeException.getMessage());
        errorResponse.setException(runtimeException.toString());
        return new ResponseEntity<>(errorResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
