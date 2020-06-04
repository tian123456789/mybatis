package com.tgr.springbootmybatis.shiro;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.util.StringUtils;

import com.tgr.springbootmybatis.entity.User;
import com.tgr.springbootmybatis.service.UserService;

public class MyRealm extends AuthorizingRealm{

	@Resource
	private UserService userService;
	
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
		//1、获取用户输入的账户信息
		UsernamePasswordToken token = (UsernamePasswordToken) arg0;
		
		//查询数据库
		User dbUser = userService.findByName(token.getUsername());
		if(dbUser==null) {
			//用户不存在
			return null;
		}
		
		
		//返回密码
		return new SimpleAuthenticationInfo(dbUser, dbUser.getPassword(), "");
	}

	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {//授权
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		
		//得到当前用户
		Subject subject = SecurityUtils.getSubject();
		User dbUser = (User) subject.getPrincipal();
		
		List<String> perms = userService.findPermissionByUserId(dbUser.getUserid());
		if(perms!=null) {
			for(String perm : perms) {
				if(!StringUtils.isEmpty(perm)) {
					info.addStringPermission(perm);
				}
			}
		}
		return info;
	}


}
