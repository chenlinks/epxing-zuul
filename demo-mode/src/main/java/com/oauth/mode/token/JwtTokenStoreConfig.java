package com.oauth.mode.token;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * jwt token 存储配置
 * @author chenling
 * @date 2020/2/6 9:43
 * @since V1.0.0
 */
@Slf4j
@Configuration
public class JwtTokenStoreConfig {

        @Value("${security.oauth2.resource.jwt.key-value}")
        private String jwtKey;

        @Bean
        @Primary
        public TokenStore  tokenStore(){
            return new JwtTokenStore(jwtAccessTokenConverter());
        }

        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter() {
            JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
            jwtAccessTokenConverter.setSigningKey(jwtKey);
            return jwtAccessTokenConverter;
        }


}
