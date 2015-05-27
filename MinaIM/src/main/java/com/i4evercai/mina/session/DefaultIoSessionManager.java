package com.i4evercai.mina.session;

import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.mina.core.session.IoSession;

public class DefaultIoSessionManager {
	private static HashMap<String, IoSession> sessions = new HashMap<String, IoSession>();

	private static final AtomicInteger connectionsCounter = new AtomicInteger(0);
	private static DefaultIoSessionManager manager;

	public static DefaultIoSessionManager getManager() {
		if (manager == null) {
			synchronized (DefaultIoSessionManager.class) {
				manager = new DefaultIoSessionManager();
			}

		}
		return manager;
	}

	public void addSession(String account, IoSession session) {
		if (session != null) {
			session.setAttribute(IMSession.SESSION_KEY, account);
			sessions.put(account, session);
			connectionsCounter.incrementAndGet();
		}

	}

	public IoSession getSession(String account) {

		return sessions.get(account);
	}

	public Collection<IoSession> getSessions() {
		return sessions.values();
	}

	public void removeSession(IoSession session) {

		sessions.remove(session.getAttribute(IMSession.SESSION_KEY));
	}

	public void removeSession(String account) {

		sessions.remove(account);

	}

	public boolean containsIoSession(IMSession ios) {
		return sessions.containsKey(ios.getAttribute(IMSession.SESSION_KEY))
				|| sessions.containsValue(ios);
	}


}
