package com.example.smartdemo;

import com.bizideal.smarthome.socket.ConstantUtil;
import com.bizideal.smarthome.socket.ControlUtils;
import com.bizideal.smarthome.socket.LoginCallback;
import com.bizideal.smarthome.socket.SocketClient;
import com.bizideal.smarthome.socket.Utils.SpUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
	
	 private EditText mAccountEt, mPasswordEt, mIpEt;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		 initViews();
	}
	
	
	 private void initViews(){
	        TextView mLoginTv = (TextView) findViewById( R.id.login_tv );
	        mLoginTv.setOnClickListener( this );
	        mAccountEt = (EditText) findViewById( R.id.username_et );//�˺�
	        mPasswordEt = (EditText) findViewById( R.id.password_et );//����
	        mIpEt = (EditText) findViewById( R.id.ip_et );//����
	        mAccountEt.setText( SpUtils.getValue( this, "Account", "" ) );
	        mPasswordEt.setText( SpUtils.getValue( this, "Password", "" ) );
	        mIpEt.setText( SpUtils.getValue( this, "Ip", "" ) );
	    }
	 
	 @Override
	    public void onClick(View v) {
	        switch (v.getId()) {
	            case R.id.login_tv:
	                if (TextUtils.isEmpty( mAccountEt.getText().toString() )) {
	                    Toast.makeText( this, "�˺Ų���Ϊ�գ�", Toast.LENGTH_SHORT ).show();
	                    return;
	                }
	                if (TextUtils.isEmpty( mPasswordEt.getText().toString() )) {
	                    Toast.makeText( this, "���벻��Ϊ�գ�", Toast.LENGTH_SHORT ).show();
	                    return;
	                }
	                if (TextUtils.isEmpty( mIpEt.getText().toString() )) {
	                    Toast.makeText( this, "Ip����Ϊ�գ�", Toast.LENGTH_SHORT ).show();
	                    return;
	                }
	                if (!mAccountEt.getText().toString().equals( "bizideal" ) || !mPasswordEt.getText().toString().equals( "123456" )) {
	                    Toast.makeText( this, "�˺Ż��������", Toast.LENGTH_SHORT ).show();
	                    return;
	                }
	                SpUtils.putValue( this, "Account", mAccountEt.getText().toString() );
	                SpUtils.putValue( this, "Password", mPasswordEt.getText().toString() );
	                SpUtils.putValue( this, "Ip", mIpEt.getText().toString() );
	                //�����˺�����
	                ControlUtils.setUser( mAccountEt.getText().toString(), mPasswordEt.getText().toString(), mIpEt.getText().toString() );
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
	                                    startActivity( new Intent( MainActivity.this, MainActivity.class ) );
	                                    finish();
	                                } else {
	                                    Toast.makeText( MainActivity.this, "ʧ�ܣ�", Toast.LENGTH_SHORT ).show();
	                                }
	                            }
	                        } );
	                    }
	                } );
	                break;
	        }
	    }
}
