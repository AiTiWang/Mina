package com.i4evercai.mina.filter;

import org.apache.mina.core.session.IdleStatus;

public class KeepAliveFilter extends org.apache.mina.filter.keepalive.KeepAliveFilter {

	public KeepAliveFilter() {
		super(new KeepAliveMessageFactoryImpl(), IdleStatus.BOTH_IDLE,new KeepAliveRequestTimeoutHandlerImpl());
	}
}
