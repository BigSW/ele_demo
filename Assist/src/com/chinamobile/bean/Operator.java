package com.chinamobile.bean;

import java.io.Serializable;

public class Operator implements Serializable{
	private static final long serialVersionUID = 1L;
	private String opernum;
	private String operpsw;
	public String getOpernum() {
		return opernum;
	}
	public void setOpernum(String opernum) {
		this.opernum = opernum;
	}
	public String getOperpsw() {
		return operpsw;
	}
	public void setOperpsw(String operpsw) {
		this.operpsw = operpsw;
	}
}
