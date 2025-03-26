package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.dao.RequestRecordRepository;
import com.tencent.wxcloudrun.model.RequestRecord;
import lombok.Setter;
import org.primefaces.model.JpaLazyDataModel;
import org.primefaces.util.SerializableSupplier;

import javax.persistence.EntityManager;
import java.util.Optional;

public class LazyRequestRecordModel extends JpaLazyDataModel<RequestRecord> {

    @Setter
    private RequestRecordRepository requestRecordRepository;

    public LazyRequestRecordModel(SerializableSupplier<EntityManager> entityManager){
        super(RequestRecord.class, entityManager);
    }

    @Override
    public String getRowKey(RequestRecord requestRecord) {
        return requestRecord.getId();
    }

    @Override
    public RequestRecord getRowData(String rowKey) {
        Optional<RequestRecord> UserById = requestRecordRepository.findById(rowKey);
        return UserById.orElse(new RequestRecord());
    }
}
