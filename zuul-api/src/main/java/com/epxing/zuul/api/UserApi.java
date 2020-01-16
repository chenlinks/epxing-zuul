package com.epxing.zuul.api;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chenling
 * @date 2020/1/15 0:45
 * @since V1.0.0
 */
@FeignClient("epxing-zuul")
@RequestMapping("/api/user")
public interface UserApi {

    @RequestMapping("/getCurrentUser")
    Object getCurrentUser(Authentication authentication, HttpServletRequest request);

    //https://blog.csdn.net/baidu_22254181/article/details/86983790
    //还没有实现完
}
