package com.ecnu.notehub.controller;

import com.ecnu.notehub.annotation.LoginRequired;
import com.ecnu.notehub.domain.User;
import com.ecnu.notehub.dto.UserRequest;
import com.ecnu.notehub.enums.ResultEnum;
import com.ecnu.notehub.exception.MyException;
import com.ecnu.notehub.service.UserService;
import com.ecnu.notehub.vo.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author onion
 * @date 2019/11/16 -1:52 下午
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/checkEmail")
    @LoginRequired(loginRequired = false)
    public ResultEntity checkEmail(@RequestParam String email){
        User user = userService.findByEmail(email);
        if (user != null)
            throw new MyException(ResultEnum.EMAIL_IN_USE);
        return ResultEntity.succeed();
    }

    @PostMapping("/register")
    @LoginRequired(loginRequired = false)
    public ResultEntity register(@RequestBody UserRequest userRequest){
        userService.register(userRequest);
        return ResultEntity.succeed();
    }

    @PostMapping("/login")
    @LoginRequired(loginRequired = false)
    public ResultEntity login(@RequestBody UserRequest userRequest){
        Map<String, String> loginInfo = userService.login(userRequest);
        return ResultEntity.succeed(loginInfo);
    }

    @PostMapping
    public ResultEntity register(@RequestParam String email){
        return null;
    }
}
