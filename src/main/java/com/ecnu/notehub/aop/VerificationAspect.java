package com.ecnu.notehub.aop;

import com.ecnu.notehub.enums.ResultEnum;
import com.ecnu.notehub.exception.MyException;
import com.ecnu.notehub.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author onion
 * @date 2019/11/22 -7:26 下午
 */
@Aspect
@Component
@Slf4j
public class VerificationAspect {
    @Pointcut("@annotation(com.ecnu.notehub.annotation.LoginRequired)")
    public void verify(){}

    @Before("verify()")
    public void verifyLogin(JoinPoint joinPoint){
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) attributes;
        HttpServletRequest request = sra.getRequest();
        String token = request.getHeader("USER_TOKEN");
        if (StringUtils.isEmpty(token)){
            throw new MyException(ResultEnum.MUST_LOGIN);
        }
        JwtUtil.parseJwt(token);
        System.out.println("intercept by aop");
    }

}
