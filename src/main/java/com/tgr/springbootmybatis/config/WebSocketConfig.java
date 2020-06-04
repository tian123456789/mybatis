package com.tgr.springbootmybatis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.tgr.springbootmybatis.file.controller.websocket.MyHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(myHandler(), "/myHandler").addInterceptors(new HttpSessionHandshakeInterceptor())
			.setAllowedOrigins("*").withSockJS();
	}
	
	@Bean
    public WebSocketHandler myHandler() {
        return new MyHandler();
    }

	@Bean
	public ServerEndpointExporter serverEndpointExporter(){
		return new ServerEndpointExporter();
	}

}
