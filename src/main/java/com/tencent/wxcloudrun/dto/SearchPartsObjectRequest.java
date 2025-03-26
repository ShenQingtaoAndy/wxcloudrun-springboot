package com.tencent.wxcloudrun.dto;

import lombok.Data;

@Data
public class SearchPartsObjectRequest {

    private Integer pageSize =10;
    private Integer pageNumber =0;
    private String searchStr;
    private String deviceName;
    private String deviceCategory;
    private String deviceBrand;
    private String deviceType;
    private String devicePattern;
}
