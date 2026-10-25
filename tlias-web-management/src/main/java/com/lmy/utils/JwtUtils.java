package com.lmy.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

/**
 * JWT令牌操作工具类
 */
public class JwtUtils {

    // 加密密钥
    private static final String SIGN_KEY = "bG15";
    // 令牌过期时间：12小时
    private static final long EXPIRE_TIME = 12 * 60 * 60 * 1000L;

    /**
     * 生成JWT令牌
     *
     * @param dataMap 需要存入令牌的自定义信息
     * @return JWT令牌字符串
     */
    public static String generateToken(Map<String, Object> dataMap) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, SIGN_KEY)  // 指定加密算法和密钥
                .addClaims(dataMap)                            // 添加自定义信息
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME)) // 设置到期时间(12小时)
                .compact();                                    // 生成令牌
    }

    /**
     * 解析JWT令牌
     *
     * @param token JWT令牌字符串
     * @return 令牌中携带的自定义信息
     */
    public static Claims parserToken(String token) {
        return Jwts.parser()
                .setSigningKey(SIGN_KEY)   // 指定解密密钥
                .parseClaimsJws(token)     // 解析令牌
                .getBody();                // 获取自定义信息
    }

}
