package com.ecnu.notehub.handler;

import com.ecnu.notehub.vo.ResultEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author onion
 * @date 2019/11/15 -2:20 下午
 */
@RestControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ResultEntity exception(Exception e){
        e.printStackTrace();
        return ResultEntity.fail(e.getMessage());
    }
}
