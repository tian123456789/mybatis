package com.tgr.springbootmybatis.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(asyncSupported = true,//激活异步支持 并不是全局的 而是单个的
			name = "asyncServlet",
			urlPatterns = "/asyncServlet")
public class AsyncServlet extends HttpServlet{//扩展HttpServlet 也可以实现servlet接口 但是比较麻烦
	
	private static final long serialVersionUID = 1L;

	//覆盖service方法
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		//判断是否支持异步
		if(req.isAsyncSupported()) {
			//创建异步上下文 开启异步
			AsyncContext asyncContext = req.startAsync();
			//设置超时时间
			asyncContext.setTimeout(50L);
			//增加监听器
			asyncContext.addListener(new AsyncListener() {
				
				@Override
				public void onTimeout(AsyncEvent paramAsyncEvent) throws IOException {
				
					//PrintWriter writer = servletResponse.getWriter();
					//如果不输出 超时会出现 500 但是还没走503
					HttpServletResponse response = (HttpServletResponse) paramAsyncEvent.getSuppliedResponse();
					response.setStatus(response.SC_SERVICE_UNAVAILABLE);//503内部服务不可用
					System.out.println(Thread.currentThread().getName()+"超时了!!!");
				}
				
				@Override
				public void onStartAsync(AsyncEvent paramAsyncEvent) throws IOException {
					System.out.println(Thread.currentThread().getName()+"正在执行");
				}
				
				@Override
				public void onError(AsyncEvent paramAsyncEvent) throws IOException {
					System.out.println(Thread.currentThread().getName()+"执行错误");
				}
				
				@Override
				public void onComplete(AsyncEvent paramAsyncEvent) throws IOException {
					System.out.println(Thread.currentThread().getName()+"执行完成");
				}
			});
			
			//试试增加过滤器
			//ServletContext context = req.getServletContext();
			//context.addFilter(filterName, filter)
			System.out.println(Thread.currentThread().getName()+"hello hello hello");
			
			ServletResponse servletResponse = asyncContext.getResponse();
			//设置响应媒体类型
			servletResponse.setContentType("text/plain;charset=UTF-8");
			//获取字符输出流
			PrintWriter writer = servletResponse.getWriter();
			writer.println("Hello,world11");
			writer.flush();
			req.getAsyncContext().complete();
		}
	}
	
	@Override
		public void init() throws ServletException {
			super.init();
		}
}

//asyncContext.dispatch(path);//这两个又是请求转发和重定向吗  
//asyncContext.dispatch(context, path);
/*req.getServletContext().addFilter(filterName, new Filter() {filter又是在这里加的吗

@Override
public void init(FilterConfig filterConfig) throws ServletException {
	
}

@Override
public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException {
	
}

@Override
public void destroy() {
	
}
})


servletResponse.setCharacterEncoding(charset);
servletResponse.setContentLength(len);
servletResponse.setContentLengthLong(len);
servletResponse.setLocale(loc);

ServletRequest servletRequest = asyncContext.getRequest();
servletRequest.setAttribute(name, o);
servletRequest.setCharacterEncoding(env);
servletRequest.getAttribute(name)
servletRequest.getCharacterEncoding()
servletRequest.getContentLength()
servletRequest.getContentType()
servletRequest.getLocalAddr()
servletRequest.getLocalPort()
servletRequest.getParameter(name)
servletRequest.getServerName()
servletRequest.getServerPort()*/