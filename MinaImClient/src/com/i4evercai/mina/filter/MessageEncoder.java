package com.i4evercai.mina.filter;

import java.nio.ByteOrder;
import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import android.annotation.TargetApi;
import android.os.Build;

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
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out)
			throws Exception {
		System.out.println("endcode::"+message);
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


}