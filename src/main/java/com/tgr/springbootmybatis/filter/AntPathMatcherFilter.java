package com.tgr.springbootmybatis.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

import Zorg.springframework.util.AntPathMatcher;
import Zorg.springframework.util.PathMatcher;

@WebFilter(filterName = "antPathMatcherFilter",urlPatterns = "/pathMatcher/*",asyncSupported = true,
			initParams = {
					@WebInitParam(name = "preParam",value = "true")
					}
)
public class AntPathMatcherFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		PathMatcher pathMatcher = new AntPathMatcher();
		String url = request.getRemoteAddr();
		System.err.println("远程地址是: "+url);
		chain.doFilter(request, response);
		
	}

	@Override
	public void destroy() {
		
	}

}
