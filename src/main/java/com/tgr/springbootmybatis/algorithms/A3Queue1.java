package com.tgr.springbootmybatis.algorithms;

import java.util.stream.Stream;

import org.assertj.core.util.Arrays;

public class A3Queue1 {

	public static void main(String[] args) throws Exception {
		PriorityQueue p = new PriorityQueue(10, 3);
		p.insert(1);
		Stream.of(Arrays.asList(p.getQueArray())).forEach(x->System.out.println(x));
		p.insert(2);
		Stream.of(Arrays.asList(p.getQueArray())).forEach(x->System.out.println(x));
		p.insert(3);
		Stream.of(Arrays.asList(p.getQueArray())).forEach(x->System.out.println(x));
		p.insert(6);
		Stream.of(Arrays.asList(p.getQueArray())).forEach(x->System.out.println(x));
		p.insert(7);
		Stream.of(Arrays.asList(p.getQueArray())).forEach(x->System.out.println(x));
		p.insert(4);
		Stream.of(Arrays.asList(p.getQueArray())).forEach(x->System.out.println(x));
		p.insert(5);
		Stream.of(Arrays.asList(p.getQueArray())).forEach(x->System.out.println(x));
	}
}

/** @author tgr
 * 
 * 循环队列 FIFO 先进先出
 * 有 头索引 尾索引
 *
 * insert ==尾部插入  arr{++尾部}=element case正常:尾部指针下移
 * 							   case 如果尾=length-1 -> 那么尾=头(-1) 下一次从头0插入
 * delete  ==尾部删除       尾部++指针下移 case尾部到头 尾=0下一次从头开始删除
 * peek查看 头部弹出         return arr[头]
 * 
  
 
 		数组索引 	-1   		0 		1		2		
 					 尾默认          头默认
 		
 		插入                			a头尾	
 					            a头		b尾
 								a头		b		c尾
 		
 		弹出						a弹         b		c尾
 		        				a弹         b弹         c尾
 		        	尾			a弹         b弹         c弹      此时再插入将从0开始 因为abc均属弹出状态 再次插入或者覆盖无影响      
 		        	
   					
   
 */
class Queue {
    
    private int [] queArray;
    private int maxSize;
    public int head;   //存储队头元素的下标
    public int end;    //存储队尾元素的下标
    private int length; //队列长度
   
    //构造方法，初始化队列
    public Queue(int maxSize){
           this.maxSize = maxSize;
           queArray = new int [maxSize];
           head = 0;
           end = -1;
           length = 0;
    }
   
    //插入
    public void insert(int elem) throws Exception{
           if(isFull()){
                  throw new Exception("队列已满，不能进行插入操作！");
           }
           //如果队尾指针已到达数组的末端，插入到数组的第一个位置
           if(end == maxSize-1){
                  end = -1;
           }
           queArray[++end] = elem;
           length++;
    }
   
    //移除
    public int remove() throws Exception{
           if(isEmpty()){
                  throw new Exception("队列为空，不能进行移除操作！");
           }
           int elem = queArray[head++];
           //如果队头指针已到达数组末端，则移到数组第一个位置
           if(head == maxSize){
                  head = 0;
           }
           length--;
           return elem;
    }
   
    //查看队头元素
    public int peek() throws Exception{
           if(isEmpty()){
                  throw new Exception("队列内没有元素！");
           }
           return queArray[head];
    }
   
    //获取队列长度
    public int size(){
           return length;
    }
   
    //判空
    public boolean isEmpty(){
           return (length == 0);
    }
   
    //判满
    public boolean isFull(){
           return (length == maxSize);
    }

}


class PriorityQueue {

	private int[] queArray;
	private int maxSize;
	private int length; // 队列长度
	private int basePoint; // 基准点

	// 构造方法，初始化队列
	public PriorityQueue(int maxSize, int basePoint) {
		this.maxSize = maxSize;
		this.basePoint = basePoint;
		queArray = new int[maxSize];
		length = 0;
	}

	//插入
    public void insert2(int elem) throws Exception{
           if(isFull()){
                  throw new Exception("队列已满，不能进行插入操作！");
           }
          
           //如果队列为空，插入到数组的第一个位置
           if(length == 0){
                  queArray[length++] = elem;
           }else{
                  int i;
                  for(i=length;i>0;i--){
                        
                         int dis =Math.abs(elem-basePoint);  //待插入元素的距离
                         int curDis =Math.abs(queArray[i-1]-basePoint); //当前元素的距离
                        
                         //将比插入元素优先级高的元素后移一位
                         if(dis>= curDis){
                                queArray[i] =queArray[i-1];
                         }else{
                                break;
                         }
                  }
                  queArray[i] = elem;
                  length++;
           }
    }

	
	// 插入
	public void insert(int elem) throws Exception {//保证尾部优先级最高
		if (isFull()) {
			throw new Exception("队列已满，不能进行插入操作！");
		}

		// 如果队列为空，插入到数组的第一个位置
		if (length == 0) {
			queArray[length++] = elem;
		} else {
			
			int i;
			for (i = length-1; i >= 0; i--) {
				int waitInsert = Math.abs(elem - basePoint); // 待插入元素的距离
				int currentPosition = Math.abs(queArray[i] - basePoint); // 当前元素的距离

				if (currentPosition < waitInsert) {//当前元素优先级<待插入元素优先级   数值最近优先级最高
					queArray[i+1] = queArray[i];//当前元素向后移1位   
				} else {//当前元素< 待插入 就不要移位了  他的下一个位置已经覆盖了下第二个位置
					break;
				}
			}
			queArray[i+1] = elem;
			length++;
			
		}
	}

	// 移除
	public int remove() throws Exception {
		if (isEmpty()) {
			throw new Exception("队列为空，不能进行移除操作！");
		}
		int elem = queArray[--length];
		return elem;
	}

	// 查看队头元素
	public int peek() throws Exception {
		if (isEmpty()) {
			throw new Exception("队列内没有元素！");
		}
		return queArray[length - 1];
	}

	// 返回队列长度
	public int size() {
		return length;
	}

	// 判空
	public boolean isEmpty() {
		return (length == 0);
	}

	// 判满
	public boolean isFull() {
		return (length == maxSize);
	}
	
	public int[] getQueArray() {
		return queArray;
	}
    
   

}

