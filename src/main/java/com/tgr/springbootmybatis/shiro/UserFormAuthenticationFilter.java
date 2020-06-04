package com.tgr.springbootmybatis.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import com.tgr.springbootmybatis.entity.User;

public class UserFormAuthenticationFilter extends FormAuthenticationFilter{

	@Override
    protected boolean isAccessAllowed(ServletRequest request, 
    		ServletResponse response, Object mappedValue) {//被允许的请求
       
		System.err.println("进入表单认证过滤器");
		
		Subject subject = getSubject(request, response);
		
		//如果isAuthenticated为false证明不是登录过的,同时isRemember为true
			//证明是没有登陆过直接通过记住我功能进来的
		if(!subject.isAuthenticated() && subject.isRemembered()) {//&& subject.isRemembered()
			//获取session看看是不是空的
			Session session = subject.getSession(true);
			
			//查看session属性是否为空
			if(session.getAttribute("currentUser") == null) {
				//如果是空才初始化
				User dbUser = (User)subject.getPrincipal();
				//存入用户数据
				if(dbUser != null) {
					session.setAttribute("currentUser", dbUser.getName());
				}
			}
		}
		if(subject.isAuthenticated() || subject.isRemembered()) {//如果被认证通过 或者记住我 设置session(currentUser,)
			//获取session看看是不是空的
			Session session = subject.getSession(true);
			
			//查看session属性是否为空
			if(session.getAttribute("currentUser") == null) {
				//如果是空才初始化
				User dbUser = (User)subject.getPrincipal();
				//存入用户数据
				if(dbUser != null) {
					session.setAttribute("currentUser", dbUser);
				}
			}
		}
		
		//这个方法本来只返回subject.isAuthenticated()现在我们加上subject.isRemember()
			//让他同时叶剑荣remember这种情况
		if(isLoginSubmission(request, response)) {/*isLoginRequest(request, response)*/
			return true;
		}
		return subject.isAuthenticated() || subject.isRemembered();
    }
	
}
