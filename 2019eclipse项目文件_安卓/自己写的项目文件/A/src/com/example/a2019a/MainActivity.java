package com.example.a2019a;

import com.bizideal.smarthome.socket.ConstantUtil;
import com.bizideal.smarthome.socket.ControlUtils;
import com.bizideal.smarthome.socket.DataCallback;
import com.bizideal.smarthome.socket.DeviceBean;
import com.bizideal.smarthome.socket.LoginCallback;
import com.bizideal.smarthome.socket.SocketClient;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener,OnCheckedChangeListener{
	
	private TextView wendu,shidu,ranqi,guangzhao,pm25,qiya,yanwu,co2,renti;
	private Button shebeizhuangtai,kongtiao,dvd,dianshi,clkai,clting,clguan;
	private CheckBox shedeng,menjin,fengshan,baojing,modeLvyou,modeWendu,modeAnfang,modeZidingyi;
	private Spinner sp11,sp12,sp13;
	private EditText linknum;
	
	private Handler mHandler=new Handler();
	
	private String select11,select12,select13;
	private String[]select11Item={"wendu","guangzhao"};
	private String[]select12Item={">","<="};
	private String[]select13Item={"fengshan","shedeng"};
	private Double wenduDouble,guangzhaoDouble,num;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initView();
		initData();
		initSelect();
		
		SocketClient.getInstance().login( new LoginCallback() {
            @Override
            public void onEvent(final String status) {
                runOnUiThread( new Runnable() {
                    @Override
                    public void run() {
                        if (status.equals( ConstantUtil.SUCCESS  )) {
                            Toast.makeText( MainActivity.this, "�����ɹ���", Toast.LENGTH_SHORT ).show();
                        } else if (status.equals( ConstantUtil.FAILURE  )) {
                            Toast.makeText( MainActivity.this, "����ʧ�ܣ�", Toast.LENGTH_SHORT ).show();
                        } else {
                            Toast.makeText( MainActivity.this, "�����У�", Toast.LENGTH_SHORT ).show();
                        }
                    }
                } );
            }
        } );
	}

	private void initView(){
		wendu=(TextView)findViewById(R.id.tv_wendu);
		shidu=(TextView)findViewById(R.id.tv_shidu);
		ranqi=(TextView)findViewById(R.id.tv_ranqi);
		guangzhao=(TextView)findViewById(R.id.tv_guangzhao);
		pm25=(TextView)findViewById(R.id.tv_pm25);
		qiya=(TextView)findViewById(R.id.tv_qiya);
		yanwu=(TextView)findViewById(R.id.tv_yanwu);
		co2=(TextView)findViewById(R.id.tv_co2);
		renti=(TextView)findViewById(R.id.tv_renti);
		
		shebeizhuangtai=(Button)findViewById(R.id.bt_shebeizhuangtai);
		shebeizhuangtai.setOnClickListener(this);
		kongtiao=(Button)findViewById(R.id.bt_kongtiao);
		kongtiao.setOnClickListener(this);
		dvd=(Button)findViewById(R.id.bt_dvd);
		dvd.setOnClickListener(this);
		dianshi=(Button)findViewById(R.id.bt_dianshi);
		dianshi.setOnClickListener(this);
		clkai=(Button)findViewById(R.id.bt_clkai);
		clkai.setOnClickListener(this);
		clting=(Button)findViewById(R.id.bt_clting);
		clting.setOnClickListener(this);
		clguan=(Button)findViewById(R.id.bt_clguan);
		clguan.setOnClickListener(this);
		
		shedeng=(CheckBox)findViewById(R.id.cb_shedeng);
		shedeng.setOnCheckedChangeListener(this);
		menjin=(CheckBox)findViewById(R.id.cb_menjin);
		menjin.setOnCheckedChangeListener(this);
		fengshan=(CheckBox)findViewById(R.id.cb_fengshan);
		fengshan.setOnCheckedChangeListener(this);
		baojing=(CheckBox)findViewById(R.id.cb_baojingdeng);
		baojing.setOnCheckedChangeListener(this);
		modeLvyou=(CheckBox)findViewById(R.id.cb_mode_lvyou);
		modeLvyou.setOnCheckedChangeListener(this);
		modeWendu=(CheckBox)findViewById(R.id.cb_mode_wendu);
		modeWendu.setOnCheckedChangeListener(this);
		modeAnfang=(CheckBox)findViewById(R.id.cb_mode_anfang);
		modeAnfang.setOnCheckedChangeListener(this);
		modeZidingyi=(CheckBox)findViewById(R.id.cb_mode_zidingyi);
		modeZidingyi.setOnCheckedChangeListener(this);
		
		sp11=(Spinner)findViewById(R.id.sp_1_1);
		sp12=(Spinner)findViewById(R.id.sp_1_2);
		sp13=(Spinner)findViewById(R.id.sp_1_3);
		
		linknum=(EditText)findViewById(R.id.et_num1);
	}
	
	private void initData(){
		ControlUtils.getData();
		SocketClient.getInstance().getData(new DataCallback<DeviceBean>() {

			@Override
			public void onResult(final DeviceBean bean) {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						if (!TextUtils.isEmpty(bean.getTemperature())) {
							wendu.setText(bean.getTemperature());
						}
						if (!TextUtils.isEmpty(bean.getHumidity())) {
							shidu.setText(bean.getHumidity());
						}
						if (!TextUtils.isEmpty(bean.getGas())) {
							ranqi.setText(bean.getGas());
						}
						if (!TextUtils.isEmpty(bean.getIllumination())) {
							guangzhao.setText(bean.getIllumination());
						}
						if (!TextUtils.isEmpty(bean.getPM25())) {
							pm25.setText(bean.getPM25());
						}
						if (!TextUtils.isEmpty(bean.getAirPressure())) {
							qiya.setText(bean.getAirPressure());
						}
						if (!TextUtils.isEmpty(bean.getSmoke())) {
							yanwu.setText(bean.getSmoke());
						}
						if (!TextUtils.isEmpty(bean.getCo2())) {
							co2.setText(bean.getCo2());
						}
						if (!TextUtils.isEmpty( bean.getStateHumanInfrared() ) && bean.getStateHumanInfrared().equals( ConstantUtil.CLOSE )){
                            renti.setText( "����" );
                        }else{
                            renti.setText( "����" );
                        }
						if (!TextUtils.isEmpty( bean.getLamp() ) && bean.getLamp().equals( ConstantUtil.CLOSE )) {
                            shedeng.setChecked( false );
                            shedeng.setBackgroundResource(R.drawable.shedeng);
                        } else {
                            shedeng.setChecked( true );
                            shedeng.setBackgroundResource(R.drawable.shedeng_press);
                        }
                        if (!TextUtils.isEmpty( bean.getFan() ) && bean.getFan().equals( ConstantUtil.CLOSE )) {
                        	fengshan.setChecked( false );
                        	fengshan.setBackgroundResource(R.drawable.fengshan);
                        } else {
                            fengshan.setChecked( true );
                            fengshan.setBackgroundResource(R.drawable.fengshan_press);
                        }
                        if (!TextUtils.isEmpty( bean.getCurtain() ) && bean.getCurtain().equals( ConstantUtil.CHANNEL_3 )) {
                        	Toast.makeText(MainActivity.this, "������", Toast.LENGTH_SHORT).show();
                        } else if (!TextUtils.isEmpty( bean.getCurtain() ) && bean.getCurtain().equals( ConstantUtil.CHANNEL_1 )) {
                        	Toast.makeText(MainActivity.this, "����ͣ", Toast.LENGTH_SHORT).show();
                        } else if (!TextUtils.isEmpty( bean.getCurtain() ) && bean.getCurtain().equals( ConstantUtil.CHANNEL_2 )) {
                        	Toast.makeText(MainActivity.this, "������", Toast.LENGTH_SHORT).show();
                        }
                        if (!TextUtils.isEmpty( bean.getWarningLight() ) && bean.getWarningLight().equals( ConstantUtil.CLOSE )) {
                            baojing.setChecked( false );
                            baojing.setBackgroundResource(R.drawable.baojing);
                        } else {
                            baojing.setChecked( true );
                            baojing.setBackgroundResource(R.drawable.baojing_press);
                        }
					}
				});
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		if (!buttonView.isPressed()) return;
		switch (buttonView.getId()) {
		case R.id.cb_shedeng:
			if (isChecked) {
				ControlUtils.control(ConstantUtil.Lamp, ConstantUtil.CHANNEL_ALL, ConstantUtil.OPEN);
			}else {
				ControlUtils.control(ConstantUtil.Lamp, ConstantUtil.CHANNEL_ALL, ConstantUtil.CLOSE);
			}
			break;
			
		case R.id.cb_menjin:
			if (isChecked) {
				ControlUtils.control(ConstantUtil.RFID_Open_Door, ConstantUtil.CHANNEL_1, ConstantUtil.OPEN);
			}else {
				ControlUtils.control(ConstantUtil.RFID_Open_Door, ConstantUtil.CHANNEL_1, ConstantUtil.CLOSE);
			}
			break;
			
		case R.id.cb_fengshan:
			if (isChecked) {
				ControlUtils.control(ConstantUtil.Fan, ConstantUtil.CHANNEL_ALL, ConstantUtil.OPEN);
			}else {
				ControlUtils.control(ConstantUtil.Fan, ConstantUtil.CHANNEL_ALL, ConstantUtil.CLOSE);
			}
			break;
			
		case R.id.cb_baojingdeng:
			if (isChecked) {
				ControlUtils.control(ConstantUtil.WarningLight,ConstantUtil.CHANNEL_ALL, ConstantUtil.OPEN);
			} else {
				ControlUtils.control(ConstantUtil.WarningLight,ConstantUtil.CHANNEL_ALL, ConstantUtil.CLOSE);
			}
			break;
		case R.id.cb_mode_lvyou:
			if (isChecked) {
				if (modeWendu.isChecked()&& modeAnfang.isChecked() && modeZidingyi.isChecked()) {
					Toast.makeText(MainActivity.this, "����ͬʱ��������ģʽ",Toast.LENGTH_SHORT).show();
					modeLvyou.setChecked(false);
				} else {
					mHandler.post( lvyouRunnable );
	                Log.d("�߳̿���", "�����߳�����");
				}
            } else {
                if (lvyouRunnable != null){
                    mHandler.removeCallbacks( lvyouRunnable );
                	Log.d("����ģʽ", "�Ƴ��߳�");
                }
                ControlUtils.control(ConstantUtil.Lamp,ConstantUtil.CHANNEL_ALL, ConstantUtil.CLOSE);
				new Handler().postDelayed(new Runnable() {
					public void run() {
						ControlUtils.control(ConstantUtil.Curtain,ConstantUtil.CHANNEL_2, ConstantUtil.OPEN);
					}
				}, 50);
				if (Integer.parseInt(DeviceBean.getPM25()) > 75) {
					new Handler().postDelayed(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							ControlUtils.control(ConstantUtil.Fan,ConstantUtil.CHANNEL_ALL,ConstantUtil.CLOSE);
						}
					}, 100);
				}
				Log.d("����ģʽ", "�ر�����豸");
            }
			
			break;
		case R.id.cb_mode_wendu:
			
			if (isChecked) {
				if (modeLvyou.isChecked() && modeAnfang.isChecked() && modeZidingyi.isChecked()) {
					Toast.makeText(MainActivity.this, "����ͬʱ��������ģʽ",Toast.LENGTH_SHORT).show();
					modeWendu.setChecked(false);
				} else {
					mHandler.post( wenduRunnable );
	                Log.d("�߳̿���", "�¶��߳�����");
				}
            } else {
                if (wenduRunnable != null){
                    mHandler.removeCallbacks( wenduRunnable );
                	Log.d("�¶�ģʽ", "�Ƴ��߳�");
                }
                ControlUtils.control(ConstantUtil.INFRARED_1_SERVE, ConstantUtil.CHANNEL_1, ConstantUtil.OPEN);
				new Handler().postDelayed(new Runnable() {
					public void run() {
						ControlUtils.control(ConstantUtil.INFRARED_1_SERVE, "3", ConstantUtil.OPEN);
					}
				}, 50);
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						ControlUtils.control(ConstantUtil.INFRARED_1_SERVE, ConstantUtil.CHANNEL_2, ConstantUtil.OPEN);
					}
				}, 100);
				new Handler().postDelayed(new Runnable() {
					public void run() {
						ControlUtils.control(ConstantUtil.WarningLight,ConstantUtil.CHANNEL_ALL, ConstantUtil.CLOSE);
					}
				}, 150);
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						ControlUtils.control(ConstantUtil.Fan, ConstantUtil.CHANNEL_ALL, ConstantUtil.CLOSE);
					}
				}, 200);
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						ControlUtils.control(ConstantUtil.Lamp, ConstantUtil.CHANNEL_ALL, ConstantUtil.CLOSE);
					}
				}, 250);
				Log.d("�¶�ģʽ", "�ر�����豸");
            }
			
			break;
		case R.id.cb_mode_anfang:
			
			if (isChecked) {
				if (modeLvyou.isChecked() && modeWendu.isChecked() && modeZidingyi.isChecked()) {
					Toast.makeText(MainActivity.this, "����ͬʱ��������ģʽ",Toast.LENGTH_SHORT).show();
					modeAnfang.setChecked(false);
				} else {
					mHandler.post( anfangRunnable );
	                Log.d("�߳̿���", "�����߳�����");
				}
            } else {
                if (anfangRunnable != null){
                    mHandler.removeCallbacks( anfangRunnable );
                	Log.d("����ģʽ", "�Ƴ��߳�");
                }
                ControlUtils.control(ConstantUtil.WarningLight, ConstantUtil.CHANNEL_ALL, ConstantUtil.CLOSE);
				new Handler().postDelayed(new Runnable() {
					public void run() {
						ControlUtils.control(ConstantUtil.Lamp, ConstantUtil.CHANNEL_ALL, ConstantUtil.CLOSE);
					}
				}, 500);
				Log.d("����ģʽ", "�ر�����豸");
            }
			
			break;
		case R.id.cb_mode_zidingyi:
			
			if (isChecked) {
				if (modeLvyou.isChecked() && modeWendu.isChecked() && modeAnfang.isChecked()) {
					Toast.makeText(MainActivity.this, "����ͬʱ��������ģʽ",Toast.LENGTH_SHORT).show();
					modeZidingyi.setChecked(false);
				} else {
					mHandler.post( zidingyiRunnable );
	                Log.d("�߳̿���", "�Զ����߳�����");
				}
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
			
			break;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
        case R.id.bt_clkai:
            ControlUtils.control(ConstantUtil.Curtain, ConstantUtil.CHANNEL_3, ConstantUtil.OPEN );
            break;
        case R.id.bt_clting:
            ControlUtils.control(ConstantUtil.Curtain, ConstantUtil.CHANNEL_1, ConstantUtil.OPEN );
            break;
        case R.id.bt_clguan:
            ControlUtils.control(ConstantUtil.Curtain, ConstantUtil.CHANNEL_2, ConstantUtil.OPEN );
            break;
        case R.id.bt_kongtiao:
        	ControlUtils.control(ConstantUtil.INFRARED_1_SERVE, ConstantUtil.CHANNEL_2, ConstantUtil.OPEN);
			break;
			
		case R.id.bt_dvd:
			ControlUtils.control(ConstantUtil.INFRARED_1_SERVE, "3", ConstantUtil.OPEN);
			break;
			
		case R.id.bt_dianshi:
			ControlUtils.control(ConstantUtil.INFRARED_1_SERVE, ConstantUtil.CHANNEL_1, ConstantUtil.OPEN);
			break;
			
		case R.id.bt_shebeizhuangtai:
			startActivity(new Intent(MainActivity.this,ConnectActivity.class));
			break;
		}
	}
	
	Runnable lvyouRunnable = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Log.d("����ģʽ", "ִ�гɹ�");
			ControlUtils.control(ConstantUtil.Lamp,ConstantUtil.CHANNEL_ALL, ConstantUtil.CLOSE);
			new Handler().postDelayed(new Runnable() {
				public void run() {
					ControlUtils.control(ConstantUtil.Curtain,ConstantUtil.CHANNEL_3, ConstantUtil.OPEN);
				}
			}, 50);
			if (Integer.parseInt(DeviceBean.getPM25()) > 75) {
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						ControlUtils.control(ConstantUtil.Fan,ConstantUtil.CHANNEL_ALL,ConstantUtil.OPEN);
					}
				}, 100);
			}else {
				Log.d("����ģʽ", "δ�ﵽPm2.5����75����ִ�з��ȴ�");
			}
			mHandler.postDelayed(this, 2000);
            Log.d("����ģʽHandler����", "�ӳ����뿪ʼִ��");
		}
	};
	
	Runnable wenduRunnable = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Log.d("�¶�ģʽ", "��");
			ControlUtils.control(ConstantUtil.INFRARED_1_SERVE, ConstantUtil.CHANNEL_1, ConstantUtil.OPEN);
