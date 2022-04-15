package com.cineplex;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cineplex.entity.Bioskop;
import com.cineplex.entity.Film;
import com.cineplex.entity.Jadwal;
import com.cineplex.entity.Jam;
import com.cineplex.model.ScheduleItemAdapter;
import com.cineplex.service.BioskopService;
import com.cineplex.service.FilmService;
import com.cineplex.service.JadwalService;
import com.cineplex.util.AutoSyncTheThread;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings.System;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ScheduleActivity extends Activity{
	private int idFilm,idBioskop;
	private TextView tvFilm,tvBioskop,tvDate;
	private ListView lvJadwal;
	private Date tgl;
	List<Jam> data;
	private Handler handler;
	SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.schedule);
		
		tvFilm=(TextView)findViewById(R.id.tvScheduleFilm);
		tvBioskop=(TextView)findViewById(R.id.tvScheduleBioskop);
		tvDate=(TextView)findViewById(R.id.tvScheduleDate);
		lvJadwal=(ListView)findViewById(R.id.lvScheduleJadwal);
		handler= new Handler();
		
		initData();
		
		lvJadwal.setOnItemClickListener(new JamListener());
	}
	
	public void initData(){
		try {
			
			Bundle extras = getIntent().getExtras();
			idFilm = extras != null ? extras.getInt("idFilm") : null;
			idBioskop = extras != null ? extras.getInt("idBioskop") : null;
			String temp=extras != null ? extras.getString("tgl") : null;
			tgl=dateFormat.parse(temp);
			Film f=new FilmService().getFilm(idFilm);
			Bioskop b=new BioskopService().getBioskop(idBioskop);
			tvFilm.setText(f.getJudul());
			tvBioskop.setText(b.getNama());
			tvDate.setText(temp);
			data= new JadwalService().getJamByFilmAndBioskop(f.getIdFilm(), b.getIdBioskop(), tgl);
			
			ScheduleItemAdapter adapter= new ScheduleItemAdapter(ScheduleActivity.this, R.layout.schedule_item, data);
			if (data.size()==0) {
				Toast.makeText(this, "Jadwal untuk hari ini sudah habis", Toast.LENGTH_SHORT).show();
			}
			lvJadwal.setAdapter(adapter);
		} catch (Exception e) {
			Log.e("ERROR", e.toString());
		}
		
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		initData();
	}

	class JamListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			final ProgressDialog dialog = ProgressDialog.show(
					ScheduleActivity.this, "", "Loading. Please wait...",
					true);
			Intent i= new Intent(ScheduleActivity.this, MapStudioActivity.class);
			i.putExtra("idJadwal", data.get(arg2).getIdJadwal());
			i.putExtra("jam", data.get(arg2).getJam());
			i.putExtra("tgl", dateFormat.format(tgl));
			startActivity(i);
			handler.post(new Runnable() {
				public void run() {
					dialog.dismiss();
				}
			});
		}
	}
}
