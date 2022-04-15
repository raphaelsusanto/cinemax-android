package com.cineplex.model;

import java.util.List;

import com.cineplex.R;
import com.cineplex.entity.Bioskop;
import com.cineplex.entity.Film;
import com.cineplex.model.MovieItemAdapter.MovieHolder;
import com.cineplex.util.AutoSyncTheThread;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TheaterItemAdapter extends ArrayAdapter<Bioskop> {
	private Context context;
	private int resourceId;
	private List<Bioskop> data;
	public TheaterItemAdapter(Context context, int textViewResourceId,
			List<Bioskop> objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
		this.resourceId = textViewResourceId;
		this.data = objects;
		// TODO Auto-generated constructor stub
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		BioskopHolder holder = null;
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(resourceId, parent, false);
			holder = new BioskopHolder();
			
			holder.tvNama = (TextView) row.findViewById(R.id.tvTheaterItemNama);
			holder.tvTelp=(TextView)row.findViewById(R.id.tvTheaterItemTelp);
			holder.tvAlamat=(TextView)row.findViewById(R.id.tvTheaterItemAlamat);
			row.setTag(holder);
		} else {
			holder = (BioskopHolder) row.getTag();
		}
		
		Bioskop f=data.get(position);
		holder.tvNama.setText(f.getNama());
		holder.tvTelp.setText(f.getTelp());
		holder.tvAlamat.setText(f.getAlamat());
		return row;
	}

	static class BioskopHolder {
		TextView tvNama;
		TextView tvTelp;
		TextView tvAlamat;
	}
}
