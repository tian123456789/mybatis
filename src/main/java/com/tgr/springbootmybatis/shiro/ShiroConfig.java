package com.tgr.springbootmybatis.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.SimpleSessionFactory;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

@Configuration
public class ShiroConfig {

	/*1.创建shiro工厂bean*/
	//@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultSecurityManager securityManager) {
		ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
		//关联securitymanager
		bean.setSecurityManager(securityManager);
		Map<String, Filter> filterMap = new HashedMap();
		filterMap.put("authc", new UserFormAuthenticationFilter());
		bean.setFilters(filterMap);
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
		
		
		
		//认证过滤器
		filterChainDefinitionMap.put("/static/**", "anon");
		filterChainDefinitionMap.put("/assets/**", "anon");
		filterChainDefinitionMap.put("/css/**", "anon");
		filterChainDefinitionMap.put("/fonts/**", "anon");
		filterChainDefinitionMap.put("/img/**", "anon");
		filterChainDefinitionMap.put("/js/**", "anon");
		filterChainDefinitionMap.put("/video/**", "anon");
		filterChainDefinitionMap.put("/**", "anon");
		
		filterChainDefinitionMap.put("/login", "authc");
		//授权过滤器
		//shiro标签 <span shiro:hasPermission="product:add">
			//只是隐藏掉点击按钮 还需要加map
		//filterMap.put("/product/toAdd", "perms[product:add]");
		//filterMap.put("/product/toUpdate", "perms[product:update]");
		//filterMap.put("/product/list", "perms[product:list]");
		
		//添加user过滤器
		//filterChainDefinitionMap.put("/index", "user");//index的请求只要使用rememberMe功能,就可以访问了
		
		//filterChainDefinitionMap.put("/**", "anon");
		//filterChainDefinitionMap.put("/**", "authc");//进入FormAuthenticationFilter 出来后验证密码
		
		//添加shiro过滤器
		
		bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		
		//修改登录请求
		bean.setLoginUrl("/toLogin");
		//添加未授权提示页面
		bean.setUnauthorizedUrl("/unAuth");
		
		
		return bean;
	}
	
	/*2.创建SecurityManager*/
	//@Bean
	public DefaultWebSecurityManager defaultWebSecurityManager(MyRealm realm
			,CookieRememberMeManager rememberMeManager) {
		DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
		//关联realm
		manager.setRealm(realm);
		
		//关联rememberMeManager
		//manager.setRememberMeManager(rememberMeManager);
		
		return manager;
	}
	
	/*3.注入Realm*/
	//@Bean
	public MyRealm myRealm() {
		MyRealm realm = new MyRealm();
		return realm;
	}
	
	/*4、整合shiro标签*/
	/*@Bean
	public ShiroDialect shiroDialect() {
		return new ShiroDialect();
	}*/
	
	//创建CookieRememberMeManager
	//@Bean
	public CookieRememberMeManager cookieRememberMeManager(SimpleCookie cookie) {
		CookieRememberMeManager manager = new CookieRememberMeManager();
		manager.setCookie(cookie);
		return manager;
	}
	
	//创建cookie
	//@Bean
	public SimpleCookie simpleCookie() {
		SimpleCookie cookie = new SimpleCookie("rememberMe");
		//设置cookie的时间长度
		cookie.setMaxAge(120);
		//设置只读模型
		cookie.setHttpOnly(true);
		
		return cookie;
	}
	
	/*@Bean
	public DefaultWebSessionManager defaultWebSessionManager() {
		DefaultWebSessionManager mg = new DefaultWebSessionManager();
		mg.setSessionFactory(sessionFactory());
		return mg;
	}
	
	@Bean
	public SimpleSessionFactory sessionFactory() {
		SimpleSessionFactory sessionFactory = new SimpleSessionFactory() {
			
			@Override
			public Session createSession(SessionContext initData) {
				Session session = new Sessio
				return null;
			}
		};
		
		return sessionFactory;
	}*/
	
	
}
