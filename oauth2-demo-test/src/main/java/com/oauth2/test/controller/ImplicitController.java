package com.oauth2.test.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author chenling
 * @date 2020/2/6 2:01
 * @since V1.0.0
 */
@RestController
@RequestMapping("/implicit")
@Slf4j
public class ImplicitController {



    @GetMapping("/getToken")
    public String getToken( @RequestParam(value = "state", required = false) String state, HttpServletRequest request, HttpServletResponse response ){
        log.info("{}",request.getRequestURL());
        return request.getRequestURL().toString();
    }

}
