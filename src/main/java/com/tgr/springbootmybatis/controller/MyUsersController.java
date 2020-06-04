package com.tgr.springbootmybatis.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tgr.springbootmybatis.entity.Users;
import com.tgr.springbootmybatis.service.MyUsersService;


@RestController
public class MyUsersController {

	@Resource
	private MyUsersService myUserService;
	
	@RequestMapping("/users/{id}")
	public Users findById(@PathVariable("id") Integer id) {
		return myUserService.findById(id);
	}
}
