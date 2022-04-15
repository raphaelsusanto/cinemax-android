package com.cineplex;

import com.cineplex.service.LoginService;
import com.cineplex.util.AutoSyncTheThread;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private EditText tvUsername;
	private EditText tvPassword;
	private Button btnLogin;
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.login);

		tvUsername = (EditText) findViewById(R.id.edUsername);
		tvPassword = (EditText) findViewById(R.id.edPassword);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(new ActionLogin());
		handler = new Handler();
	}

	class ActionLogin implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			try {
				String username = tvUsername.getText().toString();
				String pass = tvPassword.getText().toString();
				
				if (username.trim().equals("")||pass.trim().equals("")) {
					Toast.makeText(LoginActivity.this, "Email & password must be fill", Toast.LENGTH_SHORT).show();
				}else{
					
					Boolean cek = new LoginService().check(username, pass);
					if (cek) {

						final ProgressDialog dialog = ProgressDialog.show(
								LoginActivity.this, "", "Loading. Please wait...",
								true);

						
						handler.post(new Runnable() {

							@Override
							public void run() {
								Intent i = new Intent(LoginActivity.this,
										MainMenuActivity.class);

								finish();
								startActivity(i);
								dialog.dismiss();
							}
						});
					} else {
						Toast.makeText(getApplicationContext(),
								"username or password wrong", Toast.LENGTH_SHORT)
								.show();
					}
				}
				
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(),
						"couldn't connect to server", Toast.LENGTH_SHORT)
						.show();
				//Log.e("ERROR", e.getMessage());
			}

		}
	}
}