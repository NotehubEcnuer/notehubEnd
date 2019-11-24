package com.ecnu.notehub.enums;

import lombok.Getter;

/**
 * @author onion
 * @date 2019/11/15 -1:53 下午
 */
@Getter
public enum ResultEnum {
    //code从100开始
    EMAIL_IN_USE(100, "邮箱已经被注册"),
    INVALID_TOKEN(101, "无效的令牌"),
    MUST_LOGIN(102, "会话过期，请先登陆"),
    CODE_NOT_EXIST(103, "验证码不存在"),
    WRONG_CODE(104, "验证码错误"),
    ;
    private int code;
    private String message;
    ResultEnum(int code, String message){
        this.code = code;
        this.message = message;
    }
}
