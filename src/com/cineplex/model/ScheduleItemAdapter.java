package com.cineplex.model;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.spec.IvParameterSpec;

import com.cineplex.R;
import com.cineplex.entity.Jadwal;
import com.cineplex.entity.Jam;
import com.cineplex.util.AutoSyncTheThread;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ScheduleItemAdapter extends ArrayAdapter<Jam>{

	private Context context;
	private int resourceId;
	//private List<Jadwal> data;
	private List<Jam> jam;

	public ScheduleItemAdapter(Context context, int textViewResourceId,
			List<Jam> objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
		this.resourceId = textViewResourceId;
		this.jam = objects;
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		JadwalHolder holder = null;
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(resourceId, parent, false);
			holder = new JadwalHolder();
			
			holder.tvScheduleItemJam = (TextView) row.findViewById(R.id.tvScheduleItemJam);
			holder.tvScheduleItemTmptDuduk=(TextView)row.findViewById(R.id.tvScheduleItemTmptDuduk);
			holder.ivImg=(ImageView)row.findViewById(R.id.ivScheduleItemThree);
			row.setTag(holder);
		} else {
			holder = (JadwalHolder) row.getTag();
		}
		holder.tvScheduleItemJam.setText(jam.get(position).getJam());
		holder.tvScheduleItemTmptDuduk.setText(jam.get(position).getTmptDudukk()+"");
		if (!jam.get(position).isThree()) {
			holder.ivImg.setVisibility(View.INVISIBLE);
		}
		return row;
	}
	
	class JadwalHolder{
		ImageView ivImg;
		TextView tvScheduleItemJam;
		TextView tvScheduleItemTmptDuduk;
	}
}
