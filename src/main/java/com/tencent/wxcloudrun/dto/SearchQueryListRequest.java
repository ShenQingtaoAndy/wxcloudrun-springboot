package com.tencent.wxcloudrun.dto;

import lombok.Data;

@Data
public class SearchQueryListRequest {

    private Integer pageSize =10;
    private Integer pageNumber =0;
    private String partsId;
    private String requestorId;
}
