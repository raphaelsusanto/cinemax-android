package com.cineplex;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

public class MainMenuActivity extends TabActivity {
	/** Called when the activity is first created. */
	TabHost tabHost;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	
		setContentView(R.layout.mainmenu);

		tabHost = getTabHost();
		TabSpec nowPlayingSpec = tabHost.newTabSpec("NowPlaying");
		// setting Title and Icon for the Tab
		nowPlayingSpec.setIndicator("Now Playing", getResources().getDrawable(
				R.drawable.now_playing));
		Intent nowPlayingIntent = new Intent(this, NowPlayingActivity.class);
		nowPlayingSpec.setContent(nowPlayingIntent);

		TabSpec theaterSpec = tabHost.newTabSpec("Theater");
		// setting Title and Icon for the Tab
		theaterSpec.setIndicator("Theater",getResources().getDrawable(
				R.drawable.theatre));
		Intent theaterIntent = new Intent(this, TheaterActivity.class);
		theaterSpec.setContent(theaterIntent);

		TabSpec commingSoonSpec = tabHost.newTabSpec("CommingSoon");
		// setting Title and Icon for the Tab
		commingSoonSpec.setIndicator("Coming Soon", getResources().getDrawable(
				R.drawable.coming_soon));
		Intent commingSoonIntent = new Intent(this, CommingSoonActivity.class);
		commingSoonSpec.setContent(commingSoonIntent);
		
		TabSpec personalSpec = tabHost.newTabSpec("Personal Data");
		personalSpec.setIndicator("Info",getResources().getDrawable(R.drawable.info));
		Intent personalIntent = new Intent(this, InfoActivity.class);
		personalSpec.setContent(personalIntent);
		
		tabHost.addTab(nowPlayingSpec);
		tabHost.addTab(theaterSpec);
		tabHost.addTab(commingSoonSpec);
		tabHost.addTab(personalSpec);
		
		for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
			tabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.edit_tray_btn_default);
		}
		tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundResource(R.drawable.edit_tray_btn_press);

		tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String arg0) {
				for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
					tabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.edit_tray_btn_default);
				}
				tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundResource(R.drawable.edit_tray_btn_press);
				
			}
		});
		
	}
	
}