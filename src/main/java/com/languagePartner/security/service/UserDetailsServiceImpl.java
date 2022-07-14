package com.languagePartner.security.service;

import com.languagePartner.common.Constants;
import com.languagePartner.entity.LoginUser;
import com.languagePartner.entity.User;
import com.languagePartner.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * 用户验证处理
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UserService userService;


    public UserDetails createLoginUser(User user){
        return new LoginUser(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.selectUserByUserName(username);
        if(user == null){
            log.info("登录用户：{} 不存在", username);
            throw new RuntimeException("登录用户：" + username + " 不存在");
        }
        else if(Constants.DELETED.equals(user.getDelFlag())){
            log.info("登录用户：{} 被删除", username);
            throw new RuntimeException("登录用户：" + username + " 已被删除");
        }
        return createLoginUser(user);
    }
}
