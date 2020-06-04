package com.tgr.springbootmybatis.design.chainResponsibility;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author tgr
 * servlet经典视线
 * filter(context,filterChain#doFilter(ctx);)
 * filterChain{list filter;doFilter(){按顺序调用filter#dofilter}}
 * eventRunnabel(FilterChain){run(){filterChain#dofilter}}
 * 
 * 1 运行filterChain 拿到第一个filter
 * 2 第一个filter#dofilter  filter#filterChain#doFilter(filterChain)
 * 3 filterChain拿到第二个filter
 * 4 第二个filter#dofilter  filter#filterChain#doFilter(filterChain)
 * 5 filterChain拿到第三个filter
 * 6 第三个filter#dofilter  filter#filterChain#doFilter(filterChain)
 * 7 filterChain里面的filter没有下一个 执行到底
 * 
 * 总结: 一个带有多个filter的filterChian 进行filterChain->filter->filterChain循环
 */
public class ServletImpl {

	static ExecutorService executorService =
			Executors.newFixedThreadPool(5);
	
	public static void main(String[] args) {
		List<MyFilter> list = new ArrayList<MyFilter>();
		list.add(new PrintOne());
		list.add(new PrintTwo());
		list.add(new PrintThree());
		
		MyFilterChain filterChain = new DefaultFilterChain(list);
		
		executorService.submit(new MyEvent(filterChain));
		executorService.submit(new MyEvent(filterChain));
		executorService.submit(new MyEvent(filterChain));
		
		executorService.shutdown();
	}
	
	public static class MyEvent implements Runnable{
		MyFilterChain filterChain;
		public MyEvent(MyFilterChain filterChain) {
			this.filterChain = filterChain;
		}
		@Override
		public void run() {
			MyContext ctx = new TestContext();
			filterChain.doFilter(ctx);
		}
	}
}

interface MyFilter{
	void doFilter(MyContext ctx,MyFilterChain fiterChain);
}

class DefaultFilterChain implements MyFilterChain{

	List<MyFilter> list;
	volatile AtomicInteger pos = new AtomicInteger();
	
	public DefaultFilterChain(List<MyFilter> list) {
		this.list = list;
	}
	
	@Override
	public void doFilter(MyContext ctx) {
		if(pos.get() < list.size()) {
			MyFilter filter = list.get(pos.get());
			pos.incrementAndGet();
			
			filter.doFilter(ctx, this);
		}
	}
	
}

interface MyFilterChain{
	void doFilter(MyContext ctx);
}

class PrintOne implements MyFilter{

	@Override
	public void doFilter(MyContext ctx, MyFilterChain fiterChain) {
		System.out.println(Thread.currentThread().getId()+"::1");
		fiterChain.doFilter(ctx);
	}
	
}

class PrintTwo implements MyFilter{

	@Override
	public void doFilter(MyContext ctx, MyFilterChain fiterChain) {
		System.out.println(Thread.currentThread().getId()+"::2");
		fiterChain.doFilter(ctx);
	}
	
}

class PrintThree implements MyFilter{

	@Override
	public void doFilter(MyContext ctx, MyFilterChain fiterChain) {
		System.out.println(Thread.currentThread().getId()+"::3");
		fiterChain.doFilter(ctx);
	}
	
}

class TestContext implements MyContext{
	
}

interface MyContext {
	/*
	public MyContext getContext(String uripath);

	public String getContextPath();

	public int getMinorVersion();

	public int getMajorVersion();

	public String getMimeType(String file);

	public Set getResourcePaths(String path);

	public URL getResource(String path);

	public InputStream getResourceAsStream(String path);

	public RequestDispatcher getRequestDispatcher(String path);

	public RequestDispatcher getNamedDispatcher(String name);

	public Servlet getServlet(String name);

	public String getRealPath(String path);

	public String getServerInfo();

	public String getInitParameter(String name);

	public Enumeration getInitParameterNames();

	public Object getAttribute(String name);

	public Enumeration getAttributeNames();

	public String getServletContextName();

	public void removeAttribute(String name);

	public void setAttribute(String name, Object object);
	*/
}
