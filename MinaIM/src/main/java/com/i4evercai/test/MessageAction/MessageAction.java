package com.i4evercai.test.MessageAction;
import java.util.Date;

import org.apache.mina.core.session.IoSession;

import com.i4evercai.mina.session.DefaultIoSessionManager;


public class MessageAction extends BaseAction{
	public void sendMsg(){
		System.out.println("*************action sendmsg *********");
		for(IoSession session:DefaultIoSessionManager.getManager().getSessions()){
			session.write("内容测试："+new Date());
		}
		responseJson("{\"code\":200}");
	}
}
