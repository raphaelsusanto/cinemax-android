package com.cineplex;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import com.cineplex.entity.Bioskop;
import com.cineplex.entity.Film;
import com.cineplex.model.MovieItemAdapter;
import com.cineplex.service.BioskopService;
import com.cineplex.service.FilmService;
import com.cineplex.service.LoginService;
import com.cineplex.util.AutoSyncTheThread;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class TheaterDetailActivity extends Activity{
	private Bioskop bioskop;
	private TextView tvNama,tvAlamat,tvTelp,tvHarga;
	private ImageButton btn;
	private ListView lvFilm;
	private List<Film> data;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		try {
			super.onCreate(savedInstanceState);
			this.requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.theater_detail);
			
			tvNama=(TextView)findViewById(R.id.tvTheaterDetailNama);
			tvAlamat=(TextView)findViewById(R.id.tvTheaterDetailAlamat);
			tvTelp=(TextView)findViewById(R.id.tvTheaterDetailTelp);
			tvHarga=(TextView)findViewById(R.id.tvTheaterDetailHarga);
			btn=(ImageButton)findViewById(R.id.btnTheaterDetailLocation);
			lvFilm=(ListView)findViewById(R.id.lvTheaterDetailFilm);
			
			btn.setOnClickListener(new LocationListener());
			
			initData();
			
			lvFilm.setOnItemClickListener(new ScheduleListener());
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("ERROR", e.getMessage()+e.toString());
		}
		
	}
	
	public void initData(){
		try {
			Bundle extras = getIntent().getExtras();
			int idBioskop = extras != null ? extras.getInt("idBioskop") : null;
			
			NumberFormat IDR= NumberFormat.getCurrencyInstance(new Locale("ID", "id"));
			bioskop=new BioskopService().getBioskop(idBioskop);
			String nomat="Rp."+IDR.format(bioskop.getNomat()).substring(1);
			String jmt="Rp."+IDR.format(bioskop.getJmt()).substring(1);
			String htm="Rp."+IDR.format(bioskop.getHtm()).substring(1);
			String nomat3D="Rp."+IDR.format(bioskop.getNomat3d()).substring(1);
			String jmt3D="Rp."+IDR.format(bioskop.getJmt3d()).substring(1);
			String htm3D="Rp."+IDR.format(bioskop.getHtm3d()).substring(1);
			
			tvNama.setText(bioskop.getNama());
			tvAlamat.setText(bioskop.getAlamat());
			tvTelp.setText(bioskop.getTelp());
			
			tvHarga.setText(nomat+"(Senin-Kamis)\n"+jmt+"(Jumat)\n"+htm+"(Sabtu-Minggu)\n"+nomat3D+"(3D)(Senin-Kamis)\n"+jmt3D+"(3D)(Jumat)\n"+htm3D+"(3D)(Sabtu-Minggu)");
			
			data=new FilmService().getNowPlayingByBioskop(idBioskop,LoginService.email);
			MovieItemAdapter adapter= new MovieItemAdapter(this, R.layout.movie_item, data);
			lvFilm.setAdapter(adapter);
		} catch (Exception e) {
			Log.e("Error", e.getMessage());
		}
		
	}
	
	class LocationListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			Intent i= new Intent(TheaterDetailActivity.this, LocationActivity.class);
			i.putExtra("idBioskop", bioskop.getIdBioskop());
			startActivity(i);
		}
		
	}
	
	class ScheduleListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			int idFilm=data.get(arg2).getIdFilm();
			Intent i = new Intent(TheaterDetailActivity.this, DateActivity.class);
			i.putExtra("idFilm", idFilm);
			i.putExtra("idBioskop", bioskop.getIdBioskop());
			startActivity(i);
			
		}
		
		
	}

}
