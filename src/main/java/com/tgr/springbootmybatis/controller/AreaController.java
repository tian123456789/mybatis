package com.tgr.springbootmybatis.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tgr.springbootmybatis.entity.Area;
import com.tgr.springbootmybatis.entity.Areao;
import com.tgr.springbootmybatis.mapper.AreaMapper;
import com.tgr.springbootmybatis.mapper.AreaoMapper;

@SuppressWarnings("all")
@RestController
public class AreaController {
	
	@Autowired
	private AreaMapper areaMapper;
	
	@Autowired
	private AreaoMapper originAreaMapper;

	@GetMapping("/exe")
	public void exe() {
		into1();
		into2();
		into3();
	}
	
	public void into1() {//直辖市
		
		List<Areao> originAreaProvinces = originAreaMapper.selectProvinceCodes();
		
		for(int i = 0 ; i < originAreaProvinces.size() ; i++) {
			
			Areao originAreaProvinc = originAreaProvinces.get(i);
			
			if(originAreaProvinc.getProvinceCode().equals("110000") || originAreaProvinc.getProvinceCode().equals("310000") || 
					originAreaProvinc.getProvinceCode().equals("120000") || originAreaProvinc.getProvinceCode().equals("500000")) {
				
				Area provinc = new Area();
				provinc.setCode(originAreaProvinc.getProvinceCode());
				provinc.setLevel(1);
				provinc.setName(originAreaProvinc.getProvinceName());
				provinc.setParentId(null);
				
				Area isArea = areaMapper.selectCodeAndLevel(provinc.getCode(), provinc.getLevel());
				if(isArea == null) {
					areaMapper.insertSelective2(provinc);
				}
				
				
				Areao originAreaCity = originAreaMapper.selectCityCodes(provinc.getCode()).get(0);
				
				Area city = new Area();
				city.setCode(originAreaCity.getCityCode());
				city.setName(originAreaCity.getCityName()+"城区");
				city.setLevel(2);
				city.setParentId(provinc.getId());
				
				Area isArea2 = areaMapper.selectCodeAndLevel(city.getCode(), city.getLevel());
				if(isArea2 == null) {
					areaMapper.insertSelective2(city);
				}
				

				List<Areao> originAreaCountys = originAreaMapper.selectCountyCodesByProviceAndCity(city.getCode(),provinc.getCode());//所有区县
				for (int j = 0; j < originAreaCountys.size(); j++) {
					
					Areao originAreaCounty = originAreaCountys.get(j);
					
					Area county = new Area();
					county.setCode(originAreaCounty.getCountyCode());
					county.setLevel(3);
					county.setName(originAreaCounty.getCountyName());
					county.setParentId(city.getId());
					
					Area isArea3 = areaMapper.selectCodeAndLevel(county.getCode(), county.getLevel());
					if(isArea3 == null) {
						areaMapper.insertSelective2(county);
					}
					
					
					List<Areao> originAreaStresss = originAreaMapper.selectStreetCodesByCityAndCounty(county.getCode(),city.getCode());//所有街道
					for(int k = 0 ; k < originAreaStresss.size() ; k++) {
						Areao originAreaStree = originAreaStresss.get(k);
						
						Area stree = new Area();
						stree.setCode(originAreaStree.getStreetCode());
						stree.setLevel(4);
						stree.setName(originAreaStree.getStreetName());
						stree.setParentId(county.getId());
						
						Area isArea4 = areaMapper.selectCodeAndLevel(stree.getCode(), stree.getLevel());
						if(isArea4 == null) {
							areaMapper.insertSelective2(stree);
						}
						
					}
				}
				
			}
			
		}
	}
	
