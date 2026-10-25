package com.lmy.controller;

import com.lmy.mapper.EmpMapper;
import com.lmy.pojo.Emp1;
import com.lmy.pojo.LoginInfo;
import com.lmy.pojo.Result;
import com.lmy.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录控制器
 */

@Slf4j
@RestController
public class LoginController {


    @Autowired
    private EmpService empService;

    /**
     * 登录方法
     */
    @PostMapping("/login")
    public Result login(@RequestBody Emp1 emp) {
        log.info("登录信息{}", emp);
        LoginInfo loginInfo = empService.login(emp);

        if (loginInfo != null) {
            return Result.success(loginInfo);
        }
        return Result.error("用户名或密码错误");
    }
}
