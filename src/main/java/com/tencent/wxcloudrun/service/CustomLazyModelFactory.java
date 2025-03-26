package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.dao.PartsObjectRepository;
import com.tencent.wxcloudrun.dao.RequestRecordRepository;
import com.tencent.wxcloudrun.dao.UserRepository;
import org.primefaces.util.SerializableSupplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
public class CustomLazyModelFactory {

    private final EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private PartsObjectRepository partsObjectRepository;

    @Autowired
    private RequestRecordRepository requestRecordRepository;

    @Autowired
    public CustomLazyModelFactory(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    private SerializableSupplier<EntityManager> getSupplier() {
        return () -> entityManager;
    }


    public LazyUserDataModel getLazyUserDataModel() {
        LazyUserDataModel lazyModel = new LazyUserDataModel(getSupplier());
        lazyModel.setUserRepository(userRepository);
        return lazyModel;
    }

    public LazyPartsObjectModel getLazyPartsObjectModel() {
        LazyPartsObjectModel lazyModel = new LazyPartsObjectModel(getSupplier());
        lazyModel.setPartsObjectRepository(partsObjectRepository);
        return lazyModel;
    }


    public LazyRequestRecordModel getLazyRequestRecordModel() {
        LazyRequestRecordModel lazyModel = new LazyRequestRecordModel(getSupplier());
        lazyModel.setRequestRecordRepository(requestRecordRepository);
        return lazyModel;
    }
}
