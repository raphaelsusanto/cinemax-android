package com.cineplex;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.cineplex.entity.Bioskop;
import com.cineplex.entity.Film;
import com.cineplex.model.DateItemAdapter;
import com.cineplex.service.BioskopService;
import com.cineplex.service.FilmService;
import com.cineplex.service.JadwalService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class DateActivity extends Activity {
	
	private List<Date> data;
	private int idFilm,idBioskop;
	private ListView lvDate;
	private TextView tvFilm,tvTheater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.date);
		
		tvFilm=(TextView)findViewById(R.id.tvDateFilm);
		tvTheater=(TextView)findViewById(R.id.tvDateTheater);
		lvDate= (ListView)findViewById(R.id.lvDate);
		lvDate.setOnItemClickListener(new ClickDateListener());
		initData();
	}
	
	private void initData(){
		try {
			Bundle extras = getIntent().getExtras();
			idFilm = extras != null ? extras.getInt("idFilm") : null;
			idBioskop = extras != null ? extras.getInt("idBioskop") : null;
			Film f=new FilmService().getFilm(idFilm);
			Bioskop b=new BioskopService().getBioskop(idBioskop);
			JadwalService jadwalService= new JadwalService();
			data=jadwalService.getAllDate(idFilm, idBioskop, new Date());
			DateItemAdapter adapter= new DateItemAdapter(this, R.layout.date_item, data);
			lvDate.setAdapter(adapter);
			tvFilm.setText(f.getJudul());
			tvTheater.setText(b.getNama());
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("ERROR", e.toString());
		}
		
	}
	
	class ClickDateListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Intent i= new Intent(DateActivity.this, ScheduleActivity.class);
			SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");
			i.putExtra("tgl", dateFormat.format(data.get(arg2)));
			i.putExtra("idFilm", idFilm);
			i.putExtra("idBioskop", idBioskop);
			startActivity(i);
		}
		
	}

}
