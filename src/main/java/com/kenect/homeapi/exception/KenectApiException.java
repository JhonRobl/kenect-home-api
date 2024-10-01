package com.kenect.homeapi.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class KenectApiException extends RuntimeException {

    private String message;

    public KenectApiException(String message){
        super(message);
        this.message = message;

    }

}
