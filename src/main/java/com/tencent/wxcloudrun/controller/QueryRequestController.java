package com.tencent.wxcloudrun.controller;


import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableMap;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.cons.DeviceCategory;
import com.tencent.wxcloudrun.dto.*;
import com.tencent.wxcloudrun.model.RequestRecord;
import com.tencent.wxcloudrun.service.PartsRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

        log.info("/api/getPartsObject: {}", JSON.toJSONString(request) );
        return ApiResponse.ok(partsRequestService.searchPartsObject(request));
    }


    //拉取分类列表
    @PostMapping(value = "/api/getCategory")
    ApiResponse getCategory() {

        log.info("/api/getCategory:" );
        return ApiResponse.ok(Arrays.stream(DeviceCategory.values()).map(i-> ImmutableMap.of("key", i.name(), "value", i.getCategoryName())));
    }


    //创建新备件及发起查询
    @PostMapping(value = "/api/newQueryPartsObject")
    ApiResponse newQueryPartsObject(@RequestBody NewQueryPartsObjectRequest request) {

        log.info("/api/newQueryPartsObject:{}", JSON.toJSONString(request) );
        partsRequestService.newQueryPartsObject(request);
        return ApiResponse.ok(true);
    }


    //查询请求列表
    @PostMapping(value = "/api/searchQueryList")
    ApiResponse searchQueryList(@RequestBody SearchQueryListRequest request) {

        log.info("/api/searchQueryList:{}", JSON.toJSONString(request) );
        Page<RequestRecord> res = partsRequestService.searchQueryList(request);
        return ApiResponse.ok(res);
    }

    //查询请求列表
    @PostMapping(value = "/api/getRequestRecord")
    ApiResponse getOneRequestRecord(@RequestBody SearchRequestRecordRequest request) {

        log.info("/api/getRequestRecord:{}", JSON.toJSONString(request) );
        SearchRequestRecordResponse res = partsRequestService.getOneRequestRecord(request.getRequestRecordId());
        return ApiResponse.ok(res);
    }

}
