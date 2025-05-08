package com.tencent.wxcloudrun.controller;


import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.config.CustomUserDetailsService;
import com.tencent.wxcloudrun.service.AttachmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Slf4j
@RestController
public class AttachmentsController {


    @Autowired
    AttachmentService attachmentService;

    //上传附件 返回附件uid
    @PostMapping(value = "/api/uploadAttachment")
    ApiResponse uploadAttachment(@RequestParam(value = "file") MultipartFile file,
                                 @RequestParam(value = "recordId", required = false) String recordId) throws IOException {

        if(file.isEmpty()){
            return ApiResponse.ok("");
        }

        return ApiResponse.ok(attachmentService.saveAttachment(file, recordId));

    }

    //删除附件
    @PostMapping(value = "/api/deleteAttachment/{attachmentId}")
    ApiResponse deleteAttachment(@PathVariable String attachmentId) throws IOException {
        attachmentService.deleteAttachment(attachmentId);
        return ApiResponse.ok(true);

    }

    //下载附件
    @PostMapping(value = "/api/downloadAttachment/{attachmentId}")
    ApiResponse downloadAttachment(@PathVariable String attachmentId) throws IOException {
        return ApiResponse.ok(attachmentService.downloadAttachment(attachmentId));

    }

    //查询附件列表
    @PostMapping(value = "/api/getAttachments/{recordId}")
    ApiResponse getAttachments(@PathVariable String recordId) throws IOException {
        return ApiResponse.ok(attachmentService.getAttachmentsByRecord(recordId));

    }


}
