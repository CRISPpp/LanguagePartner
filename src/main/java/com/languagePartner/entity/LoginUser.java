package com.languagePartner.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser implements UserDetails, Serializable {

    private static final long serialVersionUID = 442353225232L;
    @Setter
    @Getter private User User;
    @Setter @Getter private String token;
    @Setter @Getter private Long expireTime;
    @Setter @Getter private Long loginTime;

    public LoginUser(User sysUser){
        this.User = sysUser;
    }

    @JSONField(serialize = false)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @JSONField(serialize = false)
    @Override
    public String getPassword() {
        return User.getPassword();
    }

    @JSONField(serialize = false)
    @Override
    public String getUsername() {
        return User.getUserName();
    }

    @JSONField(serialize = false)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JSONField(serialize = false)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JSONField(serialize = false)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JSONField(serialize = false)
    @Override
    public boolean isEnabled() {
        return true;
    }
}