package com.tencent.wxcloudrun.dto;

import com.tencent.wxcloudrun.model.RequestRecord;
import lombok.Data;

import java.util.List;

@Data
public class SearchRequestRecordResponse {

    private RequestRecord requestRecord;

    List<RequestRecord> records;

}
