package com.ecnu.notehub.enums;

import lombok.Getter;

/**
 * @author onion
 * @date 2019/11/16 -2:17 下午
 */
@Getter
public enum RoleEnum {
    USER(0),
    ADMIN(1),
    ;
    private int code;
    RoleEnum(int code){
        this.code = code;
    }
}
