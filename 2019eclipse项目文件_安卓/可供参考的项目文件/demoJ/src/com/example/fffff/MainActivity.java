package com.example.fffff;

import java.util.ArrayList;
import java.util.List;

import com.bizideal.smarthome.socket.ConstantUtil;
import com.bizideal.smarthome.socket.ControlUtils;
import android.os.Bundle;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class MainActivity extends FragmentActivity {
     ViewPager vp ;
     VpAdapter adapter;
     List<Fragment> list ;
     
     
   //�����豸��״̬
 	 boolean warLightState=false,chuanState=false,fanState=false,lampState=false,tvState=false,dvdState=false,ktState;  
 	 float ranqi_value,yanwu_value;
 	 boolean hasman;
 	//����ѧϰƵ��
 	String DVD_chanel = "2";
 	String TV_chanel = "3";
 	String KT_chanel = "1";
 	
 	Helper helper;
	SQLiteDatabase db;
 	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initVar();
        helper = new Helper(this);
        vp =  (ViewPager) findViewById(R.id.vp);
        list = new ArrayList<Fragment>();
        BaseFragment base = new BaseFragment();
        MoShiFragment moShi = new MoShiFragment();
        list.add(base);
        list.add(moShi);
        adapter = new VpAdapter(getSupportFragmentManager(),list);
        vp.setAdapter(adapter);
    }

	public void initVar() {
		// TODO Auto-generated method stub
		warLightState = false;
		fanState =false;
		lampState =false;
		chuanState =false;
		tvState = false;
		dvdState = false;
		ktState = false;
	}
	
	
	//==========  �豸�������رշ���==================
//	�������  ͬʱ�޸�״̬
	void openOrCloseLamp(boolean is){
		 ControlUtils.control( ConstantUtil.Lamp, ConstantUtil.CHANNEL_ALL,is?ConstantUtil.OPEN:ConstantUtil.CLOSE );
		 lampState = is;
	}
//	���ر����� ͬʱ�޸�״̬
	void openOrCloseWarLight(boolean is){
		 ControlUtils.control( ConstantUtil.WarningLight, ConstantUtil.CHANNEL_ALL,is?ConstantUtil.OPEN:ConstantUtil.CLOSE );
		 warLightState = is;
	}
//	���ط���  ͬʱ�޸�״̬
	void openOrCloseFan(boolean is){
		 ControlUtils.control( ConstantUtil.Fan, ConstantUtil.CHANNEL_ALL,is?ConstantUtil.OPEN:ConstantUtil.CLOSE );
		 fanState = is;	
	}
//	���ش���
	 void openOrCloseChuangLian(int  x){
		
		switch(x){
		case 1:
			chuanState = true;
			break;
		case 2:
			break;
		case 3:
			chuanState = false;
			break;
		}
		 		 
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
