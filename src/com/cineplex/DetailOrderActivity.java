package com.cineplex;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.cineplex.service.LoginService;
import com.cineplex.service.MemberService;
import com.cineplex.service.PesananService;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class DetailOrderActivity extends Activity{

	private int idJadwal;
	private String jam,seat;
	private TextView tvSeat,tvFilm,tvTheater,tvDate,tvTime,tvPrice,tvTotalPrice,tvSaldo;
	private Button btnBuy;
	private Date tgl;
	SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.order_summary);
		tvFilm=(TextView)findViewById(R.id.tvOrderSummaryFilm);
		tvTheater=(TextView)findViewById(R.id.tvOrderSummaryTheater);
		tvDate=(TextView)findViewById(R.id.tvOrderSummaryDate);
		tvTime=(TextView)findViewById(R.id.tvOrderSummaryTime);
		tvSeat=(TextView)findViewById(R.id.tvOrderSummarySeat);
		tvPrice=(TextView)findViewById(R.id.tvOrderSummaryPrice);
		tvTotalPrice=(TextView)findViewById(R.id.tvOrderSummaryTotalPrice);
		tvSaldo=(TextView)findViewById(R.id.tvOrderSummarySaldo);
		btnBuy=(Button)findViewById(R.id.btnOrderSummaryBuy);
		
		btnBuy.setOnClickListener(new OrderListener());
		initData();
		
	}
	
	private void initData(){
		Bundle extras = getIntent().getExtras();
		idJadwal = extras != null ? extras.getInt("idJadwal") : null;
		jam = extras != null ? extras.getString("jam") : null;
		seat=extras != null ? extras.getString("seat") : null;
		String temp=extras != null ? extras.getString("tgl") : null;
		try {
			tgl=dateFormat.parse(temp);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		seat=seat.substring(0, seat.length()-1);
		NumberFormat IDR=NumberFormat.getCurrencyInstance(Locale.US);
		int numberSeat=seat.split(",").length;
		
		SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");
		
		MemberService memberService= new MemberService();
		PesananService svc= new PesananService();
		Object[] detailPesan=svc.detailPesan(idJadwal,tgl);
		
		Long saldo=memberService.getSaldo(LoginService.email);
		tvFilm.setText(detailPesan[0].toString());
		tvTheater.setText(detailPesan[1].toString());
		
		tvSeat.setText(sortSeat());
		tvDate.setText(dateFormat.format(detailPesan[3]));
		tvTime.setText(jam);
		tvPrice.setText("Rp."+IDR.format(detailPesan[2]).substring(1));
		tvTotalPrice.setText("Rp."+IDR.format((((Integer)detailPesan[2])*numberSeat)).substring(1));
		tvSaldo.setText("Rp."+IDR.format(saldo).substring(1));
	}
	
	private String sortSeat(){
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
	
	class OrderListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			String msg=new PesananService().pesan(idJadwal, LoginService.email, seat, jam,tgl);
			AlertDialog dialogDetail = new AlertDialog.Builder(DetailOrderActivity.this).create();
			dialogDetail.setTitle("Detail");
			dialogDetail.setMessage(msg);
			dialogDetail.setButton("Yes", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					DetailOrderActivity.this.finish();
				}
			});
			dialogDetail.show();
//			setContentView(R.layout.message);
//			TextView tv=(TextView)findViewById(R.id.tvMessage);
//			tv.setText(msg);
		}
		
	}
	
}
