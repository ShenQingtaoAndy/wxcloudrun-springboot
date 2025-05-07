package com.tencent.wxcloudrun.service;


import com.alibaba.fastjson.JSON;
import com.tencent.wxcloudrun.dao.AttachmentFileRepository;
import com.tencent.wxcloudrun.dao.RecordAttachmentRepository;
import com.tencent.wxcloudrun.model.AttachmentFile;
import com.tencent.wxcloudrun.model.FileMeta;
import com.tencent.wxcloudrun.model.RecordAttachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class AttachmentService {

    @Autowired
    private AttachmentFileRepository attachmentFileRepository;

    @Autowired
    private RecordAttachmentRepository recordAttachmentRepository;

    public  String saveAttachment(MultipartFile file, String recordId) throws IOException {

        FileMeta fileInfo = FileMeta.builder().fileName(file.getOriginalFilename())
                .fileType(file.getContentType())
                .fileUploadDate(new Date()).build();

        AttachmentFile attachmentFile = new AttachmentFile();
        attachmentFile.setFile(file.getBytes());
        attachmentFile.setMetaData(JSON.toJSONString(fileInfo));

        attachmentFile = attachmentFileRepository.save(attachmentFile);

        RecordAttachment recordAttachment = new RecordAttachment();
        recordAttachment.setAttachmentId(attachmentFile.getId());
        recordAttachment.setFileName(fileInfo.getFileName());
        recordAttachment.setRequestId(recordId);
        recordAttachment.setFileSize(file.getSize());
        recordAttachment.setCreateTime(new Date());
        recordAttachment.setUpdateTime(recordAttachment.getCreateTime());
        recordAttachmentRepository.save(recordAttachment);

        return attachmentFile.getId();
    }

    @Transactional
    public void deleteAttachment(String attachmentId) {
        attachmentFileRepository.deleteById(attachmentId);
        recordAttachmentRepository.deleteAllByAttachmentId(attachmentId);
    }

    public Object downloadAttachment(String attachmentId) {

        AttachmentFile attachmentFile = attachmentFileRepository.findById(attachmentId).get();
        return attachmentFile.getFile();
    }

    public List<RecordAttachment> getAttachmentsByRecord(String recordId) {
        List<RecordAttachment> res = recordAttachmentRepository.findByRequestIdOrderByCreateTimeDesc(recordId);
        return res;
    }
}
