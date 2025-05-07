package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.RecordAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordAttachmentRepository extends JpaRepository<RecordAttachment, String> {

    void deleteAllByAttachmentId(String attachmentId);

    List<RecordAttachment> findByRequestIdOrderByCreateTimeDesc(String requestId);
}
