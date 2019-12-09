package com.ecnu.notehub.aop;

import com.ecnu.notehub.exception.MyException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * @author onion
 * @date 2019/11/22 -7:26 下午
 */
@Aspect
@Component
@Slf4j
public class VerificationAspect {
    @Pointcut("@annotation(com.ecnu.notehub.annotation.LoginRequired)")
    public void verifyLoginPoint(){}
    @Pointcut("@annotation(com.ecnu.notehub.annotation.VerifyParam)")
    public void verifyParamsPoint(){}

    @Before("verifyLoginPoint()")
    public void verifyLogin(JoinPoint joinPoint){
//        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
//        ServletRequestAttributes sra = (ServletRequestAttributes) attributes;
//        HttpServletRequest request = sra.getRequest();
//        String token = request.getHeader("USER_TOKEN");
//        if (StringUtils.isEmpty(token)){
//            throw new MyException(ResultEnum.MUST_LOGIN);
//        }
//        JwtUtil.parseJwt(token);
//        System.out.println("intercept by aop");
    }

    @Before("verifyParamsPoint()")
    public void verifyParams(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        BindingResult result = (BindingResult) args[1];
        if (result.hasErrors()){
            StringBuilder sb = new StringBuilder();
            result.getAllErrors().forEach(e->{
                FieldError error = (FieldError) e;
                String field = error.getField();
                String message = error.getDefaultMessage();
                sb.append(field).append(" : ").append(message).append(' ');
            });
            throw new MyException(sb.toString(), -1);
        }
    }
}