	public void into2() {//内部省份
		
		List<Areao> originAreaProvinces = originAreaMapper.selectProvinceCodes();
		
		for(int i = 0 ; i < originAreaProvinces.size() ; i++) {
			
			Areao originAreaProvinc = originAreaProvinces.get(i);
			
			if(!originAreaProvinc.getProvinceCode().equals("110000") && !originAreaProvinc.getProvinceCode().equals("310000") && 
					!originAreaProvinc.getProvinceCode().equals("120000") && !originAreaProvinc.getProvinceCode().equals("500000") &&
					!originAreaProvinc.getProvinceCode().equals("810000") && !originAreaProvinc.getProvinceCode().equals("820000")) {
				
				Area provinc = new Area();
				provinc.setCode(originAreaProvinc.getProvinceCode());
				provinc.setLevel(1);
				provinc.setName(originAreaProvinc.getProvinceName());
				provinc.setParentId(null);
				
				Area isArea = areaMapper.selectCodeAndLevel(provinc.getCode(), provinc.getLevel());
				if(isArea == null) {
					areaMapper.insertSelective2(provinc);
				}
				
				
				List<Areao> originAreaCitys = originAreaMapper.selectCityCodes(provinc.getCode());
				for(int i2 = 0 ; i2 < originAreaCitys.size() ; i2++) {
					Areao originAreaCity = originAreaCitys.get(i2);
					Area city = new Area();
					city.setCode(originAreaCity.getCityCode());
					city.setName(originAreaCity.getCityName());
					city.setLevel(2);
					city.setParentId(provinc.getId());
					
					Area isArea2 = areaMapper.selectCodeAndLevel(city.getCode(), city.getLevel());
					if(isArea2 == null) {
						areaMapper.insertSelective2(city);
					}
					

					List<Areao> originAreaCountys = originAreaMapper.selectCountyCodesByProviceAndCity(city.getCode(),provinc.getCode());//所有区县
					for (int j = 0; j < originAreaCountys.size(); j++) {
						
						Areao originAreaCounty = originAreaCountys.get(j);
						
						Area county = new Area();
						county.setCode(originAreaCounty.getCountyCode());
						county.setLevel(3);
						county.setName(originAreaCounty.getCountyName());
						county.setParentId(city.getId());
						
						Area isArea3 = areaMapper.selectCodeAndLevel(county.getCode(), county.getLevel());
						if(isArea3 == null) {
							areaMapper.insertSelective2(county);
						}
						
						
						List<Areao> originAreaStresss = originAreaMapper.selectStreetCodesByCityAndCounty(county.getCode(),city.getCode());//所有街道
						for(int k = 0 ; k < originAreaStresss.size() ; k++) {
							Areao originAreaStree = originAreaStresss.get(k);
							
							Area stree = new Area();
							stree.setCode(originAreaStree.getStreetCode());
							stree.setLevel(4);
							stree.setName(originAreaStree.getStreetName());
							stree.setParentId(county.getId());
							
							Area isArea4 = areaMapper.selectCodeAndLevel(stree.getCode(), stree.getLevel());
							if(isArea4 == null) {
								areaMapper.insertSelective2(stree);
							}
							
						}
					}
				}
				
			}
			
		}
	}
	
	public void into3() {//特别行政区
		
		List<Areao> originAreaProvinces = originAreaMapper.selectProvinceCodes();
		
		for(int i = 0 ; i < originAreaProvinces.size() ; i++) {
			
			Areao originAreaProvinc = originAreaProvinces.get(i);
			
			if(originAreaProvinc.getProvinceCode().equals("810000") || originAreaProvinc.getProvinceCode().equals("820000")) {
				
				Area provinc = new Area();
				provinc.setCode(originAreaProvinc.getProvinceCode());
				provinc.setLevel(1);
				provinc.setName(originAreaProvinc.getProvinceName());
				provinc.setParentId(null);
				
				Area isArea = areaMapper.selectCodeAndLevel(provinc.getCode(), provinc.getLevel());
				if(isArea == null) {
					areaMapper.insertSelective2(provinc);
				}
				
				
				Areao originAreaCity = originAreaMapper.selectCityCodes(provinc.getCode()).get(0);
				
				Area city = new Area();
				city.setCode(originAreaCity.getCityCode());
				city.setName(originAreaCity.getCityName()+"城区");
				city.setLevel(2);
				city.setParentId(provinc.getId());
				
				Area isArea2 = areaMapper.selectCodeAndLevel(city.getCode(), city.getLevel());
				if(isArea2 == null) {
					areaMapper.insertSelective2(city);
				}
				

				List<Areao> originAreaCountys = originAreaMapper.selectCountyCodesByProviceAndCity(city.getCode(),provinc.getCode());//所有区县
				for (int j = 0; j < originAreaCountys.size(); j++) {
					
					Areao originAreaCounty = originAreaCountys.get(j);
					
					Area county = new Area();
					county.setCode(originAreaCounty.getCountyCode());
					county.setLevel(3);
					county.setName(originAreaCounty.getCountyName());
					county.setParentId(city.getId());
					
					Area isArea3 = areaMapper.selectCodeAndLevel(county.getCode(), county.getLevel());
					if(isArea3 == null) {
						areaMapper.insertSelective2(county);
					}
					
					
					List<Areao> originAreaStresss = originAreaMapper.selectStreetCodesByCityAndCounty(county.getCode(),city.getCode());//所有街道
					for(int k = 0 ; k < originAreaStresss.size() ; k++) {
						Areao originAreaStree = originAreaStresss.get(k);
						
						Area stree = new Area();
						stree.setCode(originAreaStree.getStreetCode());
						stree.setLevel(4);
						stree.setName(originAreaStree.getStreetName());
						stree.setParentId(county.getId());
						
						Area isArea4 = areaMapper.selectCodeAndLevel(stree.getCode(), stree.getLevel());
						if(isArea4 == null) {
							areaMapper.insertSelective2(stree);
						}
						
					}
				}
				
			}
			
		}
	}
	
	public static void main(String[] args) {
		
		Area area = new Area();
		
		Optional<Integer> level = Optional.ofNullable(area.getLevel());
		System.err.println(level.isPresent());
	}
	
	
	
	//直辖市 北京: 110000       上海: 310000      天津市: 120000   重庆市: 500000
	//特别行政区 香港:    澳门: 
}
