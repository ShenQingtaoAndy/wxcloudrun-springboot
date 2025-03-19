package com.tencent.wxcloudrun.controller;


import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.CreateUserRequest;
import com.tencent.wxcloudrun.dto.GetUserRequest;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {



    @Autowired
    private UserService userService;

    @PostMapping(value = "/api/getUser")
    ApiResponse getUserByMini(@RequestBody GetUserRequest request) {

        User userByOpenId = userService.getUserByOpenId(request.getOpenid());
        return ApiResponse.ok(userByOpenId);
    }


    @PostMapping(value = "/api/newUser")
    ApiResponse createUserFromMini(@RequestBody CreateUserRequest request) {

        User user = userService.createUserByOpenId(request);
        return ApiResponse.ok(user);
    }
}
