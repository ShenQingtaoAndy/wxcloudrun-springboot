package com.tencent.wxcloudrun.controller;


import com.google.common.collect.ImmutableMap;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.cons.DeviceCategory;
import com.tencent.wxcloudrun.dto.NewQueryPartsObjectRequest;
import com.tencent.wxcloudrun.dto.QueryPartsObjectRequest;
import com.tencent.wxcloudrun.dto.SearchPartsObjectRequest;
import com.tencent.wxcloudrun.service.PartsRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@Slf4j
@RestController
public class QueryRequestController {



    @Autowired
    PartsRequestService partsRequestService;

    //查询备件
    @PostMapping(value = "/api/getPartsObject")
    ApiResponse getPartsObject(@RequestBody SearchPartsObjectRequest request) {

        log.info("/api/getPartsObject:" );
        return ApiResponse.ok(partsRequestService.searchPartsObject(request));
    }


    //拉取分类列表
    @PostMapping(value = "/api/getCategory")
    ApiResponse getCategory() {

        log.info("/api/getCategory:" );
        return ApiResponse.ok(Arrays.stream(DeviceCategory.values()).map(i-> ImmutableMap.of("key", i.name(), "value", i.getCategoryName())));
    }

    //根据已有备件发起查询
    @PostMapping(value = "/api/queryPartsObject")
    ApiResponse queryPartsObject(@RequestBody QueryPartsObjectRequest request) {

        log.info("/api/queryPartsObject:" );
        partsRequestService.queryPartsObject(request);
        return ApiResponse.ok(true);
    }

    //创建新备件及发起查询
    //根据已有备件发起查询
    @PostMapping(value = "/api/newQueryPartsObject")
    ApiResponse newQueryPartsObject(@RequestBody NewQueryPartsObjectRequest request) {

        log.info("/api/newQueryPartsObject:" );
        partsRequestService.newQueryPartsObject(request);
        return ApiResponse.ok(true);
    }



}
