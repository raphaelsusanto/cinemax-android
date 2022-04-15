package com.cineplex;

import java.util.List;

import com.cineplex.model.HistoryItemAdapter;
import com.cineplex.service.LoginService;
import com.cineplex.service.PesananService;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class HistoryActivity extends Activity{
	private List<Object[]>data;
	private ListView lvHistory;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.history_order);
		lvHistory=(ListView)findViewById(R.id.lvHistoryOrder);
		lvHistory.setOnItemClickListener(new HistoryListener());
		initData();
	}
	
	private void initData(){
		PesananService svc= new PesananService();
		data=svc.getHistoryOrder(LoginService.email);
		HistoryItemAdapter adapter= new HistoryItemAdapter(this, R.layout.history_item, data);
		lvHistory.setAdapter(adapter);
	}
	
	private String sortSeat(String seat){
		String[] data= seat.split(",");
		String temp="";
		String result="";
		for (int i = 0; i < data.length; i++) {
			for (int j = i; j < data.length; j++) {
				if (data[i].compareTo(data[j])>0) {
					temp=data[i];
					data[i]=data[j];
					data[j]=temp;
				}
			}
		}
		for (int i = 0; i < data.length; i++) {
			result+=data[i]+", ";
		}
		result=result.substring(0, result.length()-2);
		return result;
	}
	
	class HistoryListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			if (data.get(arg2)[2].toString().equals("0")) {
				PesananService svc= new PesananService();
				String[] dataDetail=svc.getDetailHistoryOrder((data.get(arg2)[4]).toString());

				Dialog dialog= new Dialog(HistoryActivity.this);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.dialog_history_order);
				
				TextView tvFilm=(TextView)dialog.findViewById(R.id.dialogFilm);
				tvFilm.setText(dataDetail[0]);
				TextView tvTheater=(TextView)dialog.findViewById(R.id.dialogTheater);
				tvTheater.setText(dataDetail[1]);
				TextView tvDate=(TextView)dialog.findViewById(R.id.dialogDate);
				tvDate.setText(dataDetail[2]);
				TextView tvTime=(TextView)dialog.findViewById(R.id.dialogTime);
				tvTime.setText(dataDetail[3]);
				TextView tvSeat=(TextView)dialog.findViewById(R.id.dialogSeat);
				tvSeat.setText(sortSeat(dataDetail[4]));
				
                dialog.setCancelable(true);
				dialog.show();
			}
			//Toast.makeText(HistoryActivity.this, data.get(arg2)[4].toString(), Toast.LENGTH_SHORT).show();
		}
		
		
	}
	
}
