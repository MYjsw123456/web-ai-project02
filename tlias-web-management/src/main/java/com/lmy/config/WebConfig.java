package com.lmy.config;

import com.lmy.interceptor.DemoInterceptor;
import com.lmy.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置类
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

/*    @Autowired
    private DemoInterceptor demoInterceptor;*/

    @Autowired
    private TokenInterceptor tokenInterceptor;

    //注册拦截器
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor).addPathPatterns("/**")   // 拦截所有请求
                .excludePathPatterns("/login");  // 排除登录请求
    }
}
