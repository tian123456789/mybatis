package com.tgr.springbootmybatis.design.chainResponsibility;

import java.lang.invoke.VolatileCallSite;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author tgr
 * handler
 * handlerChain(currentHandler,nextHandler) 封装成节点数层次结构
 * 
 * handlerChain#handler ->第一个handler处理 调用Handler#nextHandler#handler  开始反复利用节点数 进行context处理 形成管道
 */
public class PipeLineImpl {

	static ExecutorService executorService = Executors.newFixedThreadPool(5);
	
	public static void main(String[] args) {
		
		MyContext2 ctx = new MyContext2("1");
		MyContext2 ctx1 = new MyContext2("2");
		
		MyHandler h1 = new PrintOne2();
		MyHandler h2 = new PrintTwo2();
		MyHandler h3 = new PrintThree2();
		
		MyHandler h11 = new PrintOne2();
		MyHandler h21 = new PrintTwo2();
		MyHandler h31 = new PrintThree2();
		
		MyHandlerChain handlerChain3 = new DefaultHandlerChain(null, h3);//(next,handler)
		MyHandlerChain handlerChain2 = new DefaultHandlerChain(handlerChain3, h2);
		MyHandlerChain handlerChain1 = new DefaultHandlerChain(handlerChain2, h1);
		
		MyHandlerChain handlerChain31 = new DefaultHandlerChain(null, h31);//(next,handler)
		MyHandlerChain handlerChain21= new DefaultHandlerChain(handlerChain31, h21);
		MyHandlerChain handlerChain11 = new DefaultHandlerChain(handlerChain21, h11);
		
		executorService.submit(new RunPrintEcho(handlerChain1, ctx));
		executorService.submit(new RunPrintEcho(handlerChain11, ctx1));
		
		executorService.shutdown();
	}
	
	public static class RunPrintEcho implements Runnable{

		MyHandlerChain handlerChain;
		MyContext2 ctx;
		
		public RunPrintEcho(MyHandlerChain handlerChain,MyContext2 ctx) {
			this.handlerChain = handlerChain;
			this.ctx = ctx;
		}
		
		@Override
		public void run() {
			handlerChain.handler(this.ctx);
		}
		
	}
}

interface MyHandlerChain{
	void handler(MyContext2 ctx);
	void fireNext(MyContext2 ctx);
}

class DefaultHandlerChain implements MyHandlerChain{

	private MyHandlerChain next;
	private MyHandler handler;
	
	public DefaultHandlerChain(MyHandlerChain next,MyHandler handler) {
		this.next = next;
		this.handler = handler;
	}
	
	@Override
	public void handler(MyContext2 ctx) {
		handler.handle(ctx, this);
	}

	@Override
	public void fireNext(MyContext2 ctx) {
		MyHandlerChain next_ = this.next;
		if(next_ != null) {
			next_.handler(ctx);
		}
	}
	
}


interface MyHandler{
	void handle(MyContext2 ctx,MyHandlerChain handlerChain);
}

class MyContext2{
	String name;
	public MyContext2(String name) {
		this.name = name;
	}
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
}

class PrintOne2 implements MyHandler{

	@Override
	public void handle(MyContext2 ctx, MyHandlerChain handlerChain) {
		System.out.println(Thread.currentThread().getId()+"::2_1   ctxName = "+ctx.getName());
		handlerChain.fireNext(ctx);
	}
	
}

class PrintTwo2 implements MyHandler{

	@Override
	public void handle(MyContext2 ctx, MyHandlerChain handlerChain) {
		System.out.println(Thread.currentThread().getId()+"::2_2   ctxName = "+ctx.getName());
		handlerChain.fireNext(ctx);
	}
	
}

class PrintThree2 implements MyHandler{

	@Override
	public void handle(MyContext2 ctx, MyHandlerChain handlerChain) {
		System.out.println(Thread.currentThread().getId()+"::2_3   ctxName = "+ctx.name);
		handlerChain.fireNext(ctx);
	}
	
}
