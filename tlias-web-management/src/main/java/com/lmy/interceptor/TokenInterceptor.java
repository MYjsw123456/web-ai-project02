package com.lmy.interceptor;

import com.lmy.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
/*        //1.获取请求路径
        String requestURI = request.getRequestURI();
        //2.判断请求路径是否包含login
        if (requestURI.contains("/login")) {
            log.info("登录请求，放行");
            return true;
        }*/
        //3.获取请求头中的token
        String token = request.getHeader("token");
        //4.判断token是否存在，如果不存在，说明用户没有登录，返回错误信息（状态码401）
        if (token == null||token.isEmpty()) {
            log.info("令牌不存在，返回错误信息（状态码401）");
            response.setStatus(401);
            return false;
        }
        //5.如果token存在，校验token的合法性，如果不合法，返回错误信息（状态码401）
        try {
            JwtUtils.parserToken(token);
        } catch (Exception e) {
            log.info("令牌不合法，返回错误信息（状态码401）");
            response.setStatus(401);
            return false;
        }
        //6.如果token存在且合法，放行
        log.info("令牌存在且合法，放行");
        return true;
    }
}
