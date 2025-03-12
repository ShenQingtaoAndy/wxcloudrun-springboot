package com.tencent.wxcloudrun.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String id;
    private String name;
    private String sso;
    private String phone;
    private String email;
    private String openid;
    private String roles;
    private Date createTime;
    private Date updateTime;
    private String status;

}
