package com.ijrobotics.ijstorage.api.exceptions;

import org.springframework.http.HttpStatus;

public class CustomExceptionHandler extends RuntimeException {
    private HttpStatus httpStatus;

    public HttpStatus getHttpStatus(){
        return httpStatus;
    }
    public CustomExceptionHandler(HttpStatus httpStatus, String message){
        super(message);
        this.httpStatus=httpStatus;
    }
}
