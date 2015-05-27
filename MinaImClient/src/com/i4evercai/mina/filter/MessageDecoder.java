package com.i4evercai.mina.filter;

import java.nio.ByteOrder;
import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import android.annotation.TargetApi;
import android.os.Build;

import com.i4evercai.mina.bean.MsgPack;

/**
 * 客户端消息解码
 * 
 * @author
 * 
 */
public class MessageDecoder extends CumulativeProtocolDecoder {
	private Charset charset = Charset.forName("UTF-8");

	public MessageDecoder() {
	}


	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@Override
	protected boolean doDecode(IoSession session, IoBuffer ioBuffer,
			ProtocolDecoderOutput out) throws Exception {
		ioBuffer.order(ByteOrder.LITTLE_ENDIAN);
		MsgPack mp = (MsgPack) session.getAttribute("nac-msg-pack"); // 从session对象中获取“xhs-upload”属性值
		
		if (null == mp) {
			if (ioBuffer.remaining() >= 8) {
				// 取消息体长度
				int msgLength = ioBuffer.getInt();
				int msgMethod = ioBuffer.getInt();
				mp = new MsgPack();
				mp.setMsgLength(msgLength);
				mp.setMsgMethod(msgMethod);
				session.setAttribute("nac-msg-pack", mp);
				return true;
			}
			return false;
		}
		if (ioBuffer.remaining() >= mp.getMsgLength()) {
			byte[] msgPack = new byte[mp.getMsgLength()];
			ioBuffer.get(msgPack);
			mp.setMsgPack(new String(msgPack, charset));
			session.removeAttribute("nac-msg-pack");
			out.write(mp);
			return true;
		}
		return false;
	}

}