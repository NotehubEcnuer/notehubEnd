package com.ecnu.notehub.service.impl;

import com.ecnu.notehub.dao.UserDao;
import com.ecnu.notehub.domain.User;
import com.ecnu.notehub.dto.UserRequest;
import com.ecnu.notehub.enums.ResultEnum;
import com.ecnu.notehub.exception.MyException;
import com.ecnu.notehub.service.MailService;
import com.ecnu.notehub.service.UserService;
import com.ecnu.notehub.util.CodeUtil;
import com.ecnu.notehub.util.JwtUtil;
import com.ecnu.notehub.util.KeyGenerateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author onion
 * @date 2019/11/16 -1:58 下午
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private MailService mailService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private BCryptPasswordEncoder encoder;
    @Value("600")
    private int expireTime;
    @Value("This is the code from notehub")
    private String subject;

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

    @Override
    public Map<String, String> login(UserRequest userRequest) {
        String account = userRequest.getAccount();
        String password = userRequest.getPassword();
        User user = userDao.findByAccount(account);
        if (user == null){
            throw new MyException(ResultEnum.USER_NOT_EXIST);
        }
        if (encoder.matches(password, user.getPassword())){
            throw new MyException(ResultEnum.WRONG_PASSWORD);
        }
        Map <String, String> map = new HashMap<>();
        String token = JwtUtil.createJwt(user);
        String nickname = user.getNickname();
        map.put("token", token);
        map.put("nickname", nickname);
        return map;
    }

    @Override
    public void sendCode(String email) {
        String code = CodeUtil.getCode();
        redisTemplate.opsForValue().set("code_"+email, code, expireTime, TimeUnit.SECONDS);
        String content = "your code is " + code + " , please complete your registration in 10 minutes.";
        mailService.sendMail(email,subject,content);
    }


}
