package com.ecnu.notehub.util;

import com.ecnu.notehub.domain.User;
import com.ecnu.notehub.enums.ResultEnum;
import com.ecnu.notehub.exception.MyException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * @author onion
 * @date 2019/11/22 -7:12 下午
 */
public class JwtUtil {
    private static final String key = "ecnuOnion";
    private static final long ttl = 60 * 60 * 24 * 1000;
    public static String createJwt(User user){
        long now = System.currentTimeMillis();
        JwtBuilder builder = Jwts.builder()
                .setId(user.getId())
                .setIssuedAt(new Date(now))
                .signWith(SignatureAlgorithm.HS256, key)
                .setExpiration(new Date(now + ttl));
        return builder.compact();
    }
    public static Claims parseJwt(String jwtStr){
        try{
            return Jwts.parser().setSigningKey(key).parseClaimsJws(jwtStr).getBody();
        }catch (Exception e){
            throw new MyException(ResultEnum.INVALID_TOKEN);
        }
    }
}
