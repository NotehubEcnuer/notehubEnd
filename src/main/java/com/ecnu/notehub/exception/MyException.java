package com.ecnu.notehub.exception;

import com.ecnu.notehub.enums.ResultEnum;
import lombok.Getter;

/**
 * @author onion
 * @date 2019/11/15 -2:18 下午
 */
@Getter
public class MyException extends RuntimeException{
    private Integer code;
    public MyException(ResultEnum resultEnums){
        super(resultEnums.getMessage());
        this.code=resultEnums.getCode();
    }
    public MyException(String message, Integer code){
        super(message);
        this.code=code;
    }
}
