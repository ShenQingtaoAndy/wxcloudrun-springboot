package com.tencent.wxcloudrun.model;


import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class FileMeta {

    private String id ;
    private String fileName ;
    private String fileType ;
    private Long fileSize ;
    private Date fileUploadDate ;
    private String fileMd5 ;

}
