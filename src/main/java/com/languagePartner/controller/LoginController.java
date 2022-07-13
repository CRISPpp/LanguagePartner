package com.languagePartner.controller;



import com.languagePartner.common.R;
import com.languagePartner.entity.LoginBody;
import com.languagePartner.entity.User;
import com.languagePartner.security.service.SysLoginService;
import com.languagePartner.service.UserService;
import com.languagePartner.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录Controller
 */
@Slf4j
@RestController
public class LoginController
{
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public R<String> login(@RequestBody LoginBody loginBody){
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword());
        return R.success(token);
    }
}