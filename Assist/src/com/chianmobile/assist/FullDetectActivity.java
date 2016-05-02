package com.chianmobile.assist;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.string;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.opengl.Visibility;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.chinamobile.service.FullDetectService;
import com.chinamobile.service.SendResultService;
import com.chinamobile.util.Util;

public class FullDetectActivity extends Activity implements OnClickListener,
		OnTouchListener, Util {
	private TextView detectBtn, recommend1, recommend2, tip,id1,id2;
	private EditText product_noET;
	private ImageButton cancelclearbtn, returnbtn;
	private GestureDetector mGestureDetector;
	private ScrollView scrollview;
	private Button accept1, accept2, refuse1, refuse2;
	private String oper_servnumber,recid;
	private LinearLayout fulldetectlayout;

	/*
	 * private BroadcastReceiver receiver = new BroadcastReceiver() { public
	 * void onReceive(Context context, Intent intent) { String result =
	 * intent.getExtras().getString("result"); unregisterReceiver(receiver);
	 * Log.i("FullBR:", result); } };
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fulldetect);
		detectBtn = (Button)findViewById(R.id.searchbtn);
		detectBtn.setOnClickListener(this);
		tip = (TextView) findViewById(R.id.tv_tip);
		scrollview = (ScrollView) findViewById(R.id.scrollview_coontent);
		product_noET = (EditText) findViewById(R.id.product_no);

		recommend1 = (TextView) findViewById(R.id.recommend1);
		recommend2 = (TextView) findViewById(R.id.recommend2);

		accept1 = (Button) findViewById(R.id.accept_one);
		accept1.setOnClickListener(this);

		accept2 = (Button) findViewById(R.id.accept_two);
		accept2.setOnClickListener(this);

		refuse1 = (Button) findViewById(R.id.refuse_one);
		refuse1.setOnClickListener(this);

		refuse2 = (Button) findViewById(R.id.refuse_two);
		refuse2.setOnClickListener(this);

		cancelclearbtn = (ImageButton) findViewById(R.id.cancelbtn);
		cancelclearbtn.setOnClickListener(this);
		returnbtn = (ImageButton) findViewById(R.id.returnbt);
		returnbtn.setOnClickListener(this);
		
		fulldetectlayout=(LinearLayout) findViewById(R.id.fulldetectlayout);
		id1 = (TextView) findViewById(R.id.id1);
		id2=(TextView) findViewById(R.id.id2);
		/* mGestureDetector = new GestureDetector(this,new MySimpleGesture()); */
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		return mGestureDetector.onTouchEvent(arg1);
	}

	@SuppressWarnings("static-access")
	@SuppressLint("ShowToast") @Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.returnbt:
			finish();
			/*overridePendingTransition(R.anim.in_from_left, R.anim.out_of_right);*/
			break;
		case R.id.searchbtn:
			String servnumber = product_noET.getText().toString();
			TextView tvtip = new TextView(getApplicationContext());
			tvtip.setTextSize(24);
			tvtip.setTextColor(Color.RED);
			if (TextUtils.isEmpty(servnumber)) {
				tvtip.setText("请输入正确的手机号码！");
			} else {
				if (!isMobileNo(servnumber)) {
					tvtip.setText("请输入正确的手机号码！");
				} else {
                    if(scrollview.getVisibility()==View.VISIBLE){
                    	 scrollview.setVisibility(View.INVISIBLE);
                    }  
					
					tip.setVisibility(View.VISIBLE); // 查询开始期间，让该内容显示。
					
					  accept1.setClickable(true);
			          refuse1.setClickable(true);
			          accept2.setClickable(true);
			          refuse2.setClickable(true);
			          
			          accept1.setBackgroundResource(R.drawable.button_selector);
			          accept2.setBackgroundResource(R.drawable.button_selector);
			          refuse1.setBackgroundResource(R.drawable.button_selector);
			          refuse2.setBackgroundResource(R.drawable.button_selector);
					 /*Resources resources = FullDetectActivity.this.getResources();  
					 Drawable btnDrawable = resources.getDrawable(R.drawable.result1); 
					 fulldetectlayout.setBackgroundDrawable(btnDrawable);*/
					 
					IntentFilter filter = new IntentFilter(
							"com.chinamobile.service.FullDetectService");
					registerReceiver(receiver, filter);
					Intent searchService = new Intent(getApplicationContext(),
							FullDetectService.class);
					searchService.putExtra("servnumber", servnumber);
					startService(searchService);
					// loadUserDetail(SERVER_REQDETECT_URL+"?product_no="+product_no);
					// return;
				}
			}
			break;
		case R.id.cancelbtn:
			product_noET.setText("");
			break;
		case R.id.accept_one: // 第一组同意
		   Log.i(">>>>>>>>>>>>>", "accept_one");
			Intent sendresult1 = new Intent(getApplicationContext(),
					SendResultService.class);
			oper_servnumber = product_noET.getText().toString();
			recid=id1.getText().toString();
            
			Log.i("<<<<<<<<<<<<", recid);
			sendresult1.putExtra("oper_servnumber", oper_servnumber);
			sendresult1.putExtra("id", recid); // 表示第一组中的数据
			sendresult1.putExtra("flag", "1");
			startService(sendresult1);
			
			accept1.setClickable(false);
			accept1.setBackgroundResource(R.drawable.border4);
			refuse1.setClickable(false);
			//refuse1.setBackgroundResource(R.drawable.border4);
			break;
		case R.id.accept_two: // 第二组同意
			Intent sendresult2 = new Intent(getApplicationContext(),
					SendResultService.class);
			oper_servnumber = product_noET.getText().toString();
			recid=id2.getText().toString();
			sendresult2.putExtra("oper_servnumber", oper_servnumber);
			sendresult2.putExtra("id", recid);
			sendresult2.putExtra("flag", "1");
			startService(sendresult2);
			
			accept2.setClickable(false);
			accept2.setBackgroundResource(R.drawable.border4);
			refuse2.setClickable(false);
			//refuse2.setBackgroundResource(R.drawable.border4);
			break;
		case R.id.refuse_one: // 第一组不同意
			
			Intent sendresult3 = new Intent(getApplicationContext(),
					SendResultService.class);
			oper_servnumber = product_noET.getText().toString();
			recid=id1.getText().toString();
			sendresult3.putExtra("oper_servnumber", oper_servnumber);
			sendresult3.putExtra("id", recid);
			sendresult3.putExtra("flag", "0");
			startService(sendresult3);
			 accept1.setClickable(false);
			// accept1.setBackgroundResource(R.drawable.border4);
	         refuse1.setClickable(false);
	         refuse1.setBackgroundResource(R.drawable.border4);
			break;
		case R.id.refuse_two: // 第二组不同意
			
			Intent sendresult4 = new Intent(getApplicationContext(),
					SendResultService.class);
			oper_servnumber = product_noET.getText().toString();
			recid=id2.getText().toString();
			sendresult4.putExtra("oper_servnumber", oper_servnumber);
			sendresult4.putExtra("id", recid);
			sendresult4.putExtra("flag", "0");
			startService(sendresult4);
			
			accept2.setClickable(false);
			//accept2.setBackgroundResource(R.drawable.border4);
			refuse2.setClickable(false);
			refuse2.setBackgroundResource(R.drawable.border4);
			break;
		default:
			break;
		}
	}

	private BroadcastReceiver receiver = new BroadcastReceiver() {

		public void onReceive(Context context, Intent intent) {
			String result = intent.getExtras().getString("result");
			String sendres = intent.getExtras().getString("sendresult"); //1009 1010
			Log.i("", "GHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
           
			if (result != null) {
				try {
					tip.setVisibility(View.INVISIBLE); // 当查询账号有返回结果，不显示
					scrollview.setVisibility(View.VISIBLE);
					JSONObject object = new JSONObject(result);
					String rec1 = object.getString("rec1");
					Log.i("11111111111", " " + rec1);
					
					String rec2 = object.getString("rec2");
					Log.i("22222222222", " " + rec2);
					
					String id_one=object.getString("recid1");
					Log.i("id1111111111111111111", " " + id_one);
					
					String id_two = object.getString("recid2");
					Log.i("id222222222", " " + id_two);

					recommend1.setTextSize(18);
					recommend1.setText("\u3000\u3000推荐一：" + rec1);
					recommend2.setTextSize(18);
					recommend2.setText("\u3000\u3000 推荐二：" + rec2);
                    
					id1.setText(id_one);
					id2.setText(id_two);
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (sendres != null) {
				Log.i("gggggggggggggggggg", sendres);
				if (sendres.equals("1009")) {
					Toast.makeText(context, "记录成功!",
							Toast.LENGTH_SHORT).show();
				} else if (sendres.equals("1010")) {
					Toast.makeText(context, "记录失败，请重试!",
							Toast.LENGTH_SHORT).show();
				}
				else{
					Toast.makeText(context, "连接出错，请重试", Toast.LENGTH_SHORT).show();
				}
			}

		}
	};

	/*
	 * private BroadcastReceiver receiverSend = new BroadcastReceiver(){
	 * 
	 * @Override public void onReceive(Context context, Intent intent) {
	 * 
	 * 
	 * }};
	 */

	private boolean isMobileNo(String number) {
		Pattern p = Pattern
				.compile("^((1[3,5,8][0-9])|(14[5,7])|(17[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(number);
		return m.matches();
	}

	/*
	 * @SuppressLint("SetJavaScriptEnabled") private void loadUserDetail(String
	 * webUri) { layoutforweb.removeAllViews(); WebView mywebview = new
	 * WebView(getApplicationContext()); mywebview.setOnTouchListener(this);
	 * LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
	 * LinearLayout.LayoutParams.WRAP_CONTENT,
	 * LinearLayout.LayoutParams.WRAP_CONTENT );
	 * layoutforweb.addView(mywebview,p);
	 * mywebview.getSettings().setJavaScriptEnabled(true); if (mywebview !=
	 * null) { mywebview.setWebViewClient(new WebViewClient() {
	 * 
	 * @Override public void onReceivedError(WebView view, int errorCode, String
	 * description, String failingUrl) { super.onReceivedError(view, errorCode,
	 * description, failingUrl); Toast.makeText(getApplicationContext(),
	 * "加载失败，请稍后再试！", Toast.LENGTH_SHORT).show(); } });
	 * mywebview.getSettings().setDefaultTextEncodingName("UTF-8");
	 * mywebview.loadDataWithBaseURL("", webUri, "text/html", "UTF-8", "");
	 * mywebview.reload(); } }
	 * 
	 * private class MySimpleGesture extends SimpleOnGestureListener { private
	 * int verticalMinDistance = 20; private int minVelocity = 0;
	 * 
	 * @Override public boolean onFling(MotionEvent e1, MotionEvent e2, float
	 * velocityX, float velocityY) { if (e1.getX() - e2.getX() >
	 * verticalMinDistance && Math.abs(velocityX) > minVelocity) {
	 * 
	 * }else if (e2.getX() - e1.getX() > verticalMinDistance &&
	 * Math.abs(velocityX) > minVelocity) { //向右手势 finish();
	 * overridePendingTransition(R.anim.in_from_left,R.anim.out_of_right); }
	 * return false; } }
	 */

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
		//overridePendingTransition(R.anim.in_from_left, R.anim.out_of_right);
	}

}
