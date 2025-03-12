package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.util.Date;

@Data
public class RequestRecord {


    private String id;
    private String partsObjectId;
    private String hospitalName;
    private String requestorId;
    private String status;
    private String remark;
    private Date updateTime;
    private Date createTime;
}
