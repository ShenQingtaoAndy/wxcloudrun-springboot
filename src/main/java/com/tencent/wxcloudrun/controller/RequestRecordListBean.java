package com.tencent.wxcloudrun.controller;


import com.tencent.wxcloudrun.config.LocalUserDetails;
import com.tencent.wxcloudrun.cons.RequestStatus;
import com.tencent.wxcloudrun.model.RequestRecord;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.CustomLazyModelFactory;
import com.tencent.wxcloudrun.service.LazyRequestRecordModel;
import com.tencent.wxcloudrun.service.PartsRequestService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.security.Principal;

@Named
@ViewScoped
public class RequestRecordListBean {


    @Getter
    @Inject
    private CustomLazyModelFactory customLazyModelFactory;

    @Inject
    private PartsRequestService partsRequestService;

    @Getter @Setter
    private RequestRecord selectedRecord;

    @Getter @Setter
    private RequestRecord editingRecord;

    @Getter
    LazyRequestRecordModel lazyModel;

    @PostConstruct
    public void init() {
        lazyModel = customLazyModelFactory.getLazyRequestRecordModel();
    }


    public void onSelectChange() {

        System.out.println("onSelectChange");
    }

    public void prepareFill(RequestRecord requestRecord) {

        editingRecord = requestRecord;
    }

    public void updateRecord() {

        User user = BeansUtil.getUser();
        editingRecord.setResponserId(user.getId());
        editingRecord.setStatus(RequestStatus.Processing.name());
        partsRequestService.updateRecord(editingRecord);
        editingRecord = new RequestRecord();
    }

    public void finishRecord() {

        User user = BeansUtil.getUser();
        editingRecord.setResponserId(user.getId());
        editingRecord.setStatus(RequestStatus.Finished.name());
        partsRequestService.updateRecord(editingRecord);
        editingRecord = new RequestRecord();
    }
}