package com.cineplex.model.view;

import com.cineplex.R;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;

public class ButtonSeat extends Button{

	private Boolean status;
	public ButtonSeat(Context context) {
		super(context);
		status=false;
		this.setOnClickListener(new ButtonSeatClick());
//		this.setBackgroundColor(Color.GRAY);
		this.setBackgroundResource(R.drawable.kosong);
	}
	
	class ButtonSeatClick implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			if (status) {
				ButtonSeat.this.setBackgroundResource(R.drawable.kosong);
//				ButtonSeat.this.setBackgroundColor(Color.GRAY);
			}else{
				ButtonSeat.this.setBackgroundResource(R.drawable.selected);
//				ButtonSeat.this.setBackgroundColor(Color.YELLOW);
			}
			status=!status;
		}
	}
	
	public Boolean getStatus(){
		return this.status;
	}

}
