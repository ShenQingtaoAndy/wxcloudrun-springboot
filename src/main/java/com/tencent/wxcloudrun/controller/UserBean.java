package com.tencent.wxcloudrun.controller;


import com.tencent.wxcloudrun.dao.UserMapper;
import com.tencent.wxcloudrun.model.User;
import lombok.Getter;
import lombok.Setter;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class UserBean {



    private List<User> users;

    @Getter @Setter
    private User selectedUser;

    @Inject
    UserMapper userMapper;

    public UserBean() {
        users = new ArrayList<>();
        selectedUser = new User();
    }


    public List<User> getUsers() {
        users= userMapper.selectAllUsers();
        return users;
    }


    public void createUser() {

        userMapper.insertUser(selectedUser);
    }

    public void updateUser() {
        userMapper.updateUserById(selectedUser);
        selectedUser = new User();
    }

    public void deleteUser() {

        userMapper.deleteUserById(selectedUser.getId());
        selectedUser = new User();
    }
}