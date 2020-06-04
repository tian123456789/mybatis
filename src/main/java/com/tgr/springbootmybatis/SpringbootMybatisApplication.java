package com.tgr.springbootmybatis;


import javax.servlet.MultipartConfigElement;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

import com.tgr.springbootmybatis.file.controller.websocket.LogWebSocketService;


@SpringBootApplication
@MapperScan("com.tgr.springbootmybatis.mapper")
@EnableConfigurationProperties
@ServletComponentScan("com.tgr")
@ComponentScan("com.tgr")
@EnableAsync
public class SpringbootMybatisApplication  extends SpringBootServletInitializer {
	
	private static final Logger logger = LoggerFactory.getLogger(LogWebSocketService.class);

	public static void main(String[] args) {
		logger.debug("項目開始啓動");
		SpringApplication.run(SpringbootMybatisApplication.class, args);
	}
	
	@Bean
	public <V> RedisService<V> redisService(RedisConnectionFactory factory) {
		RedisService<V> redis = new RedisService<V>(factory);
		redis.afterPropertiesSet();
		return redis;
	}
	
	
	@Autowired
	private RestTemplateBuilder builder;
	
	@Bean
	public RestTemplate restTemplate() {
		return builder.build();
	}
	
	/*@Bean
	public MongoClient mongoClient() {
		return new MongoClient("localhost",27017);
	}
	
	@Bean
	public MongoTemplate mongoTemplate(MongoTemplate mongoTemplate) {
		return new MongoTemplate(mongoClient(), "tgr");
	}
*/
	/**
	 * 配置上传文件大小的配置
	 * @return
	 */
	@Bean
	public MultipartConfigElement multipartConfigElement() {//SpringBootServletInitializer
		MultipartConfigFactory factory = new MultipartConfigFactory();
		//  单个数据大小
		factory.setMaxFileSize("3MB");
		/// 总上传数据大小
		factory.setMaxRequestSize("20MB");
		return factory.createMultipartConfig();
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
		return builder.sources(SpringbootMybatisApplication.class);
	}
	
	//2种
		//代替//@ServletComponentScan(basePackages= {"com.tgr.spring.web.servlet"})   http://localhost:8081/asyncServlet
		/*@Bean
		@Order(Ordered.HIGHEST_PRECEDENCE)*///加上spring-servlet项目的  AsyncServlet bean
											//现在有两个
												//o.s.b.w.servlet.ServletRegistrationBean  : Servlet dispatcherServlet mapped to [/]
												//o.s.b.w.servlet.ServletRegistrationBean  : Servlet asyncServlet mapped to [/]
											//会404
											//所以调整自己的到最高优先级  但是还是未改变  需要换成RegisterBean的方式解决
		/*public AsyncServlet asyncServlet() {
			return new AsyncServlet();//映射到根路径   http://localhost:8081/
		}*/
		
		
		
		//3种
		//解决asyncServlet()方式的问题
		//http://localhost:8081/asyncServlet成功
		/*@Bean
		@Order(Ordered.HIGHEST_PRECEDENCE)*/
		/*public ServletRegistrationBean<AsyncServlet> asyncServletServletRegistrationBean() {*/
			//ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new AsyncServlet(), "/asyncServlet");
			//也可以替代DispatcherServlet 的/路径  断点就不会进入DispatcherServlet.class的service()和doDispatcher()方法 
				//而是进入spring-servlet的service()方法
		/*	ServletRegistrationBean<AsyncServlet> servletRegistrationBean = new ServletRegistrationBean<AsyncServlet>(new AsyncServlet(), "/");
			servletRegistrationBean.setName("MyAsyncServlet");
			return servletRegistrationBean;
		}*/
		
		//1种
		/*@Bean
		public ServletContextInitializer servletContextInitializer() {
			return servletContext -> {
				
				AsyncServlet asyncServlet = new AsyncServlet();
				ServletRegistration.Dynamic asyncServletRegistration = servletContext.addServlet("asyncServlet", asyncServlet);
				asyncServletRegistration.addMapping("/asyncServlet");
				asyncServletRegistration.setAsyncSupported(true);
				
				CharacterEncodingFilter filter = new CharacterEncodingFilter();
				FilterRegistration.Dynamic registration = servletContext.addFilter("filter", filter);
				registration.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false, "/");
				
			};
		}*/
		
		/*@Bean
		public ServletContextInitializer servletContextInitializer() {
			return servletContext -> {
				CharacterEncodingFilter filter = new CharacterEncodingFilter();//字符编码
				FilterRegistration.Dynamic registration = servletContext.addFilter("filter", filter);
				registration.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false, "/");
			};
		}*/

}
