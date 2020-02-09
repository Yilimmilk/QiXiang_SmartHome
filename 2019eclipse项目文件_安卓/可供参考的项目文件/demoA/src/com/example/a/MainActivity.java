package com.example.a;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SlidingDrawer;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bizideal.smarthome.socket.ConstantUtil;
import com.bizideal.smarthome.socket.ControlUtils;
import com.bizideal.smarthome.socket.DataCallback;
import com.bizideal.smarthome.socket.DeviceBean;
import com.bizideal.smarthome.socket.LoginCallback;
import com.bizideal.smarthome.socket.SocketClient;

public class MainActivity extends Activity implements OnClickListener,OnCheckedChangeListener {
	private TextView mTempTv, mHumidityTv, mGasTv, mIlluminationTv, mPm25Tv, mPressureTv, mSmokeTv, mCo2Tv, mStateHumanInfraredTv;
    private CheckBox mLampCb, mDoorCb, mWindSpeedCb, mAlarmCb,lvmodel,wdmodel,anfangmodel,zidingyimodel;
    private EditText fazhi_et;
    private Button KT,DVD,TV,kai,guan,ting,zhuangtai;
    
    private Spinner sp1,sp2,sp3;
    private String[] sp1values = new String[]{"�¶�","����"};
    private String[] sp2values = new String[]{">","<="};
    private String[] sp3values = new String[]{"���ȿ�","��ƿ�"};
	private ArrayAdapter<String> adapter1;
	private ArrayAdapter<String> adapter2;
	private ArrayAdapter<String> adapter3;
	private String sp1value,sp2value,sp3value;  //��¼��ǰѡ���ֵ
	
	Runnable r_lvyou,r_wendu,r_anfang,r_ziding; //�ĸ�ģʽ���߳�
	Runnable r_menjin; //�ر��Ž��߳�
	boolean warLightState,chuanState,fanState,lampState,tvState,dvdState,ktState;  //�����豸��״̬
	
	
	//����ѧϰƵ��
	String DVD_chanel = "8";
	String TV_chanel = "5";
	String KT_chanel = "1";
	
	
	
	
	
