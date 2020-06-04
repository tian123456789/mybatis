package com.tgr.springbootmybatis.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author tgr
 * 折半查询  要求数组有序
 */
public class A1ZheBan {

	public static void main(String[] args) {
		int[] arr = Stream
						.iterate(1, x->x+1)
						.limit(100000000)
						.map(x -> Integer.valueOf(x)).mapToInt(x->x).toArray();
		/*	
		List list = Stream
						.iterate(1, x->x+1)
						.limit(100000000)
						.map(x -> Integer.valueOf(x)).collect(Collectors.toList());
		*/
		
		long start = System.nanoTime();
		System.out.println(recursionBinarySearch(arr, 55555555, 0, arr.length-1)+"    用时"+(System.nanoTime()-start));
		
		long start2 = System.nanoTime();
		System.out.println(commonBinarySearch(arr, 55555555, 0, arr.length-1)+"    用时"+(System.nanoTime()-start2));
	}
	
	/**
	 * 递归实现
	 * @param arr
	 * @param key 关键字
	 * @param low 0
	 * @param high lenght-1
	 * @return
	 */
	public static int recursionBinarySearch(int[] arr,int key,int low,int high) {

		if(key < arr[low] || key > arr[high] || low > high) {
			return -1;
		}
		int middle = (low+high)/2;//初始位置
		if(arr[middle] > key) { //中间值>key 那么在左边
			return recursionBinarySearch(arr, key, low, middle-1);
		}else if(arr[middle] < key) { //在右面
			return recursionBinarySearch(arr, key, middle+1, high);
		}else {//arr[middle] = key
			return middle;
		}
	}
	
	/**
	 * while方式
	 * @param arr
	 * @param key
	 * @param low
	 * @param high
	 * @return
	 */
	public static int commonBinarySearch(int[] arr,int key,int low,int high) {
		if(key < arr[low] || key > arr[high] || low > high) {
			return -1;
		}
		
		while(low <= high) {
			int middle = (low+high)/2;
			if(arr[middle] > key) {
				high = middle-1;
			}else if(arr[middle] < key) {
				low =middle+1;
			}else {
				return middle;
			}
		}
		return -1;
	}
}
