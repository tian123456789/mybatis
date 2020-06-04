package com.tgr.springbootmybatis;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestClearMap {

	public static void main(String[] args) {
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		
		for(int i = 0 ; i < 50000 ; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("AAAA","AAAA");
			map.put("BBBB", "BBBB");
			map.put("CCCC", "CCCC");
			map.put("DDDD", "DDDD");
			map.put("EEEE", "EEEE");
			map.put("FFFF", "FFFF");
			map.put("GGGG", "GGGG");
			map.put("HHHH", "HHHH");
			map.put("IIII", "IIII");
			map.put("JJJJ", "JJJJ");
			map.put("KKKK", "KKKK");
			list.add(map);
		}
		

		System.err.println("");
		list.clear();
		list = null;
		System.gc();//只会回收welafRefe soft  如何利用 虚弱做和缓存?
		System.err.println("");
	}
}
