package com.tencent.wxcloudrun.controller;


import com.tencent.wxcloudrun.model.PartsObject;
import com.tencent.wxcloudrun.service.CustomLazyModelFactory;
import com.tencent.wxcloudrun.service.LazyPartsObjectModel;
import com.tencent.wxcloudrun.service.UserService;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class PartsListBean {


    @Getter
    @Inject
    private CustomLazyModelFactory customLazyModelFactory;


    @Getter @Setter
    private PartsObject selectedPartsObject;

    @Getter
    LazyPartsObjectModel lazyModel;

    @PostConstruct
    public void init() {
        lazyModel = customLazyModelFactory.getLazyPartsObjectModel();
    }


    public void onSelectChange() {
    }
}