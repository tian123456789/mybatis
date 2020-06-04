package com.tgr.springbootmybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tgr.springbootmybatis.entity.Area;
import com.tgr.springbootmybatis.entity.Areao;

public interface AreaoMapper {
    int insert(Areao record);

    int insertSelective(Areao record);
    
    List<Areao> selectProvinceCodes();//北京市
    
    List<Areao> selectCityCodes(@Param("provinceCode")String provinceCode);//北京城区
    
    List<Areao> selectCountyCodesByProviceAndCity(@Param("cityCode") String cityCode,@Param("proviceCode") String proviceCode);//昌平区
    
    List<Areao> selectStreetCodesByCityAndCounty(@Param("countyCode") String countyCode , @Param("cityCode") String cityCode);//东小口镇
    
    Areao selectAreaoById(@Param("id") int id);
    
}