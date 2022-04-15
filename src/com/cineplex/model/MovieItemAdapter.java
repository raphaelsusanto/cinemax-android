package com.cineplex.model;

import java.util.List;

import com.cineplex.R;
import com.cineplex.entity.Film;
import com.cineplex.util.AutoSyncTheThread;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MovieItemAdapter extends ArrayAdapter<Film> {

	private Context context;
	private int resourceId;
	private List<Film> data;
	private Handler h;

	public MovieItemAdapter(Context context, int textViewResourceId,
			List<Film> objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
		this.resourceId = textViewResourceId;
		this.data = objects;
		h= new Handler();
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		final MovieHolder holder;
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(resourceId, parent, false);
			holder = new MovieHolder();
			holder.ivMovie = (ImageView) row.findViewById(R.id.ivMovie);
			holder.tvMovie = (TextView) row.findViewById(R.id.tvMovie);
			holder.tvGenre=(TextView)row.findViewById(R.id.tvGenre);
			holder.btnPemirsa=(TextView)row.findViewById(R.id.movie_item_btn_pemirsa);
			row.setTag(holder);
		} else {
			holder = (MovieHolder) row.getTag();
		}
		final AutoSyncTheThread sync= new AutoSyncTheThread();
		
		final Film f=data.get(position);
		
		h.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				holder.ivMovie.setImageBitmap(sync.downloadFile(f.getGambar()));
			}
		});
		
		holder.tvMovie.setText(f.getJudul().toString());
		holder.tvGenre.setText(f.getJenis().toString());
		holder.btnPemirsa.setText(f.getPemirsa().toString());
		return row;
	}

	static class MovieHolder {
		ImageView ivMovie;
		TextView tvMovie;
		TextView tvGenre;
		TextView btnPemirsa;
	}

}
