package com.tencent.wxcloudrun.controller;


import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.apache.logging.log4j.util.Strings;
import org.primefaces.model.file.UploadedFile;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.Map;

@Slf4j
@Named
@ViewScoped
public class TestController implements Serializable {


    //testing
    private static final String APP_ID = "wxa840921ec0f3778e";
    private static final String SECRET = "79989e84a65e55947148777e49654d26";
//
//    private static final String APP_ID = "wx39fea4d428f90a64";
//    private static final String SECRET = "fb9912d4767a68f4909d9eeea619fca1";

    @Getter @Setter
    private UploadedFile file;

    @Getter
    @Setter
    private UploadedFile originalImageFile;


    private String openId;

    @Getter
    @Setter
    private JSONObject formObject = new JSONObject();

    @PostConstruct
    public void init() {


    }

    public  String userInfo(){

        JSONObject jsonObject = new JSONObject();
        Map<String, String> requestHeaderMap = FacesContext.getCurrentInstance().getExternalContext().getRequestHeaderMap();
        jsonObject.putAll(requestHeaderMap);

        return jsonObject.toJSONString();
    }

    public void query() {
        formObject.put("id", formObject.hashCode());
        formObject.put("controllerid", this.hashCode());

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

        HttpSession session = (HttpSession)externalContext.getSession(true);
        formObject.put("sessionid", session.getId());

    }

    public void handleFileUpload() {
        if (originalImageFile != null && originalImageFile.getContent() != null && originalImageFile.getContent().length > 0 && originalImageFile.getFileName() != null) {

            FacesMessage msg = new FacesMessage("Successful", this.originalImageFile.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, msg);


        }else {
            FacesMessage emsg = new FacesMessage("Fail to upload");
        }
    }

    public void onComplete(){
        FacesMessage msg = new FacesMessage("Successful", this.originalImageFile.getFileName() + " is loaded.");
    }

    public Integer getExcelProgress(){
        return 50;
    }


    public String getOpenId() throws WxErrorException {

        if (!Strings.isEmpty(this.openId)) {
            log.info("has openId:{}", openId);
            return this.openId;
        }

        // 从请求头或者参数中获取code（微信授权码）
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request  = (HttpServletRequest)externalContext.getRequest();

        String code = request.getParameter("code");
        if(Strings.isEmpty(code)){
            return "none";
        }
        log.info("code:{}", code);
        WxMpDefaultConfigImpl config = new WxMpDefaultConfigImpl();
        config.setAppId(APP_ID);
        config.setSecret(SECRET);
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(config);
        // 通过code换取用户的OpenId等信息
        String newOpenId = wxMpService.getOAuth2Service().getAccessToken(code).getOpenId();
        log.info("openId:{}", newOpenId);
        this.openId = newOpenId;
        return openId;
    }

}
