package com.tgr.springbootmybatis.entity;

/**
 * 文章类
 */
public class Article {
	private Integer id ;
	private String title;
	private String publishTime;
	private String content;
	private String tags;
	private String count;
	private Dict_C1 c1;
	private Dict_C2 c2;
	private String other1;
	private String other2;
	private String other3;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public Dict_C1 getC1() {
		return c1;
	}

	public void setDict_C1(Dict_C1 c1) {
		this.c1 = c1;
	}

	public Dict_C2 getDict_C2() {
		return c2;
	}

	public void setDict_C2(Dict_C2 c2) {
		this.c2 = c2;
	}

	public String getOther1() {
		return other1;
	}

	public void setOther1(String other1) {
		this.other1 = other1;
	}

	public String getOther2() {
		return other2;
	}

	public void setOther2(String other2) {
		this.other2 = other2;
	}

	public String getOther3() {
		return other3;
	}

	public void setOther3(String other3) {
		this.other3 = other3;
	}
}
