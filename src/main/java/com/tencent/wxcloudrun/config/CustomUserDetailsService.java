package com.tencent.wxcloudrun.config;

import com.tencent.wxcloudrun.cons.UserRole;
import com.tencent.wxcloudrun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {


    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String sso) throws UsernameNotFoundException {

        com.tencent.wxcloudrun.model.User userBySSO = userService.getUserBySSO(sso);
        if(null != userBySSO){
            return User.withUsername(userBySSO.getSso())
                    .password("")
                    .roles(userBySSO.getRoles().split(","))
                    .build();
        }

        throw new UsernameNotFoundException("用户 " + sso + " 未找到");
    }
}    