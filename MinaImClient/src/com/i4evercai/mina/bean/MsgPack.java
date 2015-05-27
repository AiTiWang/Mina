package com.i4evercai.mina.bean;

import java.io.Serializable;

public class MsgPack implements Serializable {
	/**
	 * 序列化和反序列化的版本号
	 */
	private static final long serialVersionUID = 1L;
	// 消息长度
	private int msgLength;
	// 消息方法
	private int msgMethod;
	// 消息包内容
	private String msgPack;

	public MsgPack() {
	}

	public int getMsgLength() {
		return msgLength;
	}

	public void setMsgLength(int msgLength) {
		this.msgLength = msgLength;
	}

	public int getMsgMethod() {
		return msgMethod;
	}

	public void setMsgMethod(int msgMethod) {
		this.msgMethod = msgMethod;
	}

	public String getMsgPack() {
		return msgPack;
	}

	public void setMsgPack(String msgPack) {
		this.msgPack = msgPack;
	}

	public MsgPack(int msgMethod, String msgPack) {

		this.msgMethod = msgMethod;
		this.msgPack = msgPack;
		this.msgLength = msgPack.getBytes().length;
	}

	public MsgPack(int msgLength, int msgMethod, String msgPack) {
		this.msgLength = msgLength;
		this.msgMethod = msgMethod;
		this.msgPack = msgPack;
	}

	public String toString() {
		return "MsgPack [msgLength=" + msgLength + ", msgMethod=" + msgMethod
				+ ", msgPack=" + msgPack + "]";
	}

}
