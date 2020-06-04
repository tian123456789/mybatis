package com.tgr.springbootmybatis.mapper;

import com.tgr.springbootmybatis.entity.Episode;

public interface EpisodeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Episode record);

    int insertSelective(Episode record);

    Episode selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Episode record);

    int updateByPrimaryKey(Episode record);
}