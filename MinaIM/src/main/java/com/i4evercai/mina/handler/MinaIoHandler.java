package com.i4evercai.mina.handler;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.session.IoSessionConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.i4evercai.mina.session.DefaultIoSessionManager;

public class MinaIoHandler extends IoHandlerAdapter {
	private Logger logger = LoggerFactory.getLogger(MinaIoHandler.class);

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		super.exceptionCaught(session, cause);
		logger.error("exceptionCaught()... from " + session.getRemoteAddress());
		cause.printStackTrace();
	}

	@Override
	public void inputClosed(IoSession session) throws Exception {
		super.inputClosed(session);
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		super.messageReceived(session, message);
		logger.debug("messageReceived()... from " + session.getRemoteAddress().toString()
				+ ".....内容：" + message.toString());
		System.out
				.println("messageReceived()... from "
						+ session.getRemoteAddress().toString() + ".....内容："
						+ message.toString());
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		super.messageSent(session, message);
		logger.debug("messageSent()... from " + session.getRemoteAddress().toString());
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		super.sessionClosed(session);

		logger.debug("sessionClosed()... from " + session.getRemoteAddress());
		DefaultIoSessionManager.getManager().removeSession(session);
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		super.sessionCreated(session);
		logger.debug("sessionCreated()... from " + session.getRemoteAddress().toString());
		System.out.println("sessionCreated()... from "
				+ session.getRemoteAddress().toString());
		DefaultIoSessionManager.getManager().addSession(
				session.getRemoteAddress().toString(), session);
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		super.sessionIdle(session, status);
		logger.debug("sessionIdle()... from " + session.getRemoteAddress().toString());
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		super.sessionOpened(session);
	}

}
