package com.cineplex;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.cineplex.entity.MapSeat;
import com.cineplex.entity.Studio;
import com.cineplex.model.view.ButtonSeat;
import com.cineplex.service.LoginService;
import com.cineplex.service.MemberService;
import com.cineplex.service.PesananService;
import com.cineplex.service.StudioService;
import com.cineplex.util.AutoSyncTheThread;
import com.thoughtworks.xstream.XStream;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MapStudioActivity extends Activity {
	private LinearLayout scrl;
	private TableLayout tableLayout;
	private Studio studio;
	private int idJadwal;
	private String jam;
	private List<ButtonSeat> btnSeat = new ArrayList<ButtonSeat>();
	private List<String> bookSeat = new ArrayList<String>();
	private Handler handler2, handler1;
	private Timer timer = new Timer();
	private Date tgl;
	SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");
	

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.map_studio);

		scrl = (LinearLayout) findViewById(R.id.horizontalScrollViewMapStudio);
		handler1 = new Handler();
		handler2 = new Handler();
		initData();

		timer.scheduleAtFixedRate(new task2(), 5000, 5000);

	}

	class task2 extends TimerTask {

		List<String> data = new StudioService()
				.getNotAvaibleSeat(idJadwal, jam,tgl);

		@Override
		public void run() {

			new MemberService().getMemberById(LoginService.email);
			bookSeat = new StudioService().getNotAvaibleSeat(idJadwal, jam,tgl);
			Log.e(data.size() + "", bookSeat.size() + "");
			if (bookSeat.size() != data.size()) {
				for (final String seat : bookSeat) {
					if (!data.contains(seat.trim())) {

						handler2.post(new Runnable() {
							@Override
							public void run() {
								try {
									int i = 0;
									for (; i < btnSeat.size(); i++) {
										if (btnSeat.get(i).getText().equals(
												seat)) {
											break;
										}
									}
									btnSeat.get(i).setBackgroundResource(
											R.drawable.isi);
									btnSeat.get(i).setClickable(false);
								} catch (Exception e) {
									// Log.e("error", e.toString());
								}
							}
						});
					}
				}
				Log.e("a", bookSeat.size() + "");
			}
			data = bookSeat;
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		timer.cancel();
	}

	@Override
	protected void onRestart() {
		// try {
		super.onRestart();
		// initData();
		// timer=new Timer();
		// timer.scheduleAtFixedRate(new task2(), 1000, 5000);
		this.finish();
		// } catch (Exception e) {
		// Log.e("e", e.toString());
		// }

	}

	// private ProgressDialog dialog;

	// @Override
	// protected void onStart() {
	// // TODO Auto-generated method stub
	// super.onStart();
	// initData();
	// }

	public void initData() {

		// dialog = ProgressDialog.show(MapStudioActivity.this, "",
		// "Loading. Please wait...", true);

		try {
			Bundle extras = getIntent().getExtras();
			idJadwal = extras != null ? extras.getInt("idJadwal") : null;
			jam = extras != null ? extras.getString("jam") : null;
			String temp = extras != null ? extras.getString("tgl") : null;
			tgl=dateFormat.parse(temp);
			studio = new StudioService().getStudioByJadwal(idJadwal);
			
			List<String> notAvaibleSeat = new StudioService()
					.getNotAvaibleSeat(idJadwal, jam,tgl);
			MapSeat[][] mapSeat = (MapSeat[][]) new XStream().fromXML(studio
					.getDenah());
			tableLayout = new TableLayout(MapStudioActivity.this);

			for (int i = 0; i < mapSeat.length; i++) {
				TableRow row = new TableRow(MapStudioActivity.this);
				for (int j = 0; j < mapSeat[i].length; j++) {
					if (!mapSeat[i][j].isStatus()) {
						TextView tv = new TextView(MapStudioActivity.this);
						tv.setBackgroundColor(Color.BLACK);
						row.addView(tv);
					} else {
						if (notAvaibleSeat.contains(mapSeat[i][j].getText())) {
							Button btn = new Button(MapStudioActivity.this);
							btn.setText(mapSeat[i][j].getText());
							// btn.setBackgroundColor(Color.RED);
							btn.setBackgroundResource(R.drawable.isi);
//							ViewGroup.LayoutParams params = btn
//									.getLayoutParams();
//							params.width += 20;
//							params.height+=20;
//							btn.setLayoutParams(params);
							btn.setWidth(20);
							btn.setHeight(20);
							row.addView(btn);
						} else {
							ButtonSeat btn = new ButtonSeat(
									MapStudioActivity.this);
							// btn.setBackgroundColor(Color.GRAY);
//							ViewGroup.LayoutParams params = btn
//									.getLayoutParams();
//							params.width += 20;
//							params.height+=20;
//							btn.setLayoutParams(params);
							btn.setWidth(20);
							btn.setHeight(20);
							btnSeat.add(btn);

							btn.setText(mapSeat[i][j].getText());
							row.addView(btn);
						}
					}
				}

				tableLayout.addView(row);
			}
			TextView layar = new TextView(MapStudioActivity.this);
			layar.setText("Screen");
			layar.setGravity(Gravity.CENTER);
			layar.setBackgroundColor(Color.WHITE);
			layar.setTextColor(Color.BLACK);
			// TableRow screen = new TableRow(MapStudioActivity.this);
			// screen.addView(layar);
			// tableLayout.addView(screen,new
			// LayoutParams(LayoutParams.FILL_PARENT,
			// LayoutParams.FILL_PARENT));
			// tableLayout.setGravity(Gravity.CENTER);
			scrl.addView(tableLayout);
			scrl.addView(layar, new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT));
			// dialog.dismiss();
		} catch (Exception e) {
			Log.e("error", e.toString());
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = new MenuInflater(getApplicationContext());
		inflater.inflate(R.menu.menu_map_seat, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menuPesan:
			String no = "";

			for (ButtonSeat seat : btnSeat) {
				if (seat.getStatus()) {
					no += seat.getText().toString() + ",";
				}
			}
			if (!no.equals("")) {
				Intent i = new Intent(this, DetailOrderActivity.class);
				i.putExtra("seat", no);
				i.putExtra("idJadwal", idJadwal);
				i.putExtra("jam", jam);
				i.putExtra("tgl", dateFormat.format(tgl));
				startActivity(i);
			} else {
				Toast.makeText(MapStudioActivity.this,
						"You must choose your seat first!", Toast.LENGTH_SHORT)
						.show();
			}
			break;
		}

		return true;
	}

}
