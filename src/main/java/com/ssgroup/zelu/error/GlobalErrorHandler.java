package com.ssgroup.zelu.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(Exception.class)
    public String otherErrorHandler(Exception e) {
        return "error : [ " + e.getMessage() + "]";
    }

}
