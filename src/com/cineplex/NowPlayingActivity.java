package com.cineplex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.cineplex.InfoActivity.RefreshSaldo;
import com.cineplex.entity.*;
import com.cineplex.model.MovieItemAdapter;

import com.cineplex.service.FilmService;
import com.cineplex.service.LoginService;
import com.cineplex.util.AutoSyncTheThread;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class NowPlayingActivity extends Activity {
	private ListView lvNowPlaying;
	private List<Film> list;
	private Handler handler;
	private Timer timer = new Timer();
	private Handler h;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.now_playing);
		h = new Handler();
		handler = new Handler();

		lvNowPlaying = (ListView) findViewById(R.id.lvNowPlaying);

		// initData();
		// timer.scheduleAtFixedRate(new RefreshData(), 5000, 5000);
		lvNowPlaying.setOnItemClickListener(new ItemClickNowPlayingListener());

	}

	class RefreshData extends TimerTask {

		@Override
		public void run() {
			handler.post(new Runnable() {

				@Override
				public void run() {
					initData();
					Log.e("Refresh", "now playing");
				}
			});
		}

	}

	public void initData() {
		try {
			ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
			HashMap<String, String> map;

			list = new FilmService().getNowPlaying(LoginService.email);
			// for(Film f:list){
			// map = new HashMap<String, String>();
			// map.put("movie", f.getJudul());
			// map.put("genre", f.getJenis());
			// data.add(map);
			// }
			//			
			// SimpleAdapter adapter = new SimpleAdapter(this, data,
			// R.layout.movie_item, new String[] { "movie", "genre" },
			// new int[] { R.id.tvMovie, R.id.tvGenre });
			// lvNowPlaying.setAdapter(adapter);
			MovieItemAdapter adapter = new MovieItemAdapter(this,
					R.layout.movie_item, list);
			lvNowPlaying.setAdapter(adapter);
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("ERROR", e.getMessage());
		}

	}

	@Override
	public void onBackPressed() {
		AlertDialog deleteExit = new AlertDialog.Builder(this).create();
		deleteExit.setTitle("Exit");
		deleteExit
				.setMessage("Are you sure you want to exit this application?");
		deleteExit.setButton("Yes", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});
		deleteExit.setButton2("No", new DialogInterface.OnClickListener() {

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
				NowPlayingActivity.this, "", "Loading. Please wait...", true);
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

	class ItemClickNowPlayingListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			try {
				Film f = list.get(arg2);
				Intent i = new Intent(NowPlayingActivity.this,
						MovieDetailActivity.class);
				i.putExtra("idfilm", f.getIdFilm());
				startActivity(i);
				// Toast.makeText(NowPlayingActivity.this, f.getJudul(),
				// Toast.LENGTH_SHORT).show();
				// setContentView(R.layout.movie_detail);
			} catch (Exception e) {
				Log.e("ERROR", e.getMessage());
			}

		}

	}
}