	Handler handler = new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		 getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,  
                 WindowManager.LayoutParams. FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		
		//�ؼ���ʼ��
		initViews();
		
//		������ʼ��
		initVar();
		
		
		
		
	}

	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//����socket����
        SocketClient.getInstance().creatConnect();
        //���ӻص�
        SocketClient.getInstance().login( new LoginCallback() {
            @Override
            public void onEvent(final String status) {
                runOnUiThread( new Runnable() {
                    @Override
                    public void run() {
                        if (status.equals( ConstantUtil.SUCCESS )) {
                        	 Toast.makeText( MainActivity.this, "�ɹ���", Toast.LENGTH_SHORT ).show();
                        } else {
                            Toast.makeText( MainActivity.this, "ʧ�ܣ�", Toast.LENGTH_SHORT ).show();
                        }
                    }
                } );
            }
        } );
		
        initData();
	}
	private void initVar() {
		// TODO Auto-generated method stub
		warLightState = false;
		fanState =false;
		lampState =false;
		chuanState =false;
		tvState = false;
		dvdState = false;
		ktState = false;
		
		r_lvyou = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
//				�ж����״̬ �������״̬��ر�  ͬʱ�޸�״̬
				if(lampState){
					 openOrCloseLamp(true);
					 try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
//				�������״̬�ǹر� ��������  ͬʱ�޸�״̬
				if(!chuanState){
					openOrCloseChuangLian(true);
					 try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
				float ff =0;
				try {
					ff= Float.parseFloat(mPm25Tv.getText().toString());
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					ff =0;
				}				
				
				//�ж�pm�Ƿ����75 ������� ��������
				if(ff>75){
					 openOrCloseFan(true);
				}
				
				handler.postDelayed(r_lvyou, 1000);
			}
		};
		
		r_wendu = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
//				�򿪵���				
				if(!tvState){
					openOrCloseTV(!tvState);
				}
				
				 try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//				 ��DVD
				 if(!dvdState){
					 openOrCloseDVD(!dvdState);
				 }
	             
	             try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	             
//	           	  �򿪿յ�
	             if(!ktState){
	            	openOrCloseKT(!ktState);
	             }
	        	
//				��������
	        	 if(!warLightState){
	        		 openOrCloseWarLight(true);
	        		 try {
	 					Thread.sleep(1000);
	 				} catch (InterruptedException e) {
	 					// TODO Auto-generated catch block
	 					e.printStackTrace();
	 				}
	        	 }
//	        	 ������
	        	 if(!fanState){
	        		 openOrCloseFan(true);
	        		 try {
	 					Thread.sleep(1000);
	 				} catch (InterruptedException e) {
	 					// TODO Auto-generated catch block
	 					e.printStackTrace();
	 				}
	        	 }
//	        	 �����
	        	 if(!lampState){
	        		 openOrCloseLamp(true);
	        		 try {
	 					Thread.sleep(1000);
	 				} catch (InterruptedException e) {
	 					// TODO Auto-generated catch block
	 					e.printStackTrace();
	 				}
	        	 }
			}
		};
		
		
		r_anfang = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//���������ʾ����  
				if(mStateHumanInfraredTv.getText().equals("����")){
//					�жϱ�����״̬
					if(!warLightState){
						openOrCloseWarLight(true);
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					if(!lampState){
						openOrCloseLamp(true);
						
					}
					
				}
				
				handler.postDelayed(r_anfang, 1000);
			}
		};
		
		
		
		r_ziding = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				String value = fazhi_et.getText().toString();
				if(value.isEmpty()){
					Toast.makeText(MainActivity.this, "��������ȷ����ֵ", 0).show();
					zidingyimodel.setChecked(false);
					return;
					
					
				}
				
				float fazhi = Float.parseFloat(value);
				float dangqianzhi = 0;
				
				
				
				
				if(sp1value.equals("�¶�")){ //��һ��ѡ��� Ϊ�¶�
					String ss = mTempTv.getText().toString();
					if(ss.isEmpty()){
						dangqianzhi = 0;
					}else{
						dangqianzhi = Float.parseFloat(ss);
					}
					if(sp2value.equals("<")){ //�ڶ���������Ϊ <
						
						if(sp3value.equals(sp3values[0])){ //���ȿ�
							if(dangqianzhi<fazhi){
								if(!fanState){
									//������
									openOrCloseFan(true);
								}
							}
						}else{ //��ƿ�
							if(dangqianzhi<fazhi){
								if(!fanState){
									//�����
									openOrCloseLamp(true);
								}
							}
						}
						
						
					}else{//�ڶ���������Ϊ >
						if(sp3value.equals(sp3values[0])){ //���ȿ�
							if(dangqianzhi>=fazhi){
								if(!fanState){
									//������
									openOrCloseFan(true);
								}
							}
						}else{ //��ƿ�
							if(dangqianzhi>=fazhi){
								if(!fanState){
									//�����
									openOrCloseLamp(true);
								}
							}
						}
					}
					
					
				}else { //��һ��ѡ��� Ϊ����
					
					String ss = mIlluminationTv.getText().toString();
					if(ss.isEmpty()){
						dangqianzhi = 0;
					}else{
						dangqianzhi = Float.parseFloat(ss);
					}
					if(sp2value.equals("<")){ //�ڶ���������Ϊ <
						
						if(sp3value.equals(sp3values[0])){ //���ȿ�
							if(dangqianzhi<fazhi){
								if(!fanState){
									//������
									openOrCloseFan(true);
								}
							}
						}else{ //��ƿ�
							if(dangqianzhi<fazhi){
								if(!fanState){
									//�����
									openOrCloseLamp(true);
								}
							}
						}
						
						
					}else{//�ڶ���������Ϊ >
						if(sp3value.equals(sp3values[0])){ //���ȿ�
							if(dangqianzhi>=fazhi){
								if(!fanState){
									//������
									openOrCloseFan(true);
								}
							}
						}else{ //��ƿ�
							if(dangqianzhi>=fazhi){
								if(!fanState){
									//�����
									openOrCloseLamp(true);
								}
							}
						}
					}	
				
				}	
				handler.postDelayed(r_ziding, 1000);
			}
			
		};
		
		
		
		r_menjin = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				mDoorCb.setBackgroundResource(R.drawable.mejin);
			}
		};
		
		
	}

	private void initViews() {
		// TODO Auto-generated method stub
		  	mTempTv = (TextView) findViewById( R.id.wendu );//�¶�
	        mHumidityTv = (TextView) findViewById( R.id.shidu );//ʪ��
	        mGasTv = (TextView) findViewById( R.id.ranqi );//ȼ��
	        mIlluminationTv = (TextView) findViewById( R.id.guangzhao );//���ն�
	        mPm25Tv = (TextView) findViewById( R.id.PM2_5 );//Pm2.5
	        mPressureTv = (TextView) findViewById( R.id.qiya );//��ѹ
	        mSmokeTv = (TextView) findViewById( R.id.yanwu );//����
	        mCo2Tv = (TextView) findViewById( R.id.co2 );//C02
	        mStateHumanInfraredTv = (TextView) findViewById( R.id.renti);//�������
	        
	        
	        KT = (Button) findViewById(R.id.kongtiao);
	        KT.setOnClickListener(this);
	        DVD = (Button) findViewById(R.id.DVD);
	        DVD.setOnClickListener(this);
	        TV = (Button) findViewById(R.id.dianshi);
	        TV.setOnClickListener(this);
	        kai = (Button) findViewById(R.id.kai);
	        kai.setOnClickListener(this);
	        guan = (Button) findViewById(R.id.guan);
	        guan.setOnClickListener(this);
	        ting = (Button) findViewById(R.id.ting);
	        ting.setOnClickListener(this);
	        zhuangtai = (Button) findViewById(R.id.shebeizhuangtai);
	        zhuangtai.setOnClickListener(this);
	        fazhi_et = (EditText) findViewById(R.id.fazhi);
	        mLampCb = (CheckBox) findViewById( R.id.shedeng );//���
	        mLampCb.setOnCheckedChangeListener( this );
	        mDoorCb = (CheckBox) findViewById( R.id.menjin );//�Ž�
	        mDoorCb.setOnCheckedChangeListener( this );
	        mWindSpeedCb = (CheckBox) findViewById( R.id.fengshan );//����
	        mWindSpeedCb.setOnCheckedChangeListener( this );
	        mAlarmCb = (CheckBox) findViewById( R.id.baojingdeng );//������
	        mAlarmCb.setOnCheckedChangeListener( this );
	        
	        lvmodel= (CheckBox) findViewById( R.id.lvyoumodel );  //����ģʽ
	        lvmodel.setOnCheckedChangeListener( this );
	        wdmodel = (CheckBox) findViewById( R.id.wendumodel ); //�¶�ģʽ
	        wdmodel.setOnCheckedChangeListener( this ); 
	        anfangmodel = (CheckBox) findViewById( R.id.anfangmodel ); //����ģʽ
	        anfangmodel.setOnCheckedChangeListener( this );
	        zidingyimodel = (CheckBox) findViewById( R.id.zidingyimodel ); //�Զ���ģʽ
	        zidingyimodel.setOnCheckedChangeListener( this );
	        
	        sp1 = (Spinner) findViewById(R.id.spiner1);
	        sp2 = (Spinner) findViewById(R.id.spiner2);
	        sp3 = (Spinner) findViewById(R.id.spiner3);
	        adapter1 = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item,sp1values);
	        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        sp1.setAdapter(adapter1);
	        adapter2 = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item,sp2values);
	        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        sp2.setAdapter(adapter2);
	        adapter3 = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item,sp3values);
	        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        sp3.setAdapter(adapter3);
	        
	        
	        
	        sp1.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					sp1value =sp1values[arg2];
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});
	        sp2.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					sp2value =sp2values[arg2];
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});
	        sp3.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					sp3value =sp3values[arg2];
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});
	        
	}

	 private void initData() {
	        ControlUtils.getData();
	        SocketClient.getInstance().getData( new DataCallback<DeviceBean>() {
	            @Override
	            public void onResult(final DeviceBean bean) {
	                runOnUiThread( new Runnable() {
	                    @Override
	                    public void run() {
	                        if (!bean.getTemperature().isEmpty()){
	                        	mTempTv.setText(bean.getTemperature());
	                        }
	                        if (!TextUtils.isEmpty( bean.getHumidity() )){
	                            mHumidityTv.setText( bean.getHumidity() );
	                        }
	                        if (!TextUtils.isEmpty( bean.getGas() )){
	                            mGasTv.setText( bean.getGas() );
	                        }
	                        if ( !bean.getIllumination().isEmpty()){
	                        	mIlluminationTv.setText( bean.getIllumination() );
	                        }
	                        if (!TextUtils.isEmpty( bean.getPM25() )){
	                            mPm25Tv.setText( bean.getPM25() );
	                        }
	                        if (!TextUtils.isEmpty( bean.getSmoke() )){
	                            mSmokeTv.setText( bean.getSmoke() );
	                        }
	                        if (!TextUtils.isEmpty( bean.getCo2() )){
	                            mCo2Tv.setText( bean.getCo2() );
	                        }
	                        if (!TextUtils.isEmpty( bean.getAirPressure() )){
	                            mPressureTv.setText( bean.getAirPressure() );
	                        }
	                        if (!TextUtils.isEmpty( bean.getStateHumanInfrared() ) && bean.getStateHumanInfrared().equals( ConstantUtil.CLOSE )){
	                            mStateHumanInfraredTv.setText( "����" );
	                        }else{
	                            mStateHumanInfraredTv.setText( "����" );
	                        }
	                     
	                    }
	                } );
	            }
	        } );
	    }

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		switch (buttonView.getId()) {
		case R.id.shedeng://���
            openOrCloseLamp(isChecked);
            break;
        case R.id.menjin://�Ž�
            ControlUtils.control( ConstantUtil.RFID_Open_Door, ConstantUtil.CHANNEL_1, ConstantUtil.OPEN );
            mDoorCb.setBackgroundResource(R.drawable.menjinkai);
            handler.postDelayed(r_menjin, 1500);
            break;
        case R.id.fengshan://����
          
              openOrCloseFan(isChecked);
            
        break;
        case R.id.baojingdeng://������
   
              openOrCloseWarLight(isChecked);
           
            break;
        case R.id.lvyoumodel:
        	if(isChecked){        		
        		//�ж�����ģʽ�Ƿ��� ����������ʾ 
        		
        		if(anfangmodel.isChecked()||wdmodel.isChecked()||zidingyimodel.isChecked()){
        			Toast.makeText(MainActivity.this, "���ȹر�����ģʽ", 0).show();
        			lvmodel.setChecked(false);
        			return ;
        		}
        		
        		handler.post(r_lvyou);
        		
        	}else{
        		handler.removeCallbacks(r_lvyou);
        	}
        	break;
        case R.id.wendumodel:
        	if(isChecked){        		
        		//�ж�����ģʽ�Ƿ��� ����������ʾ 
        		
        		if(lvmodel.isChecked()||anfangmodel.isChecked()||zidingyimodel.isChecked()){
        			Toast.makeText(MainActivity.this, "���ȹر�����ģʽ", 0).show();
        			wdmodel.setChecked(false);
        			return ;
        		}
        		
        		handler.post(r_wendu);
        		
        	}else{
        		handler.removeCallbacks(r_wendu);
        	}
        	break;
        case R.id.anfangmodel:
        	if(isChecked){        		
        		//�ж�����ģʽ�Ƿ��� ����������ʾ 
        		
        		if(lvmodel.isChecked()||wdmodel.isChecked()||zidingyimodel.isChecked()){
        			Toast.makeText(MainActivity.this, "���ȹر�����ģʽ", 0).show();
        			anfangmodel.setChecked(false);
        			return ;
        		}
        		
        		handler.post(r_anfang);
        		
        	}else{
        		handler.removeCallbacks(r_anfang);
        	}
        	break;
        case R.id.zidingyimodel:
        	if(isChecked){        		
        		//�ж�����ģʽ�Ƿ��� ����������ʾ 
        		
        		if(lvmodel.isChecked()||wdmodel.isChecked()||anfangmodel.isChecked()){
        			Toast.makeText(MainActivity.this, "���ȹر�����ģʽ", 0).show();
        			zidingyimodel.setChecked(false);
        			return ;
        		}
        		
        		handler.post(r_ziding);
        		
        	}else{
        		handler.removeCallbacks(r_ziding);
        	}
        	break;
		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		 case R.id.kai://������
			 openOrCloseChuangLian(true);
             break;
         case R.id.ting://����ͣ
             ControlUtils.control(ConstantUtil.Curtain, ConstantUtil.CHANNEL_2, ConstantUtil.OPEN );
             break;
         case R.id.guan://������
             //��
             openOrCloseChuangLian(false);
             break;
         case R.id.kongtiao://�յ�
        	 openOrCloseKT(!ktState);
             break;
         case R.id.DVD:// dvd
        	 openOrCloseDVD(!dvdState);
             break;
         case R.id.dianshi: //����
        	 openOrCloseTV(!tvState);
         case R.id.shebeizhuangtai: //�豸״̬
        	 startActivity(new Intent(MainActivity.this, StateActivity.class));
             break;
         
		}
	}
	
