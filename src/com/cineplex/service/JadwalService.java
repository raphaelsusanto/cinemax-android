package com.cineplex.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.cineplex.entity.Jadwal;
import com.cineplex.entity.Jam;
import com.cineplex.util.AutoSyncTheThread;

public class JadwalService {
	private AutoSyncTheThread sync= new AutoSyncTheThread();
	public List<Jadwal> getJadwalByFilmAndBioskop(int idFilm,int idBioskop){
		return (List<Jadwal>)sync.fetch("?do=getJadwal&idFilm="+idFilm+"&idBioskop="+idBioskop);
	}
	
	public List<Jam> getJamByFilmAndBioskop(int idFilm,int idBioskop,Date d){
		SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");
		return (List<Jam>)sync.fetch("?do=getJam&idFilm="+idFilm+"&idBioskop="+idBioskop+"&date="+dateFormat.format(d));
	}
	
	public List<Date> getAllDate(int idFilm,int idBioskop, Date d){
		SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");
		return (List<Date>)sync.fetch("?do=getAllDate&idFilm="+idFilm+"&idBioskop="+idBioskop+"&date="+dateFormat.format(d));
	}
}
