package com.tgr.springbootmybatis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.tgr.springbootmybatis.mapper.AreaMapper;

@RestController
public class ThreadController {

	@Autowired
	private AreaMapper areaMapper;
	
	
}
