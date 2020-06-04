package com.tgr.springbootmybatis.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/pathMatcher")
public class PathMatcherController {

	@GetMapping("/test")
	public String testPathMatcherFilter() {
		return "喔吼吼吼";
	}
	
	
}
