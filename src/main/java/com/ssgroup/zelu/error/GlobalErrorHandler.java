package com.ssgroup.zelu.error;

import com.ssgroup.zelu.pojo.Result;
import com.ssgroup.zelu.pojo.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(Exception.class)
    public Result<String> otherErrorHandler(Exception e) {
        // 处理其他异常的方法
        log.error(e.getMessage());
        // 返回错误结果
        return Result.codeFailure(ResultCode.RC404);
    }

}
