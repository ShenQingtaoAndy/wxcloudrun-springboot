package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.dao.UserRepository;
import com.tencent.wxcloudrun.model.User;
import lombok.Setter;
import org.primefaces.model.JpaLazyDataModel;
import org.primefaces.util.SerializableSupplier;

import javax.persistence.EntityManager;
import java.util.Optional;

public class LazyUserDataModel extends JpaLazyDataModel<User> {

    @Setter
    private UserRepository userRepository;

    public  LazyUserDataModel( SerializableSupplier<EntityManager> entityManager){
        super(User.class, entityManager);
    }

    @Override
    public String getRowKey(User user) {
        return user.getId();
    }

    @Override
    public User getRowData(String rowKey) {
        Optional<User> UserById = userRepository.findById(rowKey);
        return UserById.orElse(new User());
    }
}