//==========  �豸�������رշ���==================
//	�������  ͬʱ�޸�״̬
	void openOrCloseLamp(boolean is){
		 ControlUtils.control( ConstantUtil.Lamp, ConstantUtil.CHANNEL_ALL,is?ConstantUtil.OPEN:ConstantUtil.CLOSE );
		 lampState = is;		 
		 mLampCb.setChecked(is);
		 if(is){
			 mLampCb.setBackgroundResource(R.drawable.shedengkai); 
		 }else{
			 mLampCb.setBackgroundResource(R.drawable.shedengkai); 
		 }
		 
	}
//	���ر����� ͬʱ�޸�״̬
	void openOrCloseWarLight(boolean is){
		 ControlUtils.control( ConstantUtil.WarningLight, ConstantUtil.CHANNEL_ALL,is?ConstantUtil.OPEN:ConstantUtil.CLOSE );
		 warLightState = is;		 
		 mAlarmCb.setChecked(is);
		 if(is){
			 mAlarmCb.setBackgroundResource(R.drawable.baojingdengkai); 
		 }else{
			 mAlarmCb.setBackgroundResource(R.drawable.baojingdeng); 
		 }
		 
	}
//	���ط���  ͬʱ�޸�״̬
	void openOrCloseFan(boolean is){
		 ControlUtils.control( ConstantUtil.Fan, ConstantUtil.CHANNEL_ALL,is?ConstantUtil.OPEN:ConstantUtil.CLOSE );
		 fanState = is;		 
		 mWindSpeedCb.setChecked(is);
		 if (is) {
			 mWindSpeedCb.setBackgroundResource(R.drawable.fengshankai);
		} else {
			mWindSpeedCb.setBackgroundResource(R.drawable.fengshan);
		}
		
		 
		 
		 
	}
//	���ش���
	void openOrCloseChuangLian(boolean is){
		 ControlUtils.control( ConstantUtil.WarningLight, is?ConstantUtil.CHANNEL_1:ConstantUtil.CHANNEL_3,ConstantUtil.OPEN );
		 chuanState = is;		 
		
		 
	}
//	���ص���
	void openOrCloseTV(boolean is){
		ControlUtils.control( ConstantUtil.INFRARED_1_SERVE, TV_chanel, ConstantUtil.OPEN );
		 tvState = is;		 
		 
		 
	}
//	����dvd
	void openOrCloseDVD(boolean is){
		ControlUtils.control( ConstantUtil.INFRARED_1_SERVE, DVD_chanel, ConstantUtil.OPEN );
		 dvdState = is;		 
		
		 
	}
//	���ؿյ�
	void openOrCloseKT(boolean is){
		 ControlUtils.control( ConstantUtil.WarningLight, KT_chanel,is?ConstantUtil.OPEN:ConstantUtil.CLOSE );
		 ktState = is;	 
		 
	}
	
	
}


