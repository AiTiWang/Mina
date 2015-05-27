package com.i4evercai.minaim.client;

import java.io.Serializable;

public class MinaEvent implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int code;
	private String notice;
	
	public MinaEvent(int code,String notice){
		this.code = code;
		this.notice = notice;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	
}
