package com.chianmobile.assist;

import java.util.ArrayList;
import java.util.List;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinamobile.tabframents.MainTab;
import com.chinamobile.tabframents.OwninfoTab;
import com.chinamobile.tabframents.SettingTab;

public class MainActivity extends FragmentActivity implements OnClickListener {

	private FragmentPagerAdapter mAdapter;
	private ViewPager mViewPager;
	private List<Fragment> mFragments = new ArrayList<Fragment>();

	private LinearLayout mTabBtnMain,mTabBtnSettings,mTabBtnOwninfo;
	private ImageButton mMainImg,mSetImg,mOwninfoImg,logobtn;
	private TextView maintv, settv, owninfotv,logoSettingTitlte;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_main);
		initView();
		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
			@Override
			public int getCount() {
				return mFragments.size();
			}
			@Override
			public Fragment getItem(int arg0) {
				return mFragments.get(arg0);
			}
		};
		mViewPager.setAdapter(mAdapter);
		initEvent();
	}
	private void initEvent() {
		mTabBtnMain.setOnClickListener(this);
		mTabBtnSettings.setOnClickListener(this);
		mTabBtnOwninfo.setOnClickListener(this);
		
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				Resources resource = (Resources) getBaseContext().getResources();
				ColorStateList csl = (ColorStateList) resource.getColorStateList(R.color.pressedbtn);
				switch (arg0) {
				case 0:
					resetTabBtn();
					mMainImg.setBackgroundResource(R.drawable.mhomen);
					if (csl != null) {
						maintv.setTextColor(csl);
						logobtn.setVisibility(View.VISIBLE);
						logoSettingTitlte.setVisibility(View.GONE);
					}
					break;
				case 1:
					resetTabBtn();
					mSetImg.setBackgroundResource(R.drawable.msetn);
					if (csl != null) {
						settv.setTextColor(csl);
						logobtn.setVisibility(View.GONE);
						logoSettingTitlte.setVisibility(View.VISIBLE);
						logoSettingTitlte.setText("设置");
					}
					break;
				case 2:
					resetTabBtn();
					mOwninfoImg.setBackgroundResource(R.drawable.mownn);
					if (csl != null) {
						owninfotv.setTextColor(csl);
						logobtn.setVisibility(View.GONE);
						logoSettingTitlte.setVisibility(View.VISIBLE);
						logoSettingTitlte.setText("移动");
					}
					break;
				default:
					break;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}


	private void initView() {
		mViewPager = (ViewPager) findViewById(R.id.id_viewpage);
		// 初始化四个LinearLayout
		mTabBtnMain = (LinearLayout) findViewById(R.id.maintab);
		mTabBtnSettings = (LinearLayout) findViewById(R.id.settab);
		mTabBtnOwninfo = (LinearLayout) findViewById(R.id.owntab);
		// 初始化四个按钮
		mMainImg = (ImageButton) findViewById(R.id.mainimg);
		mSetImg = (ImageButton) findViewById(R.id.setimg);
		mOwninfoImg = (ImageButton) findViewById(R.id.ownimg);
		maintv = (TextView)findViewById(R.id.maintv);
		settv = (TextView)findViewById(R.id.settv);
		owninfotv = (TextView)findViewById(R.id.owntv);
		//初始化左上角app图标和标题内容
		logobtn = (ImageButton)findViewById(R.id.logobtn);
		logobtn.setVisibility(View.VISIBLE);
		logoSettingTitlte = (TextView)findViewById(R.id.settingtitle);

		MainTab maintab = new MainTab();
		SettingTab settingtab = new SettingTab();
		OwninfoTab owntab = new OwninfoTab();

		mFragments.add(maintab);
		mFragments.add(settingtab);
		mFragments.add(owntab);
	}

	protected void resetTabBtn() {
		mMainImg.setBackgroundResource(R.drawable.mhome);
		mSetImg.setBackgroundResource(R.drawable.mset);
		mOwninfoImg.setBackgroundResource(R.drawable.mown);
		Resources resource = (Resources) getBaseContext().getResources();
		ColorStateList csl = (ColorStateList) resource.getColorStateList(R.color.normalbtn);
		if (csl != null) {
			maintv.setTextColor(csl);
			settv.setTextColor(csl);
			owninfotv.setTextColor(csl);
		}
	}

	@Override
	public void onClick(View arg0) {
		Resources resource = (Resources) getBaseContext().getResources();
		ColorStateList csl = (ColorStateList) resource.getColorStateList(R.color.pressedbtn);
        switch (arg0.getId()) {
		case R.id.maintab:
			resetTabBtn();
			mViewPager.setCurrentItem(0);
			mMainImg.setBackgroundResource(R.drawable.mhomen);
			if (csl != null) {
				maintv.setTextColor(csl);
				logobtn.setVisibility(View.VISIBLE);
				logoSettingTitlte.setVisibility(View.GONE);
			}
			break;
		case R.id.settab:
			resetTabBtn();
			mViewPager.setCurrentItem(1);
			mSetImg.setBackgroundResource(R.drawable.msetn);
			if (csl != null) {
				settv.setTextColor(csl);
				logobtn.setVisibility(View.GONE);
				logoSettingTitlte.setVisibility(View.VISIBLE);
				logoSettingTitlte.setText("设置");
			}
			break;
		case R.id.owntab:
			resetTabBtn();
			mViewPager.setCurrentItem(2);
			mOwninfoImg.setBackgroundResource(R.drawable.mownn);
			if (csl != null) {
				owninfotv.setTextColor(csl);
				logobtn.setVisibility(View.GONE);
				logoSettingTitlte.setVisibility(View.VISIBLE);
				logoSettingTitlte.setText("移动");
			}
			break;
		default:
			break;
		}
	}

}
