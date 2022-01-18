package com.hyunsoo.detectapp.beans;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ScenarioApiException extends RuntimeException {
    private HttpStatus status;

    public ScenarioApiException(HttpStatus status, String message){
        super(message);
        this.status = status;
    }
}
