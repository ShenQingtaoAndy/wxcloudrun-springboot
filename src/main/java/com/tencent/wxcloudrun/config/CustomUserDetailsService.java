package com.tencent.wxcloudrun.config;

import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

        User userBySSO = userService.getUserBySSO(sso);
        if(null != userBySSO){
            return buildLocalUserDetails(userBySSO);
        }
        throw new UsernameNotFoundException("用户 " + sso + " 未找到");
    }

    public UserDetails loadUserByOpenId(String openId) {
        User userByOpenId = userService.getUserByOpenId(openId);
        if(null == userByOpenId){
            userByOpenId = new User();
            userByOpenId.setStatus("New");
            userByOpenId.setOpenid(openId);
            userByOpenId.setRoles("");
            userService.updateUser(userByOpenId);

        }
        LocalUserDetails localUserDetails = buildLocalUserDetails(userByOpenId);
        localUserDetails.setFromOpenid(true);
        return localUserDetails;
    }


    private LocalUserDetails buildLocalUserDetails(User user) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        Arrays.stream(user.getRoles().split(","))
                .map(i->"ROLE_"+i)
                .map(SimpleGrantedAuthority::new)
                .forEach(authorities::add);
        LocalUserDetails localUserDetails = new LocalUserDetails();
        localUserDetails.setLocalUser(user);
        localUserDetails.setUsername(user.getName());
        localUserDetails.setAuthorities(authorities);
        localUserDetails.setPassword("");
        localUserDetails.setFromOpenid(false);
        return localUserDetails;

    }
}