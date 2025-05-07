package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.AttachmentFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentFileRepository extends JpaRepository<AttachmentFile, String> {

}
