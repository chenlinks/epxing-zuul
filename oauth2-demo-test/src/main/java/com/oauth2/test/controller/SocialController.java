package com.oauth2.test.controller;

import com.oauth2.test.utils.SignatureUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author chenling
 * @date 2020/2/7 18:36
 * @since V1.0.0
 */
@RestController
@RequestMapping("/weChat")
@Slf4j
public class SocialController {


        @GetMapping("/handler")
        public void  weChatProcess(HttpServletRequest request, HttpServletResponse response){
            log.info("开始校验信息是否是从微信服务器发出");
            // 签名
            String signature = request.getParameter("signature");
            // 时间戳
            String timestamp = request.getParameter("timestamp");
            // 随机数
            String nonce = request.getParameter("nonce");

            // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败

            if (SignatureUtil.checkSignature(signature, timestamp, nonce)) {
                // 随机字符串
                String echostr = request.getParameter("echostr");
                log.debug("接入成功，echostr {}", echostr);
                try {
                    response.getWriter().write(echostr);
                } catch (IOException e) {
                    e.printStackTrace();
                    log.error("ERROR",e);
                }
            }

        }



}
