package com.ecnu.notehub.enums;

import lombok.Getter;

/**
 * @author onion
 * @date 2019/11/15 -1:53 下午
 */
@Getter
public enum ResultEnum {
    //code从100开始
    EMAIL_IN_USE(1000, "邮箱已经被注册"),
    INVALID_TOKEN(1001, "无效的令牌"),
    MUST_LOGIN(1002, "会话过期，请先登陆"),
    CODE_NOT_EXIST(1003, "验证码不存在"),
    WRONG_CODE(1004, "验证码错误"),
    FILE_NOT_EXIST(1005, "文件不存在"),
    FILE_CANNOT_PARSE(1006, "文件解析出错"),
    FILE_UPLOAD_ERROR(1007, "文件上传出错"),
    USER_NOT_EXIST(1008, "用户不存在"),
    WRONG_PASSWORD(1009, "密码错误"),
    ;
    private int code;
    private String message;
    ResultEnum(int code, String message){
        this.code = code;
        this.message = message;
    }
}
