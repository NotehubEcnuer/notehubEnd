package com.ecnu.notehub.enums;

import lombok.Getter;

/**
 * @author onion
 * @date 2019/11/16 -10:16 上午
 */
@Getter
public enum  TypeEnum {
    MARKDOWN(0),
    TET(1),
    MMP(2),
    PDF(3),
    ;
    private int code;
    TypeEnum(int code){
        this.code = code;
    }
}
