package com.tgr.springbootmybatis.mapper;

import java.util.List;

import com.tgr.springbootmybatis.entity.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer userid);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userid);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    User findByName(String name);
    
    List<String> findPermissionByUserId(Integer userId);
}