package com.cineplex;

import com.cineplex.service.LoginService;
import com.cineplex.service.MemberService;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePasswordActivity extends Activity{
	
	private EditText etOldPass,etNewPass1,etNewPass2;
	private Button btnChangePassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.change_password);
		etOldPass=(EditText)findViewById(R.id.etChangeOldPassword);
		etNewPass1=(EditText)findViewById(R.id.etChangeNewPassword1);
		etNewPass2=(EditText)findViewById(R.id.etChangeNewPassword2);
		btnChangePassword=(Button)findViewById(R.id.btnChangePassword);
		
		btnChangePassword.setOnClickListener(changePassword);
		
	}

	private OnClickListener changePassword= new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if (!etNewPass1.getText().toString().equals(etNewPass2.getText().toString())) {
				Toast.makeText(ChangePasswordActivity.this, "wrong new password", Toast.LENGTH_SHORT).show();
			}else{
				MemberService svc= new MemberService();
				boolean result=svc.changePassword(LoginService.email, etOldPass.getText().toString(), etNewPass1.getText().toString());
				if (result) {
					Toast.makeText(ChangePasswordActivity.this, "successfull change password", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(ChangePasswordActivity.this, "failed change password", Toast.LENGTH_SHORT).show();
				}
			}
		}
	};
}
