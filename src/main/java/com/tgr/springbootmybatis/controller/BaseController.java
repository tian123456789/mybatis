package com.tgr.springbootmybatis.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.tgr.springbootmybatis.entity.User;
import com.tgr.springbootmybatis.file.controller.websocket.LogWebSocketService;

@ControllerAdvice
public class BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(LogWebSocketService.class);

	@ModelAttribute("currentUser")
	public User currentUser(HttpServletRequest request) {
		User user = (User)request.getSession().getAttribute("currentUser");
		System.out.println("currentUser name :"+(user == null ? "null" : user.getName()));
		return user;
	}
	
	@ExceptionHandler(value = Exception.class)
	public void handlerException() {
		System.err.println("handlerException handlerException handlerException");
	}
	
}
