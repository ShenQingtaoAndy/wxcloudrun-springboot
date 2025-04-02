package com.tencent.wxcloudrun.controller;


import com.tencent.wxcloudrun.model.PartsObject;
import com.tencent.wxcloudrun.model.RequestRecord;
import com.tencent.wxcloudrun.service.CustomLazyModelFactory;
import com.tencent.wxcloudrun.service.LazyPartsObjectModel;
import com.tencent.wxcloudrun.service.PartsRequestService;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionListener;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class PartsListBean {


    @Getter
    @Inject
    private CustomLazyModelFactory customLazyModelFactory;

    @Inject
    private PartsRequestService partsRequestService;

    @Getter @Setter
    private PartsObject selectedPartsObject;

    @Getter
    LazyPartsObjectModel lazyModel;


    @Getter @Setter
    List<RequestRecord> requestRecordList = new ArrayList<>();

    @PostConstruct
    public void init() {
        lazyModel = customLazyModelFactory.getLazyPartsObjectModel();
    }


    public void onSelectChange() {
    }

    public List<RequestRecord> loadRecordList(String partsId) {

        List<RequestRecord> list= partsRequestService.searchQueryListByParts(partsId);
        return list;
    }


}