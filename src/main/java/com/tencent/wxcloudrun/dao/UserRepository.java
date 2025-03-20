package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    User findByOpenid(String openId) ;
}
