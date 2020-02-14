package com.oauth2.test.controller;

import cn.hutool.json.JSONUtil;
import com.oauth2.test.entity.TokenInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author chenling
 * @date 2020/2/6 2:32
 * @since V1.0.0
 */
@RestController
@RequestMapping("/client")
@Slf4j
public class ClientController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/getToken")
    public String  getToken(){
        String get_token="http://localhost:8089/oauth/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth("client-client", "123123");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "client_credentials");
        params.add("scope","read_userinfo");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

        ResponseEntity<TokenInfo> token = restTemplate.exchange(get_token, HttpMethod.POST, entity, TokenInfo.class);

        log.info("token:" + token);

        return JSONUtil.toJsonStr(token);
    }
}
