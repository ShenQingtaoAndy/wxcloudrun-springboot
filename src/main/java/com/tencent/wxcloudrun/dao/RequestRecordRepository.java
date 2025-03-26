package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.RequestRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRecordRepository extends JpaRepository<RequestRecord, String> {

}
