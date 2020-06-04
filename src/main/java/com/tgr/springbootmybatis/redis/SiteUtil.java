package com.tgr.springbootmybatis.redis;

/**
 * @author tgr
 *	站点工具类
 */
public class SiteUtil {
	/**
	 * 通过url地址抽取域名
	 * http://www.sohu.com
	 * http://www.sohu.net
	 * http://www.sohu.org
	 * http://www.sohu.cn
	 * http://sohu.com/
	 * http://sohu.com
	 */
	public static String getDomianName(String url){
		//
		String[] arr = url.split("[\\.\\/]") ;//按照 . 或者 / 切割
		for(int i = 0 ; i < arr.length ; i ++){
			if(arr[i].equalsIgnoreCase("com")
					|| arr[i].equalsIgnoreCase("net")
					|| arr[i].equalsIgnoreCase("org")
					|| arr[i].equalsIgnoreCase("cn")){
				return arr[i - 1] + "." + arr[i] ;
			}
		}
		return null ;
	}
}

