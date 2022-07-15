package com.languagePartner.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.languagePartner.entity.User;

public interface UserService extends IService<User> {
    public User selectUserByUserName(String userName);
}
