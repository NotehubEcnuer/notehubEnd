package com.ecnu.notehub.dto;

import lombok.Data;

/**
 * @author onion
 * @date 2019/11/22 -6:56 下午
 */
@Data
public class UserRequest {
    private String account;
    private String password;
    private String code;
    private String token;
}
