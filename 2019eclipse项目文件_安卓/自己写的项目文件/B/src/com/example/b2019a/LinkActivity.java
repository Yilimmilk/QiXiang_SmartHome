package com.example.b2019a;

import com.bizideal.smarthome.socket.ConstantUtil;
import com.bizideal.smarthome.socket.ControlUtils;
import com.bizideal.smarthome.socket.DeviceBean;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class LinkActivity extends Activity {
	
	private TextView roomnumCenter;
	private CheckBox check;
	private Spinner sp11,sp12,sp13;
	private EditText linknum;
	
	private int select;
	private Double wenduDouble=0.0,guangzhaoDouble=0.0,num=0.0;
	private String select11,select12,select13;
	private String[]select11Item={"wendu","guangzhao"};
	private String[]select12Item={">","<="};
	private String[]select13Item={"fengshan","shedeng"};
	
	private Handler mHandler=new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_link);
		
		Intent intent =getIntent();
		select=intent.getIntExtra("selectRoomNum", 0);
		Log.d("��ǰ����", ""+select);
		
		roomnumCenter=(TextView)findViewById(R.id.tv_roomnum_center);
		check=(CheckBox)findViewById(R.id.cb_check);
		
		sp11=(Spinner)findViewById(R.id.sp_1_1);
		sp12=(Spinner)findViewById(R.id.sp_1_2);
		sp13=(Spinner)findViewById(R.id.sp_1_3);
		
		linknum=(EditText)findViewById(R.id.et_num1);
		
		roomnumCenter.setText("����:"+select);
		
		check.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
						mHandler.post( zidingyiRunnable );
		                Log.d("�߳̿���", "�Զ����߳�����");
	            } else {
	                if (zidingyiRunnable != null){
	                    mHandler.removeCallbacks( zidingyiRunnable );
	                	Log.d("�Զ���ģʽ", "�Ƴ��߳�");
	                }
	                ControlUtils.control(ConstantUtil.Fan, ConstantUtil.CHANNEL_ALL, ConstantUtil.CLOSE);
					new Handler().postDelayed(new Runnable() {
						public void run() {
							ControlUtils.control(ConstantUtil.Lamp, ConstantUtil.CHANNEL_ALL, ConstantUtil.CLOSE);
						}
					}, 50);
					Log.d("�Զ���ģʽ", "�ر�����豸");
	            }
			}
		});
		
		sp11.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				select11=select11Item[arg2];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		sp12.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				select12=select12Item[arg2];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		sp13.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				select13=select13Item[arg2];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
