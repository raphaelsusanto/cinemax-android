package com.cineplex;

import java.util.List;

import com.cineplex.entity.Bioskop;
import com.cineplex.entity.Film;
import com.cineplex.model.TheaterItemAdapter;
import com.cineplex.service.BioskopService;
import com.cineplex.service.FilmService;
import com.cineplex.util.AutoSyncTheThread;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MovieDetailActivity extends Activity{
	private ImageView ivGambar;
	private TextView tvJudul,tvSutradara,tvProduser,tvJenis,tvDurasi,tvDeskripsi;
	private ListView lvTheater;
	private List<Bioskop> data;
	private Integer idFilm;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.movie_detail);
		
		ivGambar=(ImageView)findViewById(R.id.ivMovieDetailGambar);
		tvJudul=(TextView)findViewById(R.id.tvMovieDetailJudul);
		tvSutradara=(TextView)findViewById(R.id.tvMovieDetailSutradara);
		tvProduser=(TextView)findViewById(R.id.tvMovieDetailProduser);
		tvJenis=(TextView)findViewById(R.id.tvMovieDetailJenis);
		tvDurasi=(TextView)findViewById(R.id.tvMovieDetailDurasi);
		tvDeskripsi=(TextView)findViewById(R.id.tvMovieDetailDeskripsi);
		lvTheater=(ListView)findViewById(R.id.lvMovieDetailTheater);
		initData();
		
		lvTheater.setOnItemClickListener(new ScheduleListener());
		ivGambar.setOnClickListener(new ShowImageListener());
	}
	
	public void initData(){
		try {
			Bundle extras = getIntent().getExtras();
			idFilm = extras != null ? extras.getInt("idfilm") : null;
			AutoSyncTheThread sync= new AutoSyncTheThread();
			Film f=new FilmService().getFilm(idFilm);
			ivGambar.setImageBitmap(sync.downloadFile(f.getGambar()));
			tvJudul.setText(f.getJudul());
			tvSutradara.setText(f.getSutradara());
			tvProduser.setText(f.getProduser());
			tvJenis.setText(f.getJenis());
			tvDurasi.setText(f.getDurasi()+" Menit");
			tvDeskripsi.setText(f.getSinopsis());
			
			
			data = new BioskopService().getAllBioskopByFilm(idFilm);
			TheaterItemAdapter adapter = new TheaterItemAdapter(this,
					R.layout.theater_item, data);
			lvTheater.setAdapter(adapter);
		} catch (Exception e) {
			Log.e("ERROR", e.toString());
			// TODO: handle exception
		}
		
		//Toast.makeText(MovieDetailActivity.this, idFilm.toString(), Toast.LENGTH_SHORT).show();
	}
	
	class ScheduleListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			Bioskop bioskop=data.get(arg2);
			Intent i = new Intent(MovieDetailActivity.this, DateActivity.class);
			i.putExtra("idBioskop", bioskop.getIdBioskop());
			i.putExtra("idFilm", idFilm);
			startActivity(i);
		}
	}
	
	class ShowImageListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			Intent i= new Intent(MovieDetailActivity.this, ShowImageActivity.class);
			i.putExtra("idFilm", idFilm);
			startActivity(i);
		}
	}
}
