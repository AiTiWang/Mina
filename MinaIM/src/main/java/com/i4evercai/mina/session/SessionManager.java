package com.i4evercai.mina.session;

import java.util.Collection;

public interface SessionManager {
	/**
	 * 添加新的session
	 */
	public void addSession(String account,IMSession session);
	
	/**
	 * 
	 * @param account 客户端session的 key 一般可用 用户账号来对应session
	 * @return
	 */
	public IMSession getSession(String account);
	
	/**
	 * 获取所有session
	 * @return
	 */
	public Collection<IMSession> getSessions();
	
	/**
	 * 删除session
	 * @param session
	 */
    public void  removeSession(IMSession session) ;
    
    
    /**
	 * 删除session
	 * @param session
	 */
    public void  removeSession(String account);
    
    /**
	 * session是否存在
	 * @param session
	 */
    public boolean containsIMSession(IMSession ios);
    
    /**
	 * session获取对应的 用户 key  
	 * @param session
	 */
    public String getAccount(IMSession ios);
}
