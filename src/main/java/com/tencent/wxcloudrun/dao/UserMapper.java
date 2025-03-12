package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

  /**
   * 根据用户 ID 查询用户信息
   * @param id 用户 ID
   * @return 用户对象
   */
  User selectUserById(String id);

  /**
   * 查询所有用户信息
   * @return 用户列表
   */
  List<User> selectAllUsers();

  /**
   * 插入用户信息
   * @param user 用户对象
   * @return 插入成功的记录数
   */
  int insertUser(User user);

  /**
   * 根据用户 ID 更新用户信息
   * @param user 用户对象
   * @return 更新成功的记录数
   */
  int updateUserById(User user);

  /**
   * 根据用户 ID 删除用户信息
   * @param id 用户 ID
   * @return 删除成功的记录数
   */
  int deleteUserById(String id);
}
