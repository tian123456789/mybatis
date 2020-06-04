package com.tgr.springbootmybatis.mapper;

import java.util.List;

import com.tgr.springbootmybatis.entity.Video;
import com.tgr.springbootmybatis.pojo.VideoVo;

public interface VideoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Video record);

    int insertSelective(Video record);

    Video selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Video record);

    int updateByPrimaryKey(Video record);

	List<VideoVo> findAll();
}