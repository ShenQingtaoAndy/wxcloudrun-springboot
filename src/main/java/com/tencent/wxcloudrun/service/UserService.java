package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.cons.UserStatus;
import com.tencent.wxcloudrun.dao.UserMapper;
import com.tencent.wxcloudrun.dao.UserRepository;
import com.tencent.wxcloudrun.dto.CreateUserRequest;
import com.tencent.wxcloudrun.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    public  User getUserByOpenId(String openId){

        User user = userRepository.findByOpenid(openId);
        if (user != null){
            return user;
        }
        user = new User();
        user.setOpenid(openId);
        user.setStatus(UserStatus.NONE.name());
        return user;

    }




    public User createUserByOpenId(CreateUserRequest request) {

        User user = userRepository.findByOpenid(request.getOpenid());
        if (user == null){
            user = new User();
            user.setOpenid(request.getOpenid());
        }

        user.setSso(request.getSso());
        user.setName(request.getName());
        user.setStatus(UserStatus.NEW.name());
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());

        user = userRepository.save(user);
        return user;

    }

    public void updateUser(User user){
        user.setUpdateTime(new Date());
        userRepository.save(user);
    }

    public void deleteUser(User user){
        userRepository.delete(user);
    }

}


