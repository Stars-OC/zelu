package com.ssgroup.zelu.error;

import com.ssgroup.zelu.pojo.Result;
import com.ssgroup.zelu.pojo.ResultCode;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;


@Slf4j
@RestControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(Exception.class)
    public Result<String> otherErrorHandler(Exception e) {

        if (e instanceof MethodArgumentNotValidException || e instanceof ConstraintViolationException) {
            return Result.codeFailure(ResultCode.RC406);
        }

        // 处理其他异常的方法
        log.error(e.getMessage(),e);
        // 返回错误结果
        return Result.codeFailure(ResultCode.RC404);
    }

}