//			ControlUtils.control(ConstantUtil.INFRARED_1_SERVE, "3", ConstantUtil.OPEN);
//			ControlUtils.control(ConstantUtil.INFRARED_1_SERVE, ConstantUtil.CHANNEL_2, ConstantUtil.OPEN);
//			ControlUtils.control(ConstantUtil.WarningLight,ConstantUtil.CHANNEL_ALL, ConstantUtil.OPEN);
//			ControlUtils.control(ConstantUtil.Fan, ConstantUtil.CHANNEL_ALL, ConstantUtil.OPEN);
//			ControlUtils.control(ConstantUtil.Lamp, ConstantUtil.CHANNEL_ALL, ConstantUtil.OPEN);
			new Handler().postDelayed(new Runnable() {
				public void run() {
					ControlUtils.control(ConstantUtil.INFRARED_1_SERVE, "3", ConstantUtil.OPEN);
				}
			}, 50);
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					ControlUtils.control(ConstantUtil.INFRARED_1_SERVE, ConstantUtil.CHANNEL_2, ConstantUtil.OPEN);
				}
			}, 100);
			new Handler().postDelayed(new Runnable() {
				public void run() {
					ControlUtils.control(ConstantUtil.WarningLight,ConstantUtil.CHANNEL_ALL, ConstantUtil.OPEN);
				}
			}, 150);
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					ControlUtils.control(ConstantUtil.Fan, ConstantUtil.CHANNEL_ALL, ConstantUtil.OPEN);
				}
			}, 200);
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					ControlUtils.control(ConstantUtil.Lamp, ConstantUtil.CHANNEL_ALL, ConstantUtil.OPEN);
				}
			}, 250);
			
			mHandler.postDelayed(this, 5000);
            Log.d("�¶�ģʽHandler����", "�ӳ����뿪ʼִ��");
		}
	};
	
	Runnable anfangRunnable = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (DeviceBean.getStateHumanInfrared().equals( ConstantUtil.OPEN )) {
				Log.d("����ģʽ", "����-��");
				ControlUtils.control(ConstantUtil.WarningLight, ConstantUtil.CHANNEL_ALL, ConstantUtil.OPEN);
				new Handler().postDelayed(new Runnable() {
					public void run() {
						ControlUtils.control(ConstantUtil.Lamp, ConstantUtil.CHANNEL_ALL, ConstantUtil.OPEN);
					}
				}, 50);
			}else{
				Log.d("����ģʽ", "����-�ر�");
				ControlUtils.control(ConstantUtil.WarningLight, ConstantUtil.CHANNEL_ALL, ConstantUtil.CLOSE);
				new Handler().postDelayed(new Runnable() {
					public void run() {
						ControlUtils.control(ConstantUtil.Lamp, ConstantUtil.CHANNEL_ALL, ConstantUtil.CLOSE);
					}
				}, 50);
			}
			mHandler.postDelayed(this, 2000);
            Log.d("����ģʽHandler����", "�ӳ����뿪ʼִ��");
		}
	};
	
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
				Toast.makeText(MainActivity.this, "����Ϊ��", Toast.LENGTH_SHORT).show();
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
	
	private void initSelect(){
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
	
	@Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacks( lvyouRunnable );
            mHandler.removeCallbacks( wenduRunnable );
            mHandler.removeCallbacks( anfangRunnable );
        }
    }
	
}
