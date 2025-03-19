package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.cons.UserStatus;
import com.tencent.wxcloudrun.dao.UserMapper;
import com.tencent.wxcloudrun.dto.CreateUserRequest;
import com.tencent.wxcloudrun.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;


    public  User getUserByOpenId(String openId){

        List<User> userList = userMapper.queryUserByOpenId(openId);

        if (userList != null && userList.size() == 1){
            return userList.get(0);
        }

        User user = new User();
        user.setOpenid(openId);
        user.setStatus(UserStatus.NONE.name());
        return user;

    }


    public User createUserByOpenId(CreateUserRequest request) {

        User user = new User();
        user.setOpenid(request.getOpenid());
        user.setSso(request.getSso());
        user.setName(request.getName());
        user.setStatus(UserStatus.NEW.name());
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        List<User> userList = userMapper.queryUserByOpenId(request.getOpenid());
        if(userList != null && userList.size() >= 1){
            return user;
        }

        userMapper.insertUser(user);
        return user;

    }

}


