package com.lmy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录信息响应类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginInfo {
    private Integer id;
    private String username;
    private String name;
    private String token;
}
