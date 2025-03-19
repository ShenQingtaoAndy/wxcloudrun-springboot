package com.tencent.wxcloudrun.dto;

import lombok.Data;

@Data
public class CreateUserRequest {

  private String openid;
  private String sso;
  private String name;

}
