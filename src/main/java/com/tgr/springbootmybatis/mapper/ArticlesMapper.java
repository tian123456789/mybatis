package com.tgr.springbootmybatis.mapper;

import com.tgr.springbootmybatis.entity.Articles;

public interface ArticlesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Articles record);

    int insertSelective(Articles record);

    Articles selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Articles record);

    int updateByPrimaryKey(Articles record);
}