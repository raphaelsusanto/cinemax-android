package com.cineplex.service;

import java.util.List;

import com.cineplex.entity.Bioskop;
import com.cineplex.util.AutoSyncTheThread;

public class BioskopService {
	public List<Bioskop> getAllBioskop(){
		AutoSyncTheThread sync = new AutoSyncTheThread();
		List<Bioskop> data = (List<Bioskop>) sync.fetch("?do=getAllBioskop");
		return data;
	}
	public Bioskop getBioskop(int id){
		AutoSyncTheThread sync= new AutoSyncTheThread();
		Bioskop bioskop=(Bioskop)sync.fetch("?do=getBioskop&id="+id);
		return bioskop;
	}
	public List<Bioskop> getAllBioskopByFilm(int id){
		AutoSyncTheThread sync = new AutoSyncTheThread();
		List<Bioskop> data = (List<Bioskop>) sync.fetch("?do=getAllBioskopByFilm&id="+id);
		return data;
	}
}
