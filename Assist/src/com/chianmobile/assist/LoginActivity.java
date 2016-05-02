package com.chianmobile.assist;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import android.graphics.Color;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chinamobile.service.LoginService;

import com.chinamobile.util.Constant;
import com.chinamobile.util.MD5;
import com.chinamobile.util.Util;

public class LoginActivity extends Activity implements OnClickListener {
	private EditText et_name, et_pass;
	private Button mLoginButton, mLoginError, mRegister;
	private Button bt_username_clear, bt_pwd_clear, bt_pwd_eye;
	private TextWatcher username_watcher, password_watcher;
	private TextView sometip;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		sometip = (TextView) findViewById(R.id.sometip);
		sometip.setText("");
		et_name = (EditText) findViewById(R.id.username);
		et_pass = (EditText) findViewById(R.id.password);

		bt_username_clear = (Button) findViewById(R.id.bt_username_clear);
		bt_pwd_clear = (Button) findViewById(R.id.bt_pwd_clear);
		bt_pwd_eye = (Button) findViewById(R.id.bt_pwd_eye);
		bt_username_clear.setOnClickListener(this);
		bt_pwd_clear.setOnClickListener(this);
		bt_pwd_eye.setOnClickListener(this);
		initWatcher();
		et_name.addTextChangedListener(username_watcher);
		et_pass.addTextChangedListener(password_watcher);

		mLoginButton = (Button) findViewById(R.id.login);
		/*
		 * mLoginError = (Button) findViewById(R.id.login_error); mRegister =
		 * (Button) findViewById(R.id.register);
		 */
		mLoginButton.setOnClickListener(this);
		/*mLoginError.setOnClickListener(this);
		mRegister.setOnClickListener(this);*/

		TelephonyManager mTelephonyMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String imei = mTelephonyMgr.getDeviceId();
		Constant.loginkey =imei;// MD5.getMD5(imei.substring(0, 14));
	}

	private void initWatcher() {
		username_watcher = new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void afterTextChanged(Editable s) {
				et_pass.setText("");
				if (s.toString().length() > 0) {
					bt_username_clear.setVisibility(View.VISIBLE);
				} else {
					bt_username_clear.setVisibility(View.INVISIBLE);
				}
			}
		};
		password_watcher = new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void afterTextChanged(Editable s) {
				if (s.toString().length() > 0) {
					bt_pwd_clear.setVisibility(View.VISIBLE);
				} else {
					bt_pwd_clear.setVisibility(View.INVISIBLE);
				}
			}
		};
	}

	private BroadcastReceiver receiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			String result = intent.getExtras().getString("result");
			Log.i(">>>>>>>>>>>>", "+++"+result);
			if (sometip != null) {
				sometip.setText("");
			}
			//unregisterReceiver(receiver);
			if (Util.LOGIN_SUCESS.equals(result)) {
				Toast.makeText(getApplicationContext(), "登陆成功",
						Toast.LENGTH_SHORT).show();
				Constant.operid=et_name.getText().toString();
				Intent mainIntent = new Intent(getApplicationContext(),
						MainActivity.class);
				startActivity(mainIntent);
				sometip.setText("");
				sometip.setTextColor(Color.RED);
				
				
			} else if (Util.LOGIN_FILED.equals(result)) {
				sometip.setTextColor(Color.RED);
				sometip.setText("登陆失败，请重新登陆！");
			} else if (Util.NO_PERMISSIOIN.equals(result)) {
				sometip.setTextColor(Color.RED);
				sometip.setText("请使用匹配的手机进行登录，登录失败");
			} 
			else if (Util.NOT_EXIT.equals(result)) {
				sometip.setTextColor(Color.RED);
				sometip.setText("该用户名不存在，登陆失败，请重新登陆！");
			}
			else if (Util.PSW_ERROR.equals(result)) {
				sometip.setTextColor(Color.RED);
				sometip.setText("密码错误，登陆失败，请重新登陆！");
			}
			else {
				sometip.setTextColor(Color.RED);
				sometip.setText("账号或密码错误，登陆失败，请重新登陆！");
				Toast.makeText(getApplicationContext(), "登陆失败",
						Toast.LENGTH_LONG).show();
			}
		}
	};

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.login:
			if (sometip != null) {
				sometip.setText("");
			}
			final String userName = et_name.getText().toString();
			final String password = et_pass.getText().toString();
			if (userName != null && !"".equals(userName.trim())) {
				if (password != null && !"".equals(password)) {

					IntentFilter filter = new IntentFilter(
							"com.chinamobile.service.LoginService");
					registerReceiver(receiver, filter);

					//String psw_md5 = MD5.getMD5(password);
					Constant.operacount = userName;
					Constant.operpsw=password;
					//Constant.operpsw = psw_md5;

					sometip.setText("请稍等，正在拼命登陆......");
					sometip.setTextColor(Color.GREEN);
					Intent loginService = new Intent(getApplicationContext(),
							LoginService.class);
					loginService.putExtra("userName", userName);
					loginService.putExtra("password", password);
					startService(loginService);
				} else {
					sometip.setTextColor(Color.RED);
					sometip.setText("密码不能为空！");
					Toast.makeText(getApplicationContext(), "密码不能为空！",
							Toast.LENGTH_LONG).show();
				}
			} else {
				sometip.setTextColor(Color.RED);
				sometip.setText("用户名不能为空！");
				Toast.makeText(getApplicationContext(), "用户名不能为空！",
						Toast.LENGTH_LONG).show();
			}
			break;
		/*
		 * case R.id.login_error:
		 * 
		 * break; case R.id.register: break;
		 */
		case R.id.bt_username_clear:
			et_name.setText("");
			et_pass.setText("");
			break;
		case R.id.bt_pwd_clear:
			et_pass.setText("");
			break;
		case R.id.bt_pwd_eye:
			if (et_pass.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
				bt_pwd_eye.setBackgroundResource(R.drawable.button_eye_s);
				et_pass.setInputType(InputType.TYPE_CLASS_TEXT
						| InputType.TYPE_TEXT_VARIATION_NORMAL);
			} else {
				bt_pwd_eye.setBackgroundResource(R.drawable.button_eye_n);
				et_pass.setInputType(InputType.TYPE_CLASS_TEXT
						| InputType.TYPE_TEXT_VARIATION_PASSWORD);
			}
			et_pass.setSelection(et_pass.getText().toString().length());
			break;
		default:
			break;
		}
	}

}
