package com.tgr.springbootmybatis.mongodb;


import java.util.Date;

/**
 * @author lee
 * @date 2019/9/20.
 */
public class RichTextInfo {
    private String cont;
    private String title;
    private String issuerName;
    private Date issuerTime;
	public String getCont() {
		return cont;
	}
	public void setCont(String cont) {
		this.cont = cont;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIssuerName() {
		return issuerName;
	}
	public void setIssuerName(String issuerName) {
		this.issuerName = issuerName;
	}
	public Date getIssuerTime() {
		return issuerTime;
	}
	public void setIssuerTime(Date issuerTime) {
		this.issuerTime = issuerTime;
	}
    
    
}
