package com.cineplex;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import com.cineplex.MapStudioActivity.task2;
import com.cineplex.NowPlayingActivity.RefreshData;
import com.cineplex.entity.Member;
import com.cineplex.service.LoginService;
import com.cineplex.service.MemberService;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class InfoActivity extends Activity {

	private TextView tvUsername, tvEmail, tvSex, tvSaldo, tvBirthday;
	private Button btnChangePassword, btnHistory;
	private Timer timer = new Timer();
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.info);

		tvUsername = (TextView) findViewById(R.id.tvInfoUsername);
		tvEmail = (TextView) findViewById(R.id.tvInfoEmail);
		tvBirthday = (TextView) findViewById(R.id.tvInfoBirthday);
		tvSex = (TextView) findViewById(R.id.tvInfoSex);
		tvSaldo = (TextView) findViewById(R.id.tvInfoSaldo);
		btnChangePassword = (Button) findViewById(R.id.btnInfoChangePassword);
		btnHistory = (Button) findViewById(R.id.btnInfoHistory);

		btnChangePassword.setOnClickListener(changePassword);
		btnHistory.setOnClickListener(history);
		
		handler= new Handler();
		//initData();
	}

	class RefreshSaldo extends TimerTask {

		@Override
		public void run() {
			try {
				handler.post(new Runnable() {

					@Override
					public void run() {

						NumberFormat IDR = NumberFormat
								.getCurrencyInstance(Locale.US);
						MemberService svc = new MemberService();
						tvSaldo.setText("Rp."
								+ IDR.format(svc.getSaldo(LoginService.email))
										.substring(1));
						Log.e("RefreshSaldo", "a");
					}
				});
			} catch (Exception e) {
				Log.e("ERROR", e.toString());
			}

		}

	}
	
	@Override
	protected void onResume() {

		super.onResume();
		final ProgressDialog dialog = ProgressDialog.show(
				InfoActivity.this, "", "Loading. Please wait...", true);
		handler.post(new Runnable() {

			@Override
			public void run() {
				Log.e("Resume", "a");
				timer.cancel();
				timer = new Timer();
				initData();
				timer.scheduleAtFixedRate(new RefreshSaldo(), 10000, 10000);
				dialog.dismiss();
				
			}
		});
		

	}

	@Override
	protected void onPause() {
		timer.cancel();
		super.onPause();
		Log.e("pause", "a");
	}

	@Override
	protected void onStop() {
		timer.cancel();
		super.onPause();
		Log.e("Stop", "a");
	}

	
	@Override
	public void onBackPressed() {
		AlertDialog deleteExit = new AlertDialog.Builder(this).create();
		deleteExit.setTitle("Exit");
		deleteExit
				.setMessage("Are you sure you want to exit this application?");
		deleteExit.setButton("Yes", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});
		deleteExit.setButton2("No", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		deleteExit.show();
	}

	private void initData() {
		try {
			NumberFormat IDR = NumberFormat.getCurrencyInstance(Locale.US);
			MemberService svc = new MemberService();
			Member m = svc.getMemberById(LoginService.email);

			tvUsername.setText(m.getName());
			tvEmail.setText(m.getEmail());
			tvBirthday.setText(m.getBirthday().toString());
			tvSex.setText(m.getGender() ? "Male" : "Female");
			tvSaldo.setText("Rp."
					+ IDR.format(svc.getSaldo(m.getEmail())).substring(1));

			//timer.scheduleAtFixedRate(new RefreshSaldo(), 5000, 5000);
		} catch (Exception e) {
			Log.e("Error", e.toString());
		}

	}

	private OnClickListener changePassword = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent i = new Intent(InfoActivity.this,
					ChangePasswordActivity.class);
			startActivity(i);
		}
	};

	private OnClickListener history = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent i = new Intent(InfoActivity.this, HistoryActivity.class);
			startActivity(i);
		}
	};

}
