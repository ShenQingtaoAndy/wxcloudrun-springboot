package com.tencent.wxcloudrun.controller.beans;


import com.tencent.wxcloudrun.config.WechatAuthenticationException;
import com.tencent.wxcloudrun.cons.UserStatus;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.CustomLazyModelFactory;
import com.tencent.wxcloudrun.service.LazyUserDataModel;
import com.tencent.wxcloudrun.service.UserService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.AuthenticationException;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@Named
@ViewScoped
public class MobileMainPageBean {


    @Getter
    @Inject
    private CustomLazyModelFactory customLazyModelFactory;

    @Inject
    private UserService userService;


    @Inject
    private WxMpService weixinMpService;

    @Getter @Setter
    private User currentUser;


    @Getter
    LazyUserDataModel lazyModel;

    @PostConstruct
    public void init() {

        if(null == currentUser) {
            currentUser = queryCurrentUser();
        }
    }

    public void createNewUser(){
        currentUser.setStatus(UserStatus.NEW.name());
        userService.updateUser(currentUser);
    }


    private User queryCurrentUser() {

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request  = (HttpServletRequest)externalContext.getRequest();

        String code = request.getParameter("code");
        if(Strings.isEmpty(code)){
            return BeansUtil.getUser();
        }else{
            String openId = getOpenId(code);
            User userByOpenId = userService.getUserByOpenId(openId);
            if(null == userByOpenId){
                User user = new User();
                user.setOpenid(openId);
                user.setStatus(UserStatus.NONE.name());
                return user;
            }else {
                return userByOpenId;
            }
        }
    }


    public String getOpenId(String code) throws AuthenticationException {

        // 通过code换取用户的OpenId等信息

        String newOpenId = null;
        try {
            newOpenId = weixinMpService.getOAuth2Service().getAccessToken(code).getOpenId();
        } catch (WxErrorException e) {
            throw new WechatAuthenticationException(e.getMessage());
        }
        log.info("openId:{}", newOpenId);
        return newOpenId;
    }



}