Runnable zidingyiRunnable = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				wenduDouble=Double.parseDouble(DeviceBean.getTemperature());
				guangzhaoDouble=Double.parseDouble(DeviceBean.getIllumination());
				
				num=Double.parseDouble(linknum.getText().toString());
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			if (TextUtils.isEmpty(linknum.getText().toString())) {
				Toast.makeText(LinkActivity.this, "����Ϊ��", Toast.LENGTH_SHORT).show();
				num=999999.0;
			}
			
			if (select11.equals("wendu")) {
				if (select12.equals(">")) {
					if (wenduDouble>num) {
						if (select13.equals("fengshan")) {
							ControlUtils.control(ConstantUtil.Fan, ConstantUtil.CHANNEL_ALL, ConstantUtil.OPEN);
							Log.d("�¶ȴ����趨ֵ", "���ȴ�");
						}else if (select13.equals("shedeng")) {
							ControlUtils.control(ConstantUtil.Lamp, ConstantUtil.CHANNEL_ALL, ConstantUtil.OPEN);
							Log.d("�¶ȴ����趨ֵ", "��ƴ�");
						}
					}else {
						ControlUtils.control(ConstantUtil.Fan, ConstantUtil.CHANNEL_ALL, ConstantUtil.CLOSE);
						new Handler().postDelayed(new Runnable() {
							public void run() {
								ControlUtils.control(ConstantUtil.Lamp, ConstantUtil.CHANNEL_ALL, ConstantUtil.CLOSE);
							}
						}, 50);
						Log.d("�¶ȴ����趨ֵ", "����δ��ɣ��ر�����豸");
					}
				}else if (select12.equals("<=")) {
					if (wenduDouble<=num) {
						if (select13.equals("fengshan")) {
							ControlUtils.control(ConstantUtil.Fan, ConstantUtil.CHANNEL_ALL, ConstantUtil.OPEN);
							Log.d("�¶�С�ڵ����趨ֵ", "���ȴ�");
						}else if (select13.equals("shedeng")) {
							ControlUtils.control(ConstantUtil.Lamp, ConstantUtil.CHANNEL_ALL, ConstantUtil.OPEN);
							Log.d("�¶�С�ڵ����趨ֵ", "��ƴ�");
						}
					}else {
						ControlUtils.control(ConstantUtil.Fan, ConstantUtil.CHANNEL_ALL, ConstantUtil.CLOSE);
						new Handler().postDelayed(new Runnable() {
							public void run() {
								ControlUtils.control(ConstantUtil.Lamp, ConstantUtil.CHANNEL_ALL, ConstantUtil.CLOSE);
							}
						}, 50);
						Log.d("�¶�С�ڵ����趨ֵ", "����δ��ɣ��ر�����豸");
					}
				}
			}
			
			if (select11.equals("guangzhao")) {
				if (select12.equals(">")) {
					if (guangzhaoDouble>num) {
						if (select13.equals("fengshan")) {
							ControlUtils.control(ConstantUtil.Fan, ConstantUtil.CHANNEL_ALL, ConstantUtil.OPEN);
							Log.d("���մ����趨ֵ", "���ȴ�");
						}else if (select13.equals("shedeng")) {
							ControlUtils.control(ConstantUtil.Lamp, ConstantUtil.CHANNEL_ALL, ConstantUtil.OPEN);
							Log.d("���մ����趨ֵ", "��ƴ�");
						}
					}else {
						ControlUtils.control(ConstantUtil.Fan, ConstantUtil.CHANNEL_ALL, ConstantUtil.CLOSE);
						new Handler().postDelayed(new Runnable() {
							public void run() {
								ControlUtils.control(ConstantUtil.Lamp, ConstantUtil.CHANNEL_ALL, ConstantUtil.CLOSE);
							}
						}, 50);
						Log.d("���մ����趨ֵ", "����δ��ɣ��ر�����豸");
					}
				}else if (select12.equals("<=")) {
					if (guangzhaoDouble<=num) {
						if (select13.equals("fengshan")) {
							ControlUtils.control(ConstantUtil.Fan, ConstantUtil.CHANNEL_ALL, ConstantUtil.OPEN);
							Log.d("����С�ڵ����趨ֵ", "���ȴ�");
						}else if (select13.equals("shedeng")) {
							ControlUtils.control(ConstantUtil.Lamp, ConstantUtil.CHANNEL_ALL, ConstantUtil.OPEN);
							Log.d("����С�ڵ����趨ֵ", "��ƴ�");
						}
					}else {
						ControlUtils.control(ConstantUtil.Fan, ConstantUtil.CHANNEL_ALL, ConstantUtil.CLOSE);
						new Handler().postDelayed(new Runnable() {
							public void run() {
								ControlUtils.control(ConstantUtil.Lamp, ConstantUtil.CHANNEL_ALL, ConstantUtil.CLOSE);
							}
						}, 50);
						Log.d("����С�ڵ����趨ֵ", "����δ��ɣ��ر�����豸");
					}
				}
			}
			
			mHandler.postDelayed(this, 2000);
            Log.d("�Զ���ģʽHandler����", "�ӳ����뿪ʼִ��");
		}
	};
	
	@Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacks( zidingyiRunnable );
        }
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_link, menu);
		return true;
	}

}
