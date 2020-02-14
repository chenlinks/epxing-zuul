package com.oauth2.test.controller;

import cn.hutool.json.JSONUtil;
import com.oauth2.test.entity.TokenInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * authorization_code 模式
 * @author chenling
 * @date 2020/2/5 20:26
 * @since V1.0.0
 */
@RestController
@Slf4j
public class AuthorizationCodeController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/callback")
    public  String  callBack(@RequestParam("code") String code, @RequestParam(value = "state", required = false) String state, HttpServletRequest request, HttpServletResponse response){
        log.info("收到验证code:{}",code);
        log.info("收到验证携带常量：{}",state);

        String get_token="http://localhost:8089/oauth/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth("client-code", "123123");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("grant_type", "authorization_code");
        params.add("redirect_uri", "http://localhost:9001/callback");
        params.add("scope","read_userinfo");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

        ResponseEntity<TokenInfo> token = restTemplate.exchange(get_token, HttpMethod.POST, entity, TokenInfo.class);

        log.info("token:" + token);

        return JSONUtil.toJsonStr(token);
    }



}
