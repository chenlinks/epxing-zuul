package com.example.security.manager;

import com.example.security.provider.MyAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * @author chenling
 * @date 2020/2/11 11:20
 * @since V1.0.0
 */
@Component
public class UserPasswordProviderManager implements AuthenticationManager {

    @Autowired
    private MyAuthenticationProvider MyAuthenticationProvider;

    public UserPasswordProviderManager(){

    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
    }
}
