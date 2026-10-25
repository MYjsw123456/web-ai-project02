package com.lmy.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

//@WebFilter("/*") //拦截所有请求
@Slf4j
public class DemoFilter implements Filter {
    //初始化方法
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("init 初始化方法....");
        Filter.super.init(filterConfig);
    }
    //拦截请求后，执行，会执行多次
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("doFilter 拦截请求....");
        //放行
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        log.info("destroy 销毁方法....");
        Filter.super.destroy();
    }
}
