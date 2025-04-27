package com.tencent.wxcloudrun.controller.beans;


import com.tencent.wxcloudrun.cons.UserStatus;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.CustomLazyModelFactory;
import com.tencent.wxcloudrun.service.LazyUserDataModel;
import com.tencent.wxcloudrun.service.UserService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Slf4j
@Named
@ViewScoped
public class MobileMainPageBean {


    @Getter
    @Inject
    private CustomLazyModelFactory customLazyModelFactory;

    @Inject
    private UserService userService;

    @Getter @Setter
    private User currentUser;


    @Getter
    LazyUserDataModel lazyModel;

    @PostConstruct
    public void init() {
        currentUser = BeansUtil.getUser();
        if (currentUser == null) {
            currentUser = new User();
            currentUser.setStatus(UserStatus.INACTIVE.name());
        }
    }

    public void createNewUser(){
        currentUser.setStatus(UserStatus.NEW.name());
        userService.updateUser(currentUser);
    }



}