package com.i4evercai.mina.filter;

import org.apache.mina.core.future.CloseFuture;
import org.apache.mina.core.future.IoFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;

/**
  * @ClassName: KeepAliveRequestTimeoutHandlerImpl
  * @Description: 心跳超时的处理
  * @author 4evercai
  * @date 2015年5月9日 下午4:20:33
  *
  */
public class KeepAliveRequestTimeoutHandlerImpl implements KeepAliveRequestTimeoutHandler {
	// 心跳超时处理
	@Override
	public void keepAliveRequestTimedOut(KeepAliveFilter filter, IoSession session)
			throws Exception {
		System.out.println("服务器端心跳包发送超时处理(即长时间没有发送（接受）心跳包)---关闭当前长连接");
		/* CloseFuture closeFuture = session.close(true);
	        closeFuture.addListener(new IoFutureListener<IoFuture>() {
	        	@Override
	            public void operationComplete(IoFuture future) {
	                if (future instanceof CloseFuture) {
	                    ((CloseFuture) future).setClosed();
	                    System.out.println("sessionClosed CloseFuture setClosed-->"+ future.getSession().getId());
	                }
	            }
	        });*/
	}

}
