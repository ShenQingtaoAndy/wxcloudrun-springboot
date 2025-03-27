package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.PartsObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PartsObjectRepository extends JpaRepository<PartsObject, String> {


    @Query("SELECT u FROM PartsObject u WHERE (:deviceName IS NULL OR u.deviceName LIKE %:deviceName%) " +
            "AND (:deviceCategory IS NULL OR u.deviceCategory = :deviceCategory) " +
            "AND (:deviceBrand IS NULL OR u.deviceBrand LIKE %:deviceBrand% ) " +
            "AND (:devicePattern IS NULL OR u.devicePattern LIKE %:devicePattern% ) " +
            "AND (:searchStr IS NULL OR u.index_str LIKE %:searchStr% ) " +
            "AND (:type IS NULL OR u.type = :type) ")
    Page<PartsObject> searchPartsObject(String type , String deviceName, String deviceCategory, String deviceBrand,String devicePattern,String searchStr, Pageable pageable);

}
