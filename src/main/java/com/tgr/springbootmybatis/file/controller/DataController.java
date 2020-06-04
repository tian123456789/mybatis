package com.tgr.springbootmybatis.file.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/data")
public class DataController {

	@RequestMapping("/index")
	public String index(HttpServletRequest req,Model model) {
		return "data/index";
	}
	
	@RequestMapping("/log")
	public String log(Model model) {
		model.addAttribute("nginxContextPath", "");//  /springboot-mybatis
		model.addAttribute("serverPath", "localhost");
		model.addAttribute("port", 8001);
		model.addAttribute("path", "");
		
		return "data/log";
	}
}
