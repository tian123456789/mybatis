package com.tgr.springbootmybatis.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tgr.springbootmybatis.entity.User;
//import com.tgr.springbootmybatis.entity.User;
import com.tgr.springbootmybatis.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	
	/*@Resource
	private UserService userSerive;
	
	@RequestMapping("/user/{userId}")
	public User selectUserById(@PathVariable("userId") Integer userId) {
		return userSerive.selectUserById(userId);
	}
	@RequestMapping("/user/insertUser")
	public int insertUser() {
		
		User user = new User();
		user.setAddress("111111");
		user.setAge(18);
		user.setMobile("13087838983");
		user.setName("新加入1");
		
		int count = userSerive.insertUser(user);
		int lastId = user.getUserId();
		System.out.println("最后插入的userId::"+lastId);
		return count;
	}
	
	@RequestMapping("/user/updateUser")
	public int updateUser() {
		User user = new User();
		user.setAddress("111111");
		user.setAge(18);
		user.setMobile("13087838983");
		user.setName("新加入1被更新了");
		user.setUserId(1011);
		return userSerive.updateUser(user);
	}
	
	@RequestMapping("/user/deleteUser/{userId}")
	public int deleteUser(@PathVariable("userId") Integer userId) {
		return userSerive.deleteUser(userId);
	}
	
	@RequestMapping("/user/selectUserByName")
	public List<User> selectUserLikeName(@RequestParam(value="name",required = false) String name){
		
		return userSerive.selectUserByName(name);
	}
	
	@RequestMapping("/user/selectUserByName2/{name}")
	public List<User> selectUserLikeName2(@PathVariable("name") String name){
		
		return userSerive.selectUserByName(name);
	}
	
	*/
	public static void main(String[] args) {
		Md5Hash hash = new Md5Hash("root", "root", 2);
		//973ad2226a4cdd904364d843476b9165
		//c6ac59a147fa349f1032c521b7e521a3
		System.out.println(hash.toString());
	}
	
}
