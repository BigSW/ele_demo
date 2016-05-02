package com.chianmobile.assist;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chinamobile.adapter.AbstractSpinerAdapter;
import com.chinamobile.adapter.SpinerPopWindow;

public class StaffAddrActivity extends Activity implements OnClickListener,OnTouchListener, AbstractSpinerAdapter.IOnItemSelectListener {
	private TextView lftv,rttv;
	private RelativeLayout lflayout, rtlayout;
	private SpinerPopWindow mSpinerPopWindow;
	private List<String> nameList = new ArrayList<String>();
	private ImageButton sfrebkmbt;
	private GestureDetector mGestureDetector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.staffaddr);
		lftv = (TextView)findViewById(R.id.lftv);
		rttv = (TextView)findViewById(R.id.rttv);
		lflayout = (RelativeLayout)findViewById(R.id.lflayout);
		rtlayout = (RelativeLayout)findViewById(R.id.rtlayout);
		sfrebkmbt = (ImageButton)findViewById(R.id.sfrebkmbt);
		mGestureDetector = new GestureDetector(this,new MySimpleGesture());
		LinearLayout fulldetectlayout = (LinearLayout)findViewById(R.id.fulldetectlayout);
		fulldetectlayout.setOnTouchListener(this);
		
		lflayout.setOnClickListener(this);
		sfrebkmbt.setOnClickListener(this);
		
		String[] names = getResources().getStringArray(R.array.qx_name);
		for(int i = 0; i < names.length; i++){
			nameList.add(names[i]);
		}
		mSpinerPopWindow = new SpinerPopWindow(this);
		mSpinerPopWindow.refreshData(nameList, 0);
		mSpinerPopWindow.setItemListener(this);
	}
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.lflayout:
			showSpinWindow();
			break;
		case R.id.sfrebkmbt:
			finish();
			overridePendingTransition(R.anim.in_from_left,R.anim.out_of_right); 
			break;
		default:
			break;
		}
	}
	@Override
	public void onItemClick(int pos) {
		setHero(pos);
	}
	
	private void setHero(int pos){
		if (pos >= 0 && pos <= nameList.size()){
			String value = nameList.get(pos);
			lftv.setText(value);
		}
	}
	private void showSpinWindow(){
		Log.e("", "showSpinWindow");
		mSpinerPopWindow.setWidth(lflayout.getWidth());
		mSpinerPopWindow.showAsDropDown(lflayout);
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
		overridePendingTransition(R.anim.in_from_left,R.anim.out_of_right); 
	}
	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		return mGestureDetector.onTouchEvent(arg1);
	}
	private class MySimpleGesture extends SimpleOnGestureListener {
		private int verticalMinDistance = 20;
		private int minVelocity         = 0;  
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			if (e1.getX() - e2.getX() > verticalMinDistance && Math.abs(velocityX) > minVelocity) {
				
			}else if (e2.getX() - e1.getX() > verticalMinDistance && Math.abs(velocityX) > minVelocity) {  
				//向右手势
				finish();
				overridePendingTransition(R.anim.in_from_left,R.anim.out_of_right); 
			}
			return false;
		}
	}
}
