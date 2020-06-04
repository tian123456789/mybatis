package com.tgr.springbootmybatis.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tgr.springbootmybatis.entity.Users;

/*import com.tgr.springbootmybatis.entity.Users;
import com.tgr.springbootmybatis.mapper.MyUsersMapper;
*/

@Service
public class MyUsersService {

	/*@Resource
	private MyUsersMapper usersMapper;*/
	
	public Users findById(Integer id) {
		return null;// usersMapper.findById(id);
	}
}
