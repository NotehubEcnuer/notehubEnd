package com.ecnu.notehub.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * @author onion
 * @date 2019/11/22 -6:56 下午
 */
@Data
public class UserRequest {
    @Email
    @NotEmpty
    private String account;
    @NotEmpty
    private String password;
    private String code;
    private String token;
    private String nickname;
    private String ip;
    private Integer role;
}
