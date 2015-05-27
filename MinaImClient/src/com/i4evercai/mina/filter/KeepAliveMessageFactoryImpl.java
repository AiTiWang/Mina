package com.i4evercai.mina.filter;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;

/**
  * @ClassName: KeepAliveMessageFactoryImpl
  * @Description: 发送心跳包的内容
 * 				getResponse()---->isResponse();获取数据判断心跳事件（目的是判断是否触发心跳超时异常）
 * 			isRequest()----->getRequest(); 写回数据是心跳事件触发的数据（目的写回给服务器（客户端）心跳包）				
  * @author 4evercai
  * @date 2015年5月9日 下午4:17:39
  *
  */
public class KeepAliveMessageFactoryImpl implements KeepAliveMessageFactory {  
		/** 心跳包内容 */
		private static final String HEARTBEATREQUEST = "0x11";
		private static final String HEARTBEATRESPONSE = "0x12";

		@Override
		public boolean isRequest(IoSession session, Object message) {
			System.out.println("请求心跳包信息: " + message);
			if (message.equals(HEARTBEATREQUEST))
				return true;
			return false;
		}

		@Override
		public boolean isResponse(IoSession session, Object message) {
			System.out.println("响应心跳包信息: " + message);
			if (message.equals(HEARTBEATRESPONSE))
				return true;
			return false;
		}

		@Override
		public Object getRequest(IoSession session) {
			System.out.println("请求预设信息: " + HEARTBEATREQUEST);
			/** 返回预设语句 */
			return HEARTBEATREQUEST;
		}

		@Override
		public Object getResponse(IoSession session, Object request) {
			System.out.println("响应预设信息: " + HEARTBEATRESPONSE);
			/** 返回预设语句 */
			return HEARTBEATRESPONSE;
			// return null;
		}



}  