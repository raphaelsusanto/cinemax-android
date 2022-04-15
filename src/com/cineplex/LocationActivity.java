package com.cineplex;

import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.cineplex.entity.Bioskop;
import com.cineplex.util.AutoSyncTheThread;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class LocationActivity extends MapActivity {

	private Bioskop bioskop;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.map);
		initData();
		MapView mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);

		List<Overlay> mapOverlays = mapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable(
				R.drawable.loc);
		com.cineplex.util.Overlay itemizedoverlay = new com.cineplex.util.Overlay(
				drawable, this);
		GeoPoint point = new GeoPoint((int)(bioskop.getLatitude()*1E6), (int)(bioskop.getLongitude()*1E6));
		OverlayItem overlayitem = new OverlayItem(point, bioskop.getNama(),
				bioskop.getAlamat());

		itemizedoverlay.addOverlay(overlayitem);

		mapOverlays.add(itemizedoverlay);
		
		MapController mapController=mapView.getController();
		mapController.setCenter(point);

	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	public void initData() {
		Bundle extras = getIntent().getExtras();
		int idBioskop = extras != null ? extras.getInt("idBioskop") : null;
		AutoSyncTheThread sync = new AutoSyncTheThread();
		bioskop = (Bioskop) sync.fetch("?do=getBioskop&id=" + idBioskop);

	}

}
