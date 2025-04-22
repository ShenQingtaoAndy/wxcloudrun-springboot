package com.tencent.wxcloudrun.controller.beans;

import com.tencent.wxcloudrun.config.LocalUserDetails;
import com.tencent.wxcloudrun.model.User;
import org.springframework.security.core.Authentication;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class BeansUtil {

    public static User getUser(){

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Authentication userPrincipal = (Authentication)externalContext.getUserPrincipal();
        LocalUserDetails localUserDetails = (LocalUserDetails)userPrincipal.getPrincipal();
        return localUserDetails.getLocalUser();
    }
}
