package com.ecnu.notehub.enums;

import lombok.Getter;

/**
 * @author onion
 * @date 2019/11/16 -10:20 上午
 */
@Getter
public enum AuthorityEnum {
    PUBLIC(0),
    PRIVATE(1),
    READONLY(2),
    ;
    private int code;
    AuthorityEnum(int code){
        this.code = code;
    }
}
