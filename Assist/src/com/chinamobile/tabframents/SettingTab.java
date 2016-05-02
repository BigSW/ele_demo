package com.chinamobile.tabframents;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chianmobile.assist.LoginActivity;
import com.chianmobile.assist.R;
import com.chianmobile.assist.ResetPswActivity;

public class SettingTab extends Fragment implements OnClickListener{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View layoutView = inflater.inflate(R.layout.settingtab,container,false);
		TextView logoutbtn = (TextView)layoutView.findViewById(R.id.exitapp);
		RelativeLayout resetpswlayout = (RelativeLayout)layoutView.findViewById(R.id.resetpswlayout);
		
		logoutbtn.setOnClickListener(this);
		resetpswlayout.setOnClickListener(this);
		
		return layoutView;
	}
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.exitapp:
			Intent loginIntent = new Intent(this.getActivity(), LoginActivity.class);
			this.getActivity().overridePendingTransition(R.anim.in_from_left,R.anim.out_of_right);  
			startActivity(loginIntent);
			this.getActivity().finish();
			break;
		case R.id.resetpswlayout:
			Intent rstpswIntent = new Intent(this.getActivity(), ResetPswActivity.class);
			this.getActivity().overridePendingTransition(R.anim.out_of_right,R.anim.in_from_left);  
			startActivity(rstpswIntent);
			break;
		default:
			break;
		}
	}
}
