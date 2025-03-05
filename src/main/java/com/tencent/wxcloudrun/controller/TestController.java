package com.tencent.wxcloudrun.controller;


import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.file.UploadedFile;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

@Named
@ViewScoped
public class TestController implements Serializable {



    @Getter @Setter
    private UploadedFile file;

    @Getter
    @Setter
    private UploadedFile originalImageFile;


    @Getter
    @Setter
    private JSONObject formObject = new JSONObject();

    @PostConstruct
    public void init() {

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



}
