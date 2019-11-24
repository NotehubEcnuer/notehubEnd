package com.ecnu.notehub.service;

import com.ecnu.notehub.dao.UserDao;
import com.ecnu.notehub.domain.User;
import com.ecnu.notehub.dto.UserRequest;
import com.ecnu.notehub.enums.ResultEnum;
import com.ecnu.notehub.exception.MyException;
import com.ecnu.notehub.util.KeyGenerateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    private StringRedisTemplate redisTemplate;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public User findByEmail(String email) {
        return userDao.findByAccount(email);
    }

    public void register(UserRequest userRequest) {
        User user = new User();
        String code = redisTemplate.opsForValue().get("code_" + userRequest.getAccount());
        if(code == null){
            throw new MyException(ResultEnum.CODE_NOT_EXIST);
        }
        if(!code.equals(userRequest.getCode())){
            throw new MyException(ResultEnum.WRONG_CODE);
        }
        user.setId(KeyGenerateUtil.genUniqueKey());
        user.setAccount(userRequest.getAccount());
        user.setNickname(userRequest.getNickname());
        user.setPassword(encoder.encode(userRequest.getPassword()));
        user.setLastLoginTime(LocalDateTime.now());
        user.setRegisterTime(LocalDateTime.now());
        user.setLastIp(userRequest.getIp());
        user.setCollects(0);
        user.setDownloads(0);
        user.setPublishes(0);
        user.setRole(userRequest.getRole());
        user.setDisabled(false);
        userDao.save(user);
    }

    public Map<String, String> login(UserRequest userRequest) {
        return null;
    }
}
