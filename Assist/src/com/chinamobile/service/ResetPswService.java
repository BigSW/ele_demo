package com.chinamobile.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.chinamobile.util.Constant;
import com.chinamobile.util.Util;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class ResetPswService extends Service{
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		final Intent myintent = intent;
		new Thread(new Runnable() {
			@Override   
			public void run() {
				String oldpsw = myintent.getExtras().getString("oldpsw");
				String newpsw = myintent.getExtras().getString("newpsw");
				String result = "request error";
				HttpPost httpRequest = new HttpPost(Util.SERVER_REQRESETPSW_URL);
				httpRequest.addHeader("user-agent",Util.DEVICEFLAG);
				List<NameValuePair> params = new ArrayList<NameValuePair>();
		        params.add(new BasicNameValuePair("operacount", Constant.operacount));  
		        params.add(new BasicNameValuePair("oldpsw", oldpsw));
		        params.add(new BasicNameValuePair("newpsw", newpsw));
				try {
					HttpEntity httpEntity = new UrlEncodedFormEntity(params,"utf-8");
					httpRequest.setEntity(httpEntity);
					HttpClient httpClient = new DefaultHttpClient();  
		            HttpResponse httpResponse = httpClient.execute(httpRequest);
		            Log.i("code", String.valueOf(httpResponse.getStatusLine().getStatusCode()));
		            if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){  
		                result = EntityUtils.toString(httpResponse.getEntity());
		                Log.i("result", result);
		            }else{
		            	Log.i("else","request error");
		            }  
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				Intent intent = new Intent();
				intent.putExtra("result", result);
				intent.setAction("com.chinamobile.service.ResetPswService");
				sendBroadcast(intent);
				stopService(myintent);
			}
		}).start();
		return super.onStartCommand(intent, flags, startId);
	}
}
