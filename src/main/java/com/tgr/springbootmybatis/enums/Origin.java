package com.tgr.springbootmybatis.enums;

import java.util.HashMap;
import java.util.Map;

public enum Origin {

	youku("优酷"), iqiyi("爱奇艺"), qq("腾讯"), le("乐视"), pptv("PPTV"), manlan("蔓蓝");

	private static Map<String, Origin> maps;

	private String label;

	private Origin(String label) {
		this.label = label;
	}

	public String label() {
		return label;
	}

	public static Map<String, Origin> maps() {
		if (maps == null) {
			maps = new HashMap<String, Origin>();
			for (Origin o : Origin.values()) {
				maps.put(o.name(), o);
			}
		}
		return maps;
	}
}
