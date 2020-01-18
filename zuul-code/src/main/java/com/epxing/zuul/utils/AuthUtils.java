package com.epxing.zuul.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chenling
 * @date 2020/1/15 8:24
 * @since V1.0.0
 */
@Slf4j
@Component
public class AuthUtils {

    @Value("${security.oauth2.resource.jwt.key-value}")
    private   String jwtKey;

    public  Object getRequestUserInfo(HttpServletRequest req) {
        String header = req.getHeader("Authorization");
        String token = StringUtils.substringAfter(header, "Bearer");
        Claims claims;
        try {
            //验证token
            claims = Jwts.parser().setSigningKey(jwtKey.getBytes("UTF-8")).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new RuntimeException("token 验证错误");
        }
        Object userinfo = claims.get("userinfo");
        log.info("当前登陆用户信息：{}",userinfo);
        return userinfo;
    }
}
