package com.tgr.springbootmybatis.algorithms;

public class A4Link {

	/**
	 * @author tgr
	 * 链结点封装类
	 */
	class Link{
		public int key;
		public String data;
		public Link next;
		public Link before;
		
		public Link(int age, String name) {
			this.key = age;
			this.data = name;
		}
		
		//打印该链结点信息
		public void displayLink() {
			System.out.println("name: "+data+",age: "+key);
		}
	}
	
	//链表的封装类
	class LinkList{
		private Link first;//指向链表中第一个链结点
		private Link last;
		public LinkList() {
			first = null;
		}
		
		//插入
		public void insert(Link link) {
			if(first == null) {//还没有
				first = link;//插入到第一个
				last = link;
			}else {//向最后一个插入
				last.next = link;
			}
		}
		
		//删除链结点，返回删除的链结点引用
		public Link delete(int key) throws Exception{
			
			if(isEmpty()) {
				throw new Exception("链表为空!不能进行删除操作");
			}
			Link currentLink = find(key);
			
			while(currentLink != null) {
				if(currentLink.key == key) {//是要删除的链结点
					if(currentLink.before == null) {//前置为空 是第一个结点
						this.first = currentLink.next;//跨过当前指向下一个 == 删除当前
					}else {//不是第一个
						if(currentLink.next != null) {//有下一个结点
							currentLink.before.next = currentLink.next;//跨过自己
						}else {//没有下一个结点
							currentLink.before.next = null;
						}
						break;//操作完毕退出
					}
				}else {//不是要删除的链结点
					
				}
			}
			
			Link temp = first;
			first = first.next;//链表指向下一个 链表结点
			return temp;
		}
		
		private Link find(int key) {
			return null;
		}
		
		public boolean isEmpty() {
			return false;
		}

		//打印出所有的链表元素
		public void displayList() {
			Link currentLink = first;
			while(currentLink != null) {//循环打印每个链结点
				currentLink.displayLink();
				currentLink = currentLink.next;
			}
		}
		
	}

	
}
