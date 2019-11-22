package com.ecnu.notehub.service;

import com.ecnu.notehub.dao.UserDao;
import com.ecnu.notehub.domain.User;
import com.ecnu.notehub.dto.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author onion
 * @date 2019/11/16 -1:58 下午
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public User findByEmail(String email) {
        return userDao.findByAccount(email);
    }

    public void register(UserRequest userRequest) {
    }

    public Map<String, String> login(UserRequest userRequest) {
        return null;
    }
}
