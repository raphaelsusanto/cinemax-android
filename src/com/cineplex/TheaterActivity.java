package com.cineplex;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.cineplex.NowPlayingActivity.RefreshData;
import com.cineplex.entity.Bioskop;
import com.cineplex.model.TheaterItemAdapter;
import com.cineplex.service.BioskopService;
import com.cineplex.util.AutoSyncTheThread;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class TheaterActivity extends Activity {
	private ListView lvTheater;
	private List<Bioskop> data;
	private Timer timer= new Timer();
	private Handler h;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.theater);
		h= new Handler();
		lvTheater = (ListView) findViewById(R.id.lvTheater);
		//initData();
		lvTheater.setOnItemClickListener(new TheaterItemClickListener());
	}

	private void initData() {
		try {
			data = new BioskopService().getAllBioskop();
			TheaterItemAdapter adapter = new TheaterItemAdapter(this,
					R.layout.theater_item, data);
			lvTheater.setAdapter(adapter);
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("ERROR", e.getMessage());
		}
		
	}
	@Override
	public void onBackPressed() {
		AlertDialog deleteExit = new AlertDialog.Builder(this).create();
		deleteExit.setTitle("Exit");
		deleteExit.setMessage("Are you sure you want to exit this application?");
		deleteExit.setButton("Yes", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});
		deleteExit.setButton2("No", new DialogInterface.OnClickListener(){

		@Override
		public void onClick(DialogInterface dialog, int which) {
			
		}
		});
		deleteExit.show();
	}
	
	@Override
	protected void onResume() {

		super.onResume();
		final ProgressDialog dialog = ProgressDialog.show(
				TheaterActivity.this, "", "Loading. Please wait...", true);
		h.post(new Runnable() {

			@Override
			public void run() {
				Log.e("Resume", "a");
				timer.cancel();
				timer = new Timer();
				initData();
				dialog.dismiss();
				//timer.scheduleAtFixedRate(new RefreshData(), 10000, 10000);
			}
		});

	}
	
	@Override
	protected void onPause() {
		timer.cancel();
		super.onPause();
		Log.e("pause", "a");
	}

	@Override
	protected void onStop() {
		timer.cancel();
		super.onPause();
		Log.e("Stop", "a");
	}
	
	class TheaterItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			int idBioskop=data.get(arg2).getIdBioskop();
			Intent i= new Intent(TheaterActivity.this,TheaterDetailActivity.class);
			i.putExtra("idBioskop",idBioskop);
			startActivity(i);
			
		}
		
		
	}
	class RefreshData extends TimerTask {

		@Override
		public void run() {
			h.post(new Runnable() {

				@Override
				public void run() {
					initData();
					Log.e("Refresh", "now playing");
				}
			});
		}

	}

}
