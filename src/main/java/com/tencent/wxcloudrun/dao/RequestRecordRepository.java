package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.RequestRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RequestRecordRepository extends JpaRepository<RequestRecord, String> {

    @Query("SELECT u FROM RequestRecord u WHERE (:partsId IS NULL OR u.partsId=:partsId) " +
            "AND (:requestorId IS NULL OR u.requestorId = :requestorId) " )
    Page<RequestRecord> SearchQueryPage(String partsId , String requestorId, Pageable pageable);

}
