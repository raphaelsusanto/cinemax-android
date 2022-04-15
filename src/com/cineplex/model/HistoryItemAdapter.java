package com.cineplex.model;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cineplex.R;
import com.cineplex.entity.Film;
import com.cineplex.model.MovieItemAdapter.MovieHolder;
import com.cineplex.util.AutoSyncTheThread;

public class HistoryItemAdapter extends ArrayAdapter<Object[]>{
	private Context context;
	private int resourceId;
	private List<Object[]> data;
	public HistoryItemAdapter(Context context, int textViewResourceId,
			List<Object[]> objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
		this.resourceId = textViewResourceId;
		this.data = objects;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		NumberFormat IDR=NumberFormat.getCurrencyInstance(Locale.US);
		View row = convertView;
		HistoryHolder holder = null;
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(resourceId, parent, false);
			holder = new HistoryHolder();
			holder.iv = (ImageView) row.findViewById(R.id.ivHistoryItemImg);
			holder.tvPrice = (TextView) row.findViewById(R.id.tvHistoryPrice);
			holder.tvSaldo=(TextView)row.findViewById(R.id.tvHistorySaldo);
			holder.tvTanggal=(TextView)row.findViewById(R.id.tvHistoryItemTgl);
			row.setTag(holder);
		} else {
			holder = (HistoryHolder) row.getTag();
		}
		AutoSyncTheThread sync= new AutoSyncTheThread();
		
		Object[] f=data.get(position);
		holder.tvTanggal.setText(f[1]+"");
		if ((f[2]+"").equals("1")) {
			holder.tvPrice.setText("Rp."+IDR.format(f[0]).substring(1));
			holder.iv.setImageResource(R.drawable.piggy);
		} else {
			holder.tvPrice.setText("-Rp."+IDR.format(f[0]).substring(1));
			holder.iv.setImageResource(R.drawable.movie);
		}
		
		holder.tvSaldo.setText("Rp."+IDR.format(f[3]).substring(1));
//		holder.tvMovie.setText(f.getJudul().toString());
//		holder.tvGenre.setText(f.getJenis().toString());
		return row;
	}
	
	static class HistoryHolder {
		
		TextView tvTanggal;
		ImageView iv;
		TextView tvPrice;
		TextView tvSaldo;
	}

}
