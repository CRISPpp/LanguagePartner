package com.languagePartner.controller;



import com.languagePartner.common.R;
import com.languagePartner.entity.User;
import com.languagePartner.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
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
    static Map<Integer, User> userMap = new HashMap<>();

    static {
        //模拟数据库
        User user1 = new User(1L, "张三", "zhangsan");
        userMap.put(1, user1);
        User user2 = new User(2L, "李四", "李四");
        userMap.put(2, user2);
    }

    /**
     * 模拟用户 登录
     */
    @GetMapping("/login")
    public R<String> login(User user)
    {
        for (User dbUser : userMap.values()) {
            if (dbUser.getUserName().equals(user.getUserName()) && dbUser.getPassword().equals(user.getPassword())) {
                log.info("登录成功！生成token！");
                String token = JwtUtil.createToken(dbUser);
                log.info(token);
                return R.success(token);
            }
        }
        return R.error("???");
    }
}