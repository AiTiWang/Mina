package com.i4evercai.mina.session;

import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class DefaultSessionManager implements SessionManager {
	private static HashMap<String, IMSession> sessions = new HashMap<String, IMSession>();

	private static final AtomicInteger connectionsCounter = new AtomicInteger(0);
	private static DefaultSessionManager manager;

	public static DefaultSessionManager getManager() {
		if (manager == null) {
			synchronized (DefaultSessionManager.class) {
				manager = new DefaultSessionManager();
			}

		}
		return manager;
	}

	@Override
	public void addSession(String account, IMSession session) {
		if (session != null) {
			session.setAttribute(IMSession.SESSION_KEY, account);
			sessions.put(account, session);
			connectionsCounter.incrementAndGet();
		}

	}

	@Override
	public IMSession getSession(String account) {

		return sessions.get(account);
	}

	@Override
	public Collection<IMSession> getSessions() {
		return sessions.values();
	}

	public void removeSession(IMSession session) {

		sessions.remove(session.getAttribute(IMSession.SESSION_KEY));
	}

	@Override
	public void removeSession(String account) {

		sessions.remove(account);

	}

	@Override
	public boolean containsIMSession(IMSession ios) {
		return sessions.containsKey(ios.getAttribute(IMSession.SESSION_KEY))
				|| sessions.containsValue(ios);
	}

	@Override
	public String getAccount(IMSession ios) {
		if (ios.getAttribute(IMSession.SESSION_KEY) == null) {
			for (String key : sessions.keySet()) {
				if (sessions.get(key).equals(ios)
						|| sessions.get(key).getNid() == ios.getNid()) {
					return key;
				}
			}
		} else {
			return ios.getAttribute(IMSession.SESSION_KEY).toString();
		}

		return null;
	}

}
