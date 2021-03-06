package com.epxing.zuul.config;

import com.epxing.zuul.constant.BaseConstants;
import com.epxing.zuul.filter.AuthHeaderFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import javax.servlet.Filter;
import java.util.Arrays;


@Configuration
@Order(1)
@EnableWebSecurity
@EnableOAuth2Sso
public class ZuulWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and().httpBasic().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 退出的URL是/logout
                .logout().logoutUrl(BaseConstants.LOGOUT_URL).permitAll()
                // 退出成功后，跳转到/路径。
                .logoutSuccessUrl(BaseConstants.LOGIN_URL).permitAll();
    }

}