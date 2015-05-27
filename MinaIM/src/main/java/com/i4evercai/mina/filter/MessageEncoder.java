package com.i4evercai.mina.filter;

import java.io.NotSerializableException;
import java.io.Serializable;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.i4evercai.mina.bean.MsgPack;

/**
 * 客户端消息发送前进行编码,可在此加密消息
 * 
 * @author
 * 
 */
public class MessageEncoder extends ProtocolEncoderAdapter {
	private Charset charset = Charset.forName("UTF-8");

	public MessageEncoder() {
	}

	// 在此处实现对MsgProtocolEncoder包的编码工作，并把它写入输出流中
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out)
			throws Exception {
		if (message instanceof MsgPack) {
			MsgPack mp = (MsgPack) message;
			IoBuffer buf = IoBuffer.allocate(mp.getMsgLength());
			buf.order(ByteOrder.LITTLE_ENDIAN);
			buf.setAutoExpand(true);
			// 设置消息内容的长度
			buf.putInt(mp.getMsgLength());
			// 设置消息的功能函数
			buf.putInt(mp.getMsgMethod());
			if (null != mp.getMsgPack()) {
				buf.put(mp.getMsgPack().getBytes(charset));
			}
			buf.flip();
			out.write(buf);
			out.flush();
			buf.free();
		}else if(message instanceof String){
			byte[] b=((String) message).getBytes(charset);
			IoBuffer buf = IoBuffer.allocate(b.length);
			buf.order(ByteOrder.LITTLE_ENDIAN);
			buf.setAutoExpand(true);
			// 设置消息内容的长度
			buf.putInt(b.length);
			buf.flip();
			out.write(buf);
			out.flush();
			buf.free();
		}
	}

	public void dispose() throws Exception {
	}

	public void encode0(IoSession arg0, Object arg1, ProtocolEncoderOutput arg2)
			throws Exception {
		if (!(arg1 instanceof Serializable)) {
			throw new NotSerializableException();
		}
		IoBuffer buf = IoBuffer.allocate(64);
		buf.setAutoExpand(true);
		buf.putObject(arg1);

		int objectSize = buf.position() - 4;
		if (objectSize > 1024) {
			throw new IllegalArgumentException("The encoded object is too big: "
					+ objectSize + " (> " + 1024 + ')');
		}

		buf.flip();
		arg2.write(buf);
	}

}