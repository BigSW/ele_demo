package com.chinamobile.tabframents;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chianmobile.assist.FullDetectActivity;
import com.chianmobile.assist.R;
import com.chianmobile.assist.StaffAddrActivity;

public class MainTab extends Fragment implements OnClickListener{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View layoutView = inflater.inflate(R.layout.maintab,container,false);
		
		DisplayMetrics dm = new DisplayMetrics();
		this.getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		
		RelativeLayout mtoplayout = (RelativeLayout)layoutView.findViewById(R.id.mtoplayout);
		
		LinearLayout.LayoutParams linearParams =  (LinearLayout.LayoutParams)mtoplayout.getLayoutParams();
        linearParams.height = (int)(dm.widthPixels*0.5);
        mtoplayout.setLayoutParams(linearParams);
		
		RelativeLayout detectlayout = (RelativeLayout)layoutView.findViewById(R.id.schrltlayout);
		detectlayout.setOnClickListener(this);
		
		/*RelativeLayout addrlayout = (RelativeLayout)layoutView.findViewById(R.id.addrrltlayout);
		addrlayout.setOnClickListener(this);*/
		
		return layoutView;
	}
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.schrltlayout:
			Intent detectIntent = new Intent(this.getActivity(), FullDetectActivity.class);
			this.getActivity().overridePendingTransition(R.anim.out_of_right,R.anim.in_from_left);  
			startActivity(detectIntent);
			break;
		/*case R.id.addrrltlayout:
			Intent addrIntent = new Intent(this.getActivity(), StaffAddrActivity.class);
			this.getActivity().overridePendingTransition(R.anim.out_of_right,R.anim.in_from_left);  
			startActivity(addrIntent);
			break;*/
		default:
			break;
		}
	}
}
