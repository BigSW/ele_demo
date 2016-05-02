package com.chianmobile.assist;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.chinamobile.service.ResetPswService;
import com.chinamobile.util.Constant;
import com.chinamobile.util.MD5;
import com.chinamobile.util.Util;

public class ResetPswActivity extends Activity implements OnClickListener{
	private ImageButton rebackbtn;
	private EditText ypswet,xpswetone,xpswettwo;
	private TextView submitxpsw;
	private String nepassword = "0";
	
	private BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context arg0, Intent intent) {
			String result = intent.getExtras().getString("result");
			unregisterReceiver(receiver);
			Log.i("resetpsw result TAG:", result);
			if (Util.LOGIN_SUCESS.equals(result)) {
				Toast.makeText(getApplicationContext(), "修改成功！",
						Toast.LENGTH_SHORT).show();
				Constant.operpsw = nepassword;
				finish();
				overridePendingTransition(R.anim.in_from_left,R.anim.out_of_right); 
			}else if (Util.NO_PERMISSIOIN.equals(result)) {
				Toast.makeText(getApplicationContext(), "你好，请用手机”《app名称》“客户端正确访问",
						Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(getApplicationContext(), "密码修改失败，请核实手机是否连网或联系业支解决！",
						Toast.LENGTH_LONG).show();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.resetpsd);//rebacksetbtn
		rebackbtn = (ImageButton)findViewById(R.id.rebacksetbtn);
		ypswet = (EditText)findViewById(R.id.ypswet);
		xpswetone = (EditText)findViewById(R.id.xpswetone);
		xpswettwo = (EditText)findViewById(R.id.xpswettwo);
		submitxpsw = (TextView)findViewById(R.id.submitxpsw);
		
		rebackbtn.setOnClickListener(this);
		submitxpsw.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.rebacksetbtn:
			finish();
			overridePendingTransition(R.anim.in_from_left,R.anim.out_of_right); 
			break;
		case R.id.submitxpsw:
			String lastpsw = ypswet.getText().toString();
			if(lastpsw!=null){
				if(!"no".equals(Constant.operpsw) && MD5.getMD5(lastpsw).equals(Constant.operpsw)) {
					String newpsw = xpswetone.getText().toString();
					String newpsw2 = xpswettwo.getText().toString();
					if (newpsw!=null && newpsw2!=null && !newpsw.trim().equals("") && !newpsw2.trim().equals("")) {
						if(newpsw.equals(newpsw2)){
							IntentFilter filter = new IntentFilter(
									"com.chinamobile.service.ResetPswService");
							registerReceiver(receiver, filter);
							
							Intent loginService = new Intent(getApplicationContext(),
									ResetPswService.class);
							loginService.putExtra("oldpsw", MD5.getMD5(lastpsw));
							nepassword = MD5.getMD5(newpsw);
							loginService.putExtra("newpsw",  MD5.getMD5(newpsw));
							startService(loginService);
						}else {
							Toast.makeText(getApplicationContext(), "两次新密码输入不一致，请重新输入！", Toast.LENGTH_SHORT).show();
						}
					}else {
						Toast.makeText(getApplicationContext(), "新密码不能为空，请重新输入！", Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(getApplicationContext(), "原密码错误，请重新输入！", Toast.LENGTH_SHORT).show();
				}
			}
			break;
		default:
			break;
		}
	}
	

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
		overridePendingTransition(R.anim.in_from_left,R.anim.out_of_right); 
	}
}
