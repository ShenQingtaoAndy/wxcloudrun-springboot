package com.tencent.wxcloudrun.controller;


import com.alibaba.fastjson.JSON;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.config.CustomUserDetailsService;
import com.tencent.wxcloudrun.dto.CreateUserRequest;
import com.tencent.wxcloudrun.dto.GetUserRequest;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController {



    @Autowired
    private UserService userService;


    @Autowired
    private CustomUserDetailsService userDetailsService;


    @PostMapping(value = "/api/getUser")
    ApiResponse getUserByMini(@RequestBody GetUserRequest request) {

        log.info(String.format("/api/getUser: %s", JSON.toJSON(request) ));
        User userByOpenId = userService.getUserByOpenId(request.getOpenid());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if( !Strings.isEmpty(userByOpenId.getId()) &&  "anonymousUser".equals(authentication.getName()) ){
            UserDetails userDetails = userDetailsService.buildLocalUserDetails(userByOpenId);
            authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        return ApiResponse.ok(userByOpenId);
    }


    @PostMapping(value = "/api/newUser")
    ApiResponse createUserFromMini(@RequestBody CreateUserRequest request) {

        log.info(String.format("/api/newUser: %s", JSON.toJSON(request) ));
        User user = userService.createUserByOpenId(request);
        return ApiResponse.ok(user);
    }
}
