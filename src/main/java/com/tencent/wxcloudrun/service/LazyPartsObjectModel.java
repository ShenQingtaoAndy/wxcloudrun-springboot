package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.dao.PartsObjectRepository;
import com.tencent.wxcloudrun.model.PartsObject;
import lombok.Setter;
import org.primefaces.model.JpaLazyDataModel;
import org.primefaces.util.SerializableSupplier;

import javax.persistence.EntityManager;
import java.util.Optional;

public class LazyPartsObjectModel extends JpaLazyDataModel<PartsObject> {

    @Setter
    private PartsObjectRepository partsObjectRepository;

    public LazyPartsObjectModel(SerializableSupplier<EntityManager> entityManager){
        super(PartsObject.class, entityManager);
    }

    @Override
    public String getRowKey(PartsObject partsObject) {
        return partsObject.getId();
    }

    @Override
    public PartsObject getRowData(String rowKey) {
        Optional<PartsObject> UserById = partsObjectRepository.findById(rowKey);
        return UserById.orElse(new PartsObject());
    }
}
