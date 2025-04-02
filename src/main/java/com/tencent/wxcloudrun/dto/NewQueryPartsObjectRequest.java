package com.tencent.wxcloudrun.dto;

import lombok.Data;

@Data
public class NewQueryPartsObjectRequest {

    private String deviceName;
    private String deviceCategory;
    private String deviceBrand;
    private String devicePattern;
    private String probPattern;
    private String tubePattern;
    private String partsPattern;
    private String type;
    private String partsId;
    private String hospitalName;
    private String requestorId;
    private String remark;
    private String hasIssue;
    private String hasCompetition;
    private String requestType;


}
