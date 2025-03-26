package com.tencent.wxcloudrun.controller;


import com.tencent.wxcloudrun.model.RequestRecord;
import com.tencent.wxcloudrun.service.CustomLazyModelFactory;
import com.tencent.wxcloudrun.service.LazyRequestRecordModel;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class RequestRecordListBean {


    @Getter
    @Inject
    private CustomLazyModelFactory customLazyModelFactory;


    @Getter @Setter
    private RequestRecord selectedRecord;

    @Getter
    LazyRequestRecordModel lazyModel;

    @PostConstruct
    public void init() {
        lazyModel = customLazyModelFactory.getLazyRequestRecordModel();
    }


    public void onSelectChange() {

        System.out.println("onSelectChange");
    }
}