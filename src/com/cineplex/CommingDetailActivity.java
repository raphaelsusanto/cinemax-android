package com.cineplex;

import com.cineplex.entity.Film;
import com.cineplex.service.FilmService;
import com.cineplex.util.AutoSyncTheThread;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class CommingDetailActivity extends Activity {
	private ImageView ivGambar;
	private TextView tvJudul, tvSutradara, tvProduser, tvJenis, tvDurasi,
			tvDeskripsi;
	private int idFilm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.comming_soon_detail);
		ivGambar = (ImageView) findViewById(R.id.ivCommingDetailGambar);
		tvJudul = (TextView) findViewById(R.id.tvCommingDetailJudul);
		tvSutradara = (TextView) findViewById(R.id.tvCommingDetailSutradara);
		tvProduser = (TextView) findViewById(R.id.tvCommingDetailProduser);
		tvJenis = (TextView) findViewById(R.id.tvCommingDetailJenis);
		tvDurasi = (TextView) findViewById(R.id.tvCommingDetailDurasi);
		tvDeskripsi = (TextView) findViewById(R.id.tvCommingDetailDeskripsi);

		initData();
		
		ivGambar.setOnClickListener(new ShowImageListener());
	}

	public void initData() {
		try {
			Bundle extras = getIntent().getExtras();
			idFilm = extras != null ? extras.getInt("idfilm") : null;
			AutoSyncTheThread sync = new AutoSyncTheThread();
			Film f = new FilmService().getFilm(idFilm);
			ivGambar.setImageBitmap(sync.downloadFile(f.getGambar()));
			tvJudul.setText(f.getJudul());
			tvSutradara.setText(f.getSutradara());
			tvProduser.setText(f.getProduser());
			tvJenis.setText(f.getJenis());
			tvDurasi.setText(f.getDurasi() + " Menit");
			tvDeskripsi.setText(f.getSinopsis());
		} catch (Exception e) {
			Log.e("pael", idFilm+"");
		}

	}
	class ShowImageListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			Intent i= new Intent(CommingDetailActivity.this, ShowImageActivity.class);
			i.putExtra("idFilm", idFilm);
			startActivity(i);
		}
	}
}
