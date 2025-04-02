package com.tencent.wxcloudrun.config;

import com.tencent.wxcloudrun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;


@Service
public class CustomUserDetailsService implements UserDetailsService {


    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String sso) throws UsernameNotFoundException {

        com.tencent.wxcloudrun.model.User userBySSO = userService.getUserBySSO(sso);

        if(null != userBySSO){

            Collection<GrantedAuthority> authorities = new ArrayList<>();
            Arrays.stream(userBySSO.getRoles().split(","))
                    .map(i->"ROLE_"+i)
                    .map(SimpleGrantedAuthority::new)
                    .forEach(authorities::add);
            LocalUserDetails localUserDetails = new LocalUserDetails();
            localUserDetails.setLocalUser(userBySSO);
            localUserDetails.setUsername(userBySSO.getName());
            localUserDetails.setAuthorities(authorities);
            localUserDetails.setPassword("");
            return localUserDetails;
        }

        throw new UsernameNotFoundException("用户 " + sso + " 未找到");
    }
}    