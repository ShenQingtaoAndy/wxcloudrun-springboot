package com.tencent.wxcloudrun.service;

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
    public CustomLazyModelFactory(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    private SerializableSupplier<EntityManager> getSupplier() {
        return () -> entityManager;
    }


    public LazyUserDataModel getLazyUserDataModel() {
        LazyUserDataModel lazyUserDataModel = new LazyUserDataModel(getSupplier());
        lazyUserDataModel.setUserRepository(userRepository);
        return lazyUserDataModel;
    }
}
