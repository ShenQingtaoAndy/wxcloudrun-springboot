package com.tencent.wxcloudrun.controller;


import com.tencent.wxcloudrun.cons.UserStatus;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.CustomLazyModelFactory;
import com.tencent.wxcloudrun.service.LazyUserDataModel;
import com.tencent.wxcloudrun.service.UserService;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.util.Strings;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleSelectEvent;
import org.primefaces.event.UnselectEvent;

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

    @Getter @Setter
    private String[] selectedRoles;

    @Getter
    LazyUserDataModel lazyModel;

    @PostConstruct
    public void init() {
        lazyModel = customLazyModelFactory.getLazyUserDataModel();
    }



    public void updateUser() {

        selectedUser.setRoles(String.join(",", selectedRoles));
        userService.updateUser(selectedUser);
        selectedUser = null;
        selectedRoles=null;
    }

    public void deleteUser() {

        userService.deleteUser(selectedUser);
        selectedUser = null;
        selectedRoles=null;
    }

    public void onSelectUser() {
        selectedRoles = Strings.isEmpty(selectedUser.getRoles()) ? new String[0]: selectedUser.getRoles().split(",");
    }

    public void prepareView() {

    }

    public void activeUser() {
        selectedUser.setStatus(UserStatus.ACTIVE.name());
        updateUser();
    }
}