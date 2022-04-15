package com.cineplex;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.cineplex.TheaterActivity.RefreshData;
import com.cineplex.entity.Film;
import com.cineplex.model.MovieItemAdapter;
import com.cineplex.service.FilmService;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IInterface;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class CommingSoonActivity extends Activity{

	private ListView lvCommingSoon;
	private List<Film> filmData;
	private Timer timer= new Timer();
	private Handler h;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.comming_soon);
		
		h= new Handler();
		lvCommingSoon=(ListView)findViewById(R.id.lvCommingSoon);
		lvCommingSoon.setOnItemClickListener(new CommingSoonListener());
		//initData();
	}
	
	private void initData(){
		filmData= new FilmService().getCommingSoon();
		MovieItemAdapter adapter= new MovieItemAdapter(this, R.layout.movie_item, filmData);
		lvCommingSoon.setAdapter(adapter);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		h.post(new Runnable() {
			final ProgressDialog dialog = ProgressDialog.show(
					CommingSoonActivity.this, "", "Loading. Please wait...", true);
			@Override
			public void run() {
				Log.e("Resume", "a");
				timer.cancel();
				timer = new Timer();
				initData();
				//timer.scheduleAtFixedRate(new RefreshData(), 0, 10000);
				dialog.dismiss();
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
	
	class CommingSoonListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			//Toast.makeText(CommingSoonActivity.this, filmData.get(arg2).getJudul(), Toast.LENGTH_SHORT).show();
			Intent i= new Intent(CommingSoonActivity.this, CommingDetailActivity.class);
			i.putExtra("idfilm", filmData.get(arg2).getIdFilm());
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
