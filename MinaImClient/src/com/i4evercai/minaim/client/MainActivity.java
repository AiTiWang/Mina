package com.i4evercai.minaim.client;

import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.i4evercai.mina.bean.MsgPack;
import com.i4evercai.minaim.client.service.MinaConnectorService;

import de.greenrobot.event.EventBus;

public class MainActivity extends ActionBarActivity {
	private EditText etContent;
	private Button btnSend;
	private TextView tvResult;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initViews();
		EventBus.getDefault().register(this);
		Intent intent = new Intent(this,MinaConnectorService.class);
		startService(intent);
	}
	private void initViews(){
		etContent = (EditText) findViewById(R.id.et_content);
		btnSend = (Button) findViewById(R.id.btnSend);
		tvResult = (TextView) findViewById(R.id.tv_result);
		btnSend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String content = etContent.getText().toString();
				if(TextUtils.isEmpty(content)){
					
				}else{
					MsgPack msg = new MsgPack(2000,content);
					EventBus.getDefault().post(msg);
				}
				
			}
		});
	}
	
	public void onEventMainThread(MinaEvent event) {
		tvResult.append(new Date()+"："+event.getNotice()+"\n");
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}
	
}
