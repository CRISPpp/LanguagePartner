package com.languagePartner.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class SecureController {
    /**
     * 查询 用户信息，登录后携带JWT才能访问
     */
    @GetMapping("/secure/getUserInfo")
    public String login(HttpServletRequest request) {
        Integer id = (Integer) request.getAttribute("id");
        String nickName = request.getAttribute("nickName").toString();
        String password= request.getAttribute("password").toString();
        return "当前用户信息id=" + id + ",nickName=" + nickName+ ",password=" + password;
    }

}
