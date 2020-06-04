package com.tgr.springbootmybatis.config;

import org.apache.coyote.http11.Http11Nio2Protocol;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatConfig {

	@Bean
	public ConfigurableServletWebServerFactory servletWebServerFactory(){
		System.err.println("执行tomcat配置");
		TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
		factory.setPort(8002);
		factory.addConnectorCustomizers(
				connector->{
					Http11NioProtocol protocol = 
							(Http11NioProtocol) connector.getProtocolHandler();
					System.err.println("===================");
					protocol.setDisableUploadTimeout(false);
				}
				);
		return factory;
	}
}
