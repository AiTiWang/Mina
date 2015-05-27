package com.i4evercai.minaim.client.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.i4evercai.mina.MinaConnectorManager;
import com.i4evercai.mina.bean.MsgPack;

import de.greenrobot.event.EventBus;

public class MinaConnectorService extends Service {

	@Override
	public void onCreate() {

		super.onCreate();
		EventBus.getDefault().register(this);
		MinaConnectorManager.getManager(getApplicationContext()).connect();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		System.out.println("Server onDestroy~~~~~~~~~~");
		EventBus.getDefault().unregister(this);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	public void onEventMainThread(MsgPack msg) {
		MinaConnectorManager.getManager(getApplicationContext()).send(msg);
	}
}
