package com.ep.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/***
 * @author dep
 * @version 1.0
 * @date 2023-04-11 22:01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser implements UserDetails {

    private User user;

    // 表示获取登录用户所有权限
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    // 表示获取密码
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // 表示获取用户名
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // 表示判断账户是否过期
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 表示判断账户是否被锁定
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 表示凭证{密码}是否过期
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 表示当前用户是否可用
    @Override
    public boolean isEnabled() {
        return true;
    }
}
