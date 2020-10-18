package com.qualitychemicals.qciss.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST)
public class InvalidValuesException extends RuntimeException{
    private static final long serialVersionUID=1L;
    public InvalidValuesException(String message){
        super(message);

    }
}
