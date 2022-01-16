package com.hyunsoo.detectapp.beans;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DetectApiException extends RuntimeException {

    private HttpStatus status;

    public DetectApiException(HttpStatus status, String message){
        super(message);
        this.status = status;
    }
}
