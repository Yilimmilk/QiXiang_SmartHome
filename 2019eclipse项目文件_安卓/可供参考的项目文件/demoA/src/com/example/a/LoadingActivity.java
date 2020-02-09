package com.example.a;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoadingActivity extends Activity {

	private ProgressBar bar;
	private TextView tv;

	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			bar.setProgress(msg.what);
			switch(msg.what){
			case 10:
				tv.setText("���ڼ��ش�������..........");
				break;
			case 20:
				tv.setText("�������ü������..........");
				break;
			case 30:
				tv.setText("���ڼ��ؽ�������..........");
				break;
			case 50:
				tv.setText("�������ü������..........");
				break;
	
			case 60:
				tv.setText("���ڳ�ʼ������..........");
				break;
			case 80:
				tv.setText("�����ʼ�����..........");
				break;
			case 100:
				tv.setText("����ϵͳ��..........");
				break;
			case 101:
				startActivity(new Intent(LoadingActivity.this, LoginActivity.class));
				break;
			}
		};
	};
	
	
	Thread t;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading);
		
		bar = (ProgressBar) findViewById(R.id.progressBar1);
		tv = (TextView) findViewById(R.id.textView1);
		
		
		t = new Thread(){
			public void run() {
				for(int i=0;i<=101;i++){
					Message msg = new Message();
					msg.what=i;
					handler.sendMessage(msg);
					try {
						sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
		};
		t.start();
		
		
	}

	

}
