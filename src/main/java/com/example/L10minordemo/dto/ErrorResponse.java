package com.example.L10minordemo.dto;

import lombok.Data;

@Data
public class ErrorResponse {

    private Integer httpStatus;

    private String exception;

    private String message;
}
