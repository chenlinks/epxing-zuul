package com.epxing.zuul.filter;

import com.epxing.zuul.constant.BaseConstants;
import com.epxing.zuul.utils.AuthUtils;
import com.fasterxml.jackson.core.filter.TokenFilter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private AuthUtils authUtils;

    /**
     * 访问黑名单
     */
    private List<String>  blackList = new LinkedList<>();


    public AuthHeaderFilter() {
        super();
        //处理白名单，实际应用中，应该从数据库读取
    }


    @Override
    public Object run() throws ZuulException {

        log.info("。。。。进入自定义网关。。。。。");
        //向header中添加鉴权令牌
        RequestContext requestContext = RequestContext.getCurrentContext();
        //获取header
        HttpServletRequest request = requestContext.getRequest();
        String authorization = request.getHeader("authorization");
        String requestURL = request.getRequestURL().toString();

        //设置响应字符编码
        requestContext.getResponse().setCharacterEncoding("UTF-8");
        requestContext.getResponse().setContentType("application/json; charset=utf-8");

        if(blackList.contains(requestURL)){
            //返回错误提示
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseBody(BaseConstants.INVALID_TOKEN);
            requestContext.setResponseStatusCode(403);
            return null;
        }

        if (StringUtils.isEmpty(authorization) && !requestURL.contains(BaseConstants.LOGIN_URL)) {
            //返回错误提示
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseBody(BaseConstants.INVALID_TOKEN);
            requestContext.setResponseStatusCode(401);
            return null;
        }

        //token校验
        try {
            authUtils.getRequestUserInfo(request);
        }catch (Exception e){
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

}
