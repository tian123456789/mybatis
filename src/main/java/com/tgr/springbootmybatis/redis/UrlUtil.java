package com.tgr.springbootmybatis.redis;

/**
 * url工具类
 */

public class UrlUtil {

	/**
	 * 抽取url地址
	 */
	public String extractUrl(String comboUrl){
		return comboUrl.substring(0 , comboUrl.lastIndexOf("#")) ;
	}

	/**
	 * 抽取url中的级别
	 */
	public int extractLevel(String comboUrl){
		return Integer.parseInt(comboUrl.substring(comboUrl.lastIndexOf("#") + 1)) ;
	}
}
