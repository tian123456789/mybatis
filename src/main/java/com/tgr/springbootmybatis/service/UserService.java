package com.tgr.springbootmybatis.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tgr.springbootmybatis.entity.User;

import com.tgr.springbootmybatis.mapper.UserMapper;

@Service
public class UserService {

	@Resource
	private UserMapper userMapper;
	
	public User findByName(String name) {
		return userMapper.findByName(name);
	}
	
	public List<String> findPermissionByUserId(Integer userId){
		return userMapper.findPermissionByUserId(userId);
	}
	
	public User selectUserById(Integer userId) {
		return null;//usersMapper.selectUserById(userId);
	}
	
	public int insertUser(User user) {
		return 0;//usersMapper.insertUser(user);
	}
	
	public int updateUser(User user) {
		return 0;//usersMapper.updateUser(user);
	}
	
	public int deleteUser(Integer userId) {
		return 0;//usersMapper.deleteUser(userId);
	}
	
	public List<User> selectUserByName(String value){
		return null;//usersMapper.selectUserByName(value);
	}
}
