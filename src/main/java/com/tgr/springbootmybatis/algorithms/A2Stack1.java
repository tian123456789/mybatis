package com.tgr.springbootmybatis.algorithms;

import java.util.stream.Stream;

import org.assertj.core.util.Arrays;

/**
 * @author tgr 栈 并实现eclipse分隔符匹配 分隔符匹配程序从字符串中不断地读取程序， 每次读取一个字符，若发现它是左分隔符（
 *         {、[、(），将它压入栈中。当读到一个右分隔符时（)、]、}），弹出栈顶元素，
 *         并且查看它是否和该右分隔符匹配。如果它们不匹配，则程序报错。 如果到最后一直存在着没有被匹配的分隔符，程序也报错
 */
public class A2Stack1 {
	public static void main(String[] args) {
		String str = "(jnvdnv)";
		BrecketChecker checker = new BrecketChecker(str);
		checker.check();
		
	}
	
	
}

/**
 * @author tgr 分隔符匹配
 */
class BrecketChecker {
	private String input;// 存储待检测的字符串
	// 构造 接收待检测字符串

	public BrecketChecker(String in) {
		this.input = in;
	}

	// 检测分隔符匹配的方法
	public void check() {
		int strLength = input.length();
		Stack stack = new Stack(strLength);
		String s = "1,2,3";

		Arrays.asList(input.toCharArray())
		.stream()
		.map(c -> c.toString().charAt(0))
		.forEach(c -> {
			char ch = c;
			cc(ch);
			switch (ch) {
			case '{':
			case '[':
			case '(':
				// 如果为左分隔符，压入栈
				stack.push(ch);
				break;
			case '}':
			case ']':
			case ')':
				// 如果为右分隔符，与栈顶元素进行匹配
				if (!stack.isEmpty()) {
					char chx = stack.pop();

					if ((ch == '{' && chx != '}') || (ch == '(' && chx != ')') || (ch == '[' && chx != ']')) {
						System.out.println("匹配出错！字符：" + ch /*+ ",下标：" + i*/);
					}
				} else {
					System.out.println("匹配出错！字符：" + ch /*+ ",下标：" + i*/);
				}

			default:
				break;
			}

		 if(!stack.isEmpty()){
             //匹配结束时如果栈中还有元素，证明右分隔符缺失
             System.out.println("有括号没有关闭！");
		 }}

			);

	}
	
	public void cc(char c) {
		System.out.println("c="+c);
	}
}

/**
 * LIFO后进先出  只有top索引
 * @author tgr 栈
 * 出栈 顶部top弹出数据 arr[top--]->ele top指向前一个
 * 入栈 top后放入数据 arr[++top]=ele top指向后一个
 * 
  	角标		 -1   	    0	   		1	   		  2	   
  				
 	入栈         默认
 						a默认
 						a     	 	b默认
 						a      		b       	 c默认
 	
 	出栈                             a     	 	b默认      	 c弹
 					    a默认      	b弹         		 c弹
 			默认               a弹			b弹			 c弹			
 */
class Stack {
	private int size;// 栈大小
	private int top;// 栈顶元素下标
	private char[] stackArray;// 栈容器

	public Stack(int size) {
		stackArray = new char[size];
		top = -1;// 初始化无元素
		this.size = size;
	}

	// 入栈 栈顶下标+1
	public void push(char element) {
		stackArray[++top] = element;
	}

	// 出栈 栈顶下标-1
	public char pop() {
		return stackArray[top--];
	}

	// 查看栈顶 不删除栈顶元素
	public char peek() {
		return stackArray[top];
	}

	// 判断栈是否为空
	public boolean isEmpty() {
		return (top == -1);
	}

	// 判断是否已满
	public boolean isFull() {
		return (top == size - 1);
	}
}
