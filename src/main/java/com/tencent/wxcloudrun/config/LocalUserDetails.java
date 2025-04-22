package com.tencent.wxcloudrun.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

public class LocalUserDetails implements UserDetails, Serializable {

    @Getter @Setter
    private String username;

    @Getter @Setter
    private String password;

    @Getter @Setter
    private boolean enabled;


    @Getter @Setter
    private boolean fromOpenid;

    @Getter @Setter
    private Collection<? extends GrantedAuthority> authorities;

    @Getter @Setter
    private  com.tencent.wxcloudrun.model.User localUser;


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

}
