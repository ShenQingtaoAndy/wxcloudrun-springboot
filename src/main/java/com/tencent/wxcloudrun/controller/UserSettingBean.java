package com.tencent.wxcloudrun.controller;


import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.CustomLazyModelFactory;
import com.tencent.wxcloudrun.service.LazyUserDataModel;
import com.tencent.wxcloudrun.service.UserService;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.el.MethodExpression;
import javax.faces.event.ActionListener;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class UserSettingBean {


    @Getter
    @Inject
    private CustomLazyModelFactory customLazyModelFactory;

    @Inject
    private UserService userService;

    @Getter @Setter
    private User selectedUser;

    @Getter
    LazyUserDataModel lazyModel;

    @PostConstruct
    public void init() {
        lazyModel = customLazyModelFactory.getLazyUserDataModel();
    }



    public void updateUser() {

        userService.updateUser(selectedUser);
        selectedUser = null;
    }

    public void deleteUser() {

        selectedUser = new User();
    }

    public void onSelectUser() {

    }

    public void prepareView() {

    }
}