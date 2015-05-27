package com.i4evercai.mina.handler;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.i4evercai.mina.bean.MsgPack;
import com.i4evercai.minaim.client.MainActivity;
import com.i4evercai.minaim.client.MinaEvent;
import com.i4evercai.minaim.client.R;

import de.greenrobot.event.EventBus;

public class MinaIoHandler extends IoHandlerAdapter {
	private Context context;
	NotificationManager nm;

	public MinaIoHandler(Context context) {
		this.context = context;
		nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		super.exceptionCaught(session, cause);
		cause.printStackTrace();
		EventBus.getDefault().post(
				new MinaEvent(1, "mina*****异常：" + cause.getLocalizedMessage()));
	}

	@Override
	public void inputClosed(IoSession session) throws Exception {
		super.inputClosed(session);
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		super.messageReceived(session, message);
		System.out
				.println("messageReceived()... from "
						+ session.getRemoteAddress().toString() + ".....内容："
						+ message.toString());
		EventBus.getDefault().post(
				new MinaEvent(1, "mina***** 接收到消息：" + message.toString()));
		showNotify(message.toString());
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		super.messageSent(session, message);
		EventBus.getDefault().post(
				new MinaEvent(1, "mina***** 消息发送成功：" + message.toString()));
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		super.sessionClosed(session);
		EventBus.getDefault().post(new MinaEvent(1, "mina***** session 关闭~~~"));
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		super.sessionCreated(session);
		System.out.println("sessionCreated()... from "
				+ session.getRemoteAddress().toString());
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		super.sessionIdle(session, status);
		EventBus.getDefault().post(new MinaEvent(1, "mina***** session 空闲~~~"));
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		super.sessionOpened(session);
	}

	public void showNotify(String message) {
		NotificationCompat.Builder nb = new NotificationCompat.Builder(context);
		nb.setAutoCancel(true).setWhen(System.currentTimeMillis())
		.setSmallIcon(R.drawable.ic_launcher).setTicker("消息通知")
		.setContentTitle("消息通知").setContentText(message)
		.setDefaults(Notification.DEFAULT_LIGHTS)
		.setDefaults(NotificationCompat.DEFAULT_ALL);

		nm.notify(R.string.app_name, nb.build());
	}
}
