package com.i4evercai.mina.filter;

import java.nio.charset.Charset;

import org.apache.mina.filter.codec.textline.TextLineCodecFactory;

/**
 * android客户端端消息 编码解码器， 可以在 关于消息加密与加密， 可在
 * encoder时进行消息加密，在ServerMessageCodecFactory的 decoder时对消息解密
 * 
 * @author
 */
public class MessageCodecFactory1 extends TextLineCodecFactory {


	public MessageCodecFactory1() {
		super(Charset.forName("UTF-8"));
	}


	

}
