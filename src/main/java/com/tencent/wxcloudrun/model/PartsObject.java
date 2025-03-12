package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.util.Date;

@Data
public class PartsObject {

    private String id;
    private String name;
    private String type;
    private Date updateTime;
    private Date createTime;
    private String remark;
}
