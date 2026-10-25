package com.lmy;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {

    /**
     * 测试生成JWT令牌
     */
    @Test
    public void testGenerateJwt() {

        Map<String, Object> dataMap=new HashMap<>();
        dataMap.put("id",1);
        dataMap.put("username","admin");


        String jwt = Jwts.builder().signWith(SignatureAlgorithm.HS256, "bG15")  //指定加密算法和密钥
                .addClaims(dataMap)    //添加自定义信息
                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000))//设置到期时间
                .compact();   //生成令牌

        System.out.println(jwt);
    }

    /**
     * 解析jwt令牌
     */
    @Test
    public void testParserJwt(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJhZG1pbiIsImV4cCI6MTc5MjkxMjM0M30.wBssSNc10OwPruci4_ZMPuvQyS1XvimOOWbZMTny4dY";

        Claims claims = Jwts.parser().setSigningKey("bG15")
                .parseClaimsJws(token)
                .getBody();
        System.out.println(claims);
    }

}
