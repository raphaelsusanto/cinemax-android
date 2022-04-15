package com.cineplex.model;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cineplex.R;

public class DateItemAdapter extends ArrayAdapter<Date> {
	private Context context;
	private int resourceId;
	private List<Date> data;
	
	public DateItemAdapter(Context context, int textViewResourceId,
			List<Date> objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
		this.resourceId = textViewResourceId;
		this.data = objects;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		DateHolder holder = null;
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(resourceId, parent, false);
			holder = new DateHolder();
			holder.tvDate = (TextView) row.findViewById(R.id.tvDateItemTgl);
			
			row.setTag(holder);
		} else {
			holder = (DateHolder) row.getTag();
		}
		SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");
		holder.tvDate.setText(dateFormat.format(data.get(position)));
		return row;
	}
	
	static class DateHolder {
		TextView tvDate;
	}
}
