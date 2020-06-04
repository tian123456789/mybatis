package com.tgr.springbootmybatis.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tgr.springbootmybatis.entity.Dict_C1;
import com.tgr.springbootmybatis.mapper.Dict_C1Mapper;
import com.tgr.springbootmybatis.mapper.Dict_C2Mapper;

@Service
@Transactional
public class Dict_C1Service {
	
	@Resource
	private Dict_C1Mapper dict_C1Mapper;

	public void insert(Dict_C1 tmp) {
		/*dict_C1Mapper.insert(tmp);*/
	}

	public Dict_C1 findByName(String name) {
		/*return dict_C1Mapper.findByName(name);*/
		return new Dict_C1();
	}

	public void update(Dict_C1 c1) {
		/*dict_C1Mapper.update(c1);*/
	}

	public List<Dict_C1> findAll() {
		return null;
		//return dict_C1Mapper.findAll();
	}

	public Dict_C1 findById(Integer c1id) {
		return new Dict_C1();
	}

	public void delete(Integer c1id) {
		return;
	}

}
