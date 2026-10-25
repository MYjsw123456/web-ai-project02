package com.lmy.filter;

import com.lmy.utils.JwtUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * 拦截路径
 * /login  只有访问/login的路径才会被拦截
 * /emps/* 访问/emps下的所有资源都会被拦截
 * /*      访问所有资源，都会被拦截
 */
//@WebFilter("/*")
@Slf4j
public class TokenFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        //1.获取请求路径
        String requestURI = httpRequest.getRequestURI();
        //2.判断请求路径是否包含login
        if (requestURI.contains("/login")) {
            log.info("登录请求，放行");
            chain.doFilter(request, response);
            return;
        }
        //3.获取请求头中的token
        String token = httpRequest.getHeader("token");
        //4.判断token是否存在，如果不存在，说明用户没有登录，返回错误信息（状态码401）
        if (token == null||token.isEmpty()) {
            log.info("令牌不存在，返回错误信息（状态码401）");
            httpResponse.setStatus(401);
            return;
        }
        //5.如果token存在，校验token的合法性，如果不合法，返回错误信息（状态码401）
        try {
            JwtUtils.parserToken(token);
        } catch (Exception e) {
            log.info("令牌不合法，返回错误信息（状态码401）");
            httpResponse.setStatus(401);
            return;
        }
        //6.如果token存在且合法，放行
        log.info("令牌存在且合法，放行");
        chain.doFilter(request, response);
    }
}
