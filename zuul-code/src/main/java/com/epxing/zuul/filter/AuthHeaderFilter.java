package com.epxing.zuul.filter;

import com.epxing.zuul.constant.BaseConstants;
import com.fasterxml.jackson.core.filter.TokenFilter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author chenling
 * @date 2020/1/17 19:32
 * @since V1.0.0
 */
@Component
@Slf4j
@Order(-1000)
public class AuthHeaderFilter extends ZuulFilter {

    public AuthHeaderFilter() {
        super();
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {


        log.info("。。。。。进入网关自定义。。。。。。");

        //向header中添加鉴权令牌
        RequestContext requestContext = RequestContext.getCurrentContext();
        //获取header
        HttpServletRequest request = requestContext.getRequest();
        String authorization = request.getHeader("authorization");
        String requestURL = request.getRequestURL().toString();

        if (StringUtils.isEmpty(authorization) && !requestURL.contains(BaseConstants.LOGIN_URL)) {
            //返回错误提示
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseBody(BaseConstants.INVALID_TOKEN);
            requestContext.setResponseStatusCode(401);
            return null;
        }

        log.info("authorization: {}" ,authorization);
        requestContext.addZuulRequestHeader("Authorization", authorization);

        return null;
    }
}
