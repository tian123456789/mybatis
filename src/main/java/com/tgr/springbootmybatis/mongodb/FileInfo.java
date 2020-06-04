package com.tgr.springbootmybatis.mongodb;

public class FileInfo {
	private String filename;
	private long contentlen;
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public long getContentlen() {
		return contentlen;
	}
	public void setContentlen(long contentlen) {
		this.contentlen = contentlen;
	}
	
}
