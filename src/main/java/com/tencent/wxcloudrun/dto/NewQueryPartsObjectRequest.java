package com.tencent.wxcloudrun.dto;

import lombok.Data;

@Data
public class NewQueryPartsObjectRequest {

    private String deviceName;
    private String deviceCategory;
    private String deviceBrand;
    private String devicePattern;
    private String type;
    private String hospitalName;
    private String requestorId;
}
