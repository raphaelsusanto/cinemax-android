package com.cineplex;

import com.cineplex.entity.Film;
import com.cineplex.service.FilmService;
import com.cineplex.util.AutoSyncTheThread;
import com.polites.android.GestureImageView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class ShowImageActivity extends Activity {
	GestureImageView ivImage;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.show_image);
		ivImage = (GestureImageView) findViewById(R.id.ivShowImage);
		initData();
	}

	public void initData() {
		try {
			Bundle extras = getIntent().getExtras();
			int idFilm = extras != null ? extras.getInt("idFilm") : null;
			AutoSyncTheThread sync = new AutoSyncTheThread();
			Film f = new FilmService().getFilm(idFilm);
			ivImage.setImageBitmap(sync.downloadFile(f.getGambar()));

		} catch (Exception e) {
			Log.e("", e.toString());
		}

	}

}
