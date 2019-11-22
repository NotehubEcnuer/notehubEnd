package com.ecnu.notehub.aop;

import com.ecnu.notehub.annotation.LoginRequired;
import com.ecnu.notehub.enums.ResultEnum;
import com.ecnu.notehub.exception.MyException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author onion
 * @date 2019/11/22 -7:26 下午
 */
@Aspect
@Component
@Slf4j
public class VerificationAspect {
    @Pointcut("execution(public * com.ecnu.notehub.controller.*.*(..))")
    public void verify(){}

    private boolean isLoginRequired(Method method) {
        boolean result = false;
        if (method.isAnnotationPresent(LoginRequired.class)) {
            result = method.getAnnotation(LoginRequired.class).loginRequired();
        }
        return result;
    }

    @Around("verify()")
    public Object verifyLogin(ProceedingJoinPoint pjp) throws Throwable{
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();

        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();

        if (isLoginRequired(method)){
            String token = request.getHeader("USER_TOKEN");
            if (StringUtils.isEmpty(token))
                throw new MyException(ResultEnum.MUST_LOGIN);
        }
        return pjp.proceed();
    }

}
