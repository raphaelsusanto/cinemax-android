package com.cineplex.service;

import java.util.List;

import com.cineplex.entity.Film;
import com.cineplex.util.AutoSyncTheThread;

public class FilmService {
	public List<Film> getNowPlayingByBioskop(int id,String email){
		AutoSyncTheThread sync= new AutoSyncTheThread();
		return (List<Film>)sync.fetch("?do=nowPlayingByBioskop&id="+id+"&email="+email);
	}
	
	public Film getFilm(int id){
		AutoSyncTheThread sync= new AutoSyncTheThread();
		Film f=(Film)sync.fetch("?do=getFilm&id="+id);
		return f;
	}
	
	public List<Film> getNowPlaying(String email){
		AutoSyncTheThread sync = new AutoSyncTheThread();
		return (List<Film>) sync.fetch("?do=nowplaying&email="+email);
	}
	
	public List<Film> getCommingSoon(){
		AutoSyncTheThread sync = new AutoSyncTheThread();
		return (List<Film>) sync.fetch("?do=commingsoon");
	}
}
