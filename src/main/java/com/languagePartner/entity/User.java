package com.languagePartner.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 12345L;//序列化版本
    private Long id;
    private String userName;
    private String password;
}
