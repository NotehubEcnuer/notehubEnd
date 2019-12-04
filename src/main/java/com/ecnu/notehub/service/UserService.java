package com.ecnu.notehub.service;

import com.ecnu.notehub.domain.User;
import com.ecnu.notehub.dto.UserRequest;

import java.util.Map;

/**
 * @author onion
 * @date 2019/11/29 -5:18 下午
 */
public interface UserService {
    User findByEmail(String email);

    void register(UserRequest userRequest);

    Map<String, String> login(UserRequest userRequest);

    void sendCode(String email);
}
