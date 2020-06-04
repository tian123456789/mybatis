package com.tgr.springbootmybatis.mapper;

import org.apache.ibatis.annotations.Param;

import com.tgr.springbootmybatis.entity.Area;

public interface AreaMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Area record);

    int insertSelective(Area record);
    
    int insertSelective2(Area record);

    Area selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Area record);

    int updateByPrimaryKey(Area record);
    
    Area selectCodeAndLevel(@Param("code")String code , @Param("level")int level);
}