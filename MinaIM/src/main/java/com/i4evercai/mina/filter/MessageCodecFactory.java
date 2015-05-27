package com.i4evercai.mina.filter;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * android客户端端消息 编码解码器， 可以在 关于消息加密与加密， 可在
 * encoder时进行消息加密，在ServerMessageCodecFactory的 decoder时对消息解密
 * 
 * @author
 */
public class MessageCodecFactory implements ProtocolCodecFactory {

	private final MessageEncoder encoder;

	private final MessageDecoder decoder;

	public MessageCodecFactory() {
		encoder = new MessageEncoder();
		decoder = new MessageDecoder();
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		return encoder;
	}

	@Override
	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		return decoder;
	}

	

}
