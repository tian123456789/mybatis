package com.tgr.springbootmybatis.servlet.support;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

//import com.tgr.web.config.DispatcherServletConfiguration;

/**
 * @author tgr
 * Spring Web Mvc自动装配默认实现 配置类  
 */
@ComponentScan(basePackages= {"com.tgr"})//.spring.web.servlet.controller
//@Configuration  //加不加无所谓
public class DefaultAnnotationConfigDispatcherServletInitializer 
				extends AbstractAnnotationConfigDispatcherServletInitializer{//boot不支持他

	/* (non-Javadoc)
	 * 整体上下文配置
	 * web.xml <init-param><param-name>contextConfigLocation</param-name>
	 * <param-value>/WEB-INF/app-context.xml</param-value>
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		System.err.println("走配之类了");
		return new Class[0];
	}

	/* (non-Javadoc)
	 * DispatcherServlet  新建配置类
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		System.err.println("走配之类了");
		return new Class[] {
				getClass()//返回当前类
		};
	}

	/* (non-Javadoc)
	 * <url-pattern>/</url-pattern>
	 */
	@Override
	protected String[] getServletMappings() {
		System.err.println("走配之类了");
		return new String[] {"/"};
	}

}
