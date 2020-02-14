package com.oauth2.test.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author chenling
 * @date 2020/2/5 20:34
 * @since V1.0.0
 */
@Data
public class TokenInfo {

    private String access_token;
    private String refresh_token;
    private String token_type;
    private Long expires_in;
    private String scope;

    private LocalDateTime expireTime;

    public TokenInfo init() {
        // 提前5秒过期时就开始刷新token
        expireTime = LocalDateTime.now().plusSeconds(expires_in - 5);
        return this;
    }

    @JsonIgnore
    public boolean isExpired() {
        return expireTime.isBefore(LocalDateTime.now());
    }



}